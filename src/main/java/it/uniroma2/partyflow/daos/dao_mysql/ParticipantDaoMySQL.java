package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.QueryMySQLManager;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing participant persistence.
 */


public class ParticipantDaoMySQL {

    /**
     * Persists a user as a participant by delegating the insertion
     * of their email into the participant table to QueryMySQLManager.
     *
     * @param user the user to persist as a participant
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws PersistenceConstraintViolationException if a database constraint is violated
     * @throws DuplicateEntityException if the participant already exists
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */

    public void insertParticipant(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException, InvalidPersistenceTypeException {

        QueryMySQLManager.persistAccountType(user.getEmail(), AccountType.PARTICIPANT);

    }
}
