package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.daos.ConnectionFactory;
import it.uniroma2.partyflow.daos.dao_interfaces.UserDaoInterface;
import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.enums.Gender;
import it.uniroma2.partyflow.errorhandlers.HandlerSystemError;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.LoginCredential;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.PasswordProtector;
import java.sql.*;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing user persistence.
 *
 * <p>Manages database transactions during account creation
 * and translates SQL exceptions into application-specific
 * persistence exceptions.</p>
 */


public class UserDaoMySQL implements UserDaoInterface {

    private Connection conn = null;

    /**
     * Retrieves the shared JDBC connection through ConnectionFactory.
     *
     * @throws PersistenceServiceUnavailableException if the database
     * connection cannot be established
     * @throws InvalidPersistenceTypeException if MySQL is not the
     * configured persistence type
     */

    private void connect() throws PersistenceServiceUnavailableException,
            InvalidPersistenceTypeException {

            this.conn = ConnectionFactory.getConnection();
    }


    /**
     * Rolls back all uncommitted operations within the current transaction.
     *
     * <p>Any SQLException occurring during the rollback is logged
     * through HandlerSystemError without being propagated to the caller.</p>
     */

    private void rollBack() {
        try{
            if(this.conn != null){
                this.conn.rollback();
            }
        }
        catch (SQLException e1){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e1);
        }
    }



    /**
     * Persists a new user in the MySQL database.
     *
     * <p>Disables auto-commit to manage the database transaction explicitly.
     * Inserts the user's data into the users table and delegates the insertion
     * of the account type to the corresponding DAO.</p>
     *
     * <p>Commits the transaction upon successful completion and attempts
     * a rollback if a persistence or SQL error occurs, except in the case
     * of an InvalidPersistenceTypeException.</p>
     *
     * <p>The finally block restores auto-commit and closes the prepared
     * statement, logging any errors occurring during these operations.</p>
     *
     * @param user the user to persist
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws DuplicateEntityException if the user already exists
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws PersistenceConstraintViolationException if a foreign key constraint is violated
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */

    @Override
    public void signUp(User user) throws PersistenceServiceUnavailableException, DuplicateEntityException,
            PersistenceGenericException, PersistenceConstraintViolationException, InvalidPersistenceTypeException {

        PreparedStatement stmt = null;

        /*
         * Tracks the transaction lifecycle to ensure that auto-commit
         * is restored only after a successful commit or rollback.
         * This prevents unintentional commits of partially executed
         * operations if an exception occurs during the transaction.
         */
        boolean transactionStarted = false;
        boolean transactionCompleted = false;

        try{
            this.connect();
            this.conn.setAutoCommit(false); // start transaction
            transactionStarted = true;

            // insert the new account in the 'users' table
            String query = "insert into users (email, name, surname, password, dateOfBirth" +
                    ", gender) value (?,?,?,?,?,?)";

            stmt = this.conn.prepareStatement(query);

            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getPwd());
            stmt.setDate(5, Date.valueOf(user.getDateOfBirth()));
            stmt.setString(6, user.getGender().name());

            stmt.executeUpdate();

            // Insert the user into the table corresponding to their account type.
            switch (user.getAccountType()) {
                case PARTY_PLANNER -> {
                    PartyPlannerDaoMySQL plannerDaoMySQL = new PartyPlannerDaoMySQL();
                    plannerDaoMySQL.insertPartyPlanner(user);
                }
                case PARTICIPANT -> {
                    ParticipantDaoMySQL participantDaoMySQL = new ParticipantDaoMySQL();
                    participantDaoMySQL.insertParticipant(user);
                }
            }

            this.conn.commit(); // end transaction
            transactionCompleted = true;
        }

        catch (InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        catch (PersistenceServiceUnavailableException | DuplicateEntityException |
               PersistenceConstraintViolationException | PersistenceGenericException e){

            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack();

            // throw the exception
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);

            // rollback all the action done
            this.rollBack();

            switch (e.getErrorCode()){
                case 1062:
                    // Primary key or UNIQUE duplicated
                    throw new DuplicateEntityException();

                case 1451, 1452:
                    // Break Foreign Key constraint
                    throw new PersistenceConstraintViolationException();

                default:
                    throw new PersistenceGenericException();
            }
        }

        finally {
            try{
                if (this.conn != null && transactionStarted) {

                    if (!transactionCompleted) {
                        this.conn.rollback();
                    }

                    this.conn.setAutoCommit(true);
                }
            }
            catch (SQLException e1){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e1);
            }

            try {
                if (stmt != null) {
                    stmt.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }
    }



    /**
     * Authenticates a user against the MySQL database.
     *
     * <p>Retrieves the stored password and account type associated
     * with the provided email address using a prepared statement.
     * Verifies the password through PasswordProtector and returns
     * the corresponding account type if authentication succeeds.</p>
     *
     * <p>Translates SQL exceptions into persistence exceptions
     * and releases JDBC resources through the finally block.</p>
     *
     * @param cred the login credentials provided by the user
     * @return the account type of the authenticated user
     * @throws PersistenceGenericException if an SQL error occurs
     * @throws EntityNotFound if the user does not exist or the password is incorrect
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     * @throws EntityNotFound if no matching account is found
     */

    @Override
    public AccountType login(LoginCredential cred) throws PersistenceGenericException,
            EntityNotFound, PersistenceServiceUnavailableException, InvalidPersistenceTypeException {

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try{
            this.connect();
            String password = cred.getPassword();

            // check if the user is a participant or a party planner
            String query = "select u.password as password, COALESCE(p.email,'PartyPlanner') as account_type\n" +
                    "from users as u left join participant as p on u.email = p.email " +
                    "where u.email = ?";

            stmt = this.conn.prepareStatement(query);
            stmt.setString(1,cred.getEmail());

            rs = stmt.executeQuery();

            if (rs.next() && PasswordProtector.checkPassword(password, rs.getString("password"))){
                if (rs.getString("account_type").equals("PartyPlanner")){
                    return AccountType.PARTY_PLANNER;
                }
                else{
                    return AccountType.PARTICIPANT;
                }

            }
            else{
                throw new EntityNotFound();
            }
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }

        catch (PersistenceServiceUnavailableException | EntityNotFound | InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if(rs != null){
                    rs.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }
    }


    /**
     * Retrieves a user from the MySQL database using their email address.
     *
     * <p>Retrieves the user's personal information and determines their
     * account type through the corresponding DAOs. Constructs and
     * returns the User domain model.</p>
     *
     * @param email the email address identifying the user
     * @return the corresponding User, or null if no user is found
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */
    @Override
    public User retriveUserByEmail(String email) throws PersistenceGenericException, PersistenceServiceUnavailableException, InvalidPersistenceTypeException, EntityNotFound {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AccountType accountType = null;
        User user;

        try{
            this.connect();

            // ---- obtain email, name, surname, dateOfBirth, password and gender ----
            String query = "select email, name, surname, dateOfBirth, password, gender from\n" +
                    "users  where email = ?";
            stmt = this.conn.prepareStatement(query);

            stmt.setString(1,email);

            rs = stmt.executeQuery();
            // ----------------------------------------------------------

            // -------------- obtain user account type ------------------
            ParticipantDaoMySQL participantDaoMySQL = new ParticipantDaoMySQL();
            PartyPlannerDaoMySQL partyPlannerDaoMySQL = new PartyPlannerDaoMySQL();
            if(participantDaoMySQL.existsParticipant(email)){
                accountType = AccountType.PARTICIPANT;
            } else if (partyPlannerDaoMySQL.existsPartyPlanner(email)) {
                accountType = AccountType.PARTY_PLANNER;

            }
            // ----------------------------------------------------------

            // --------------- define the model user --------------------
            if(rs.next()){
                user = new User( rs.getString("name"),rs.getString("surname"),
                        rs.getString("email"),
                        rs.getString("password"), rs.getDate("dateOfBirth").toLocalDate(),
                        accountType, Gender.valueOf(rs.getString("gender")) );

                return user;
            }
            else{
                throw new EntityNotFound();
            }

        }

        catch (PersistenceServiceUnavailableException | EntityNotFound | InvalidPersistenceTypeException e){
            // log the error
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            // throw the exception
            throw e;
        }

        catch (SQLException e){
            HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
            handlerSystemError.logError(e);
            throw new PersistenceGenericException();
        }

        finally{
            try {
                if (stmt != null) {
                    stmt.close();
                }

                if(rs != null){
                    rs.close();
                }
            }
            catch (SQLException e2){
                HandlerSystemError handlerSystemError = HandlerSystemError.getInstance();
                handlerSystemError.logError(e2);
            }
        }

    }

}
