package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.User;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing party planner persistence.
 * This class provides operations to store party planner data in the
 * database using JDBC.
 *
 */

public class PartyPlannerDaoMySQL {

    public void insertPartyPlanner(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException, InvalidPersistenceTypeException {

        QueryMySQLManager.persistAccountType(user.getEmail(), AccountType.PartyPlanner);

    }

}