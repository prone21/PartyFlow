package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.User;
import it.uniroma2.partyflow.utilities.QueryMySQLManager;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing party planner persistence.
 */

public class PartyPlannerDaoMySQL {

    /**
     * Persists a user as a party planner by delegating the insertion
     * of their email into the partyplanner table to QueryMySQLManager.
     *
     * @param user the user to persist as a party planner
     * @throws PersistenceServiceUnavailableException if the database is unavailable
     * @throws PersistenceConstraintViolationException if a database constraint is violated
     * @throws DuplicateEntityException if the party planner already exists
     * @throws PersistenceGenericException if an unexpected SQL error occurs
     * @throws InvalidPersistenceTypeException if MySQL is not the configured persistence type
     */

    public void insertPartyPlanner(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException, InvalidPersistenceTypeException {

        QueryMySQLManager.persistAccountType(user.getEmail(), AccountType.PARTY_PLANNER);

    }

}