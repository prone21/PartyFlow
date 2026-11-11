package it.uniroma2.partyflow.daos.dao_mysql;

import it.uniroma2.partyflow.enums.AccountType;
import it.uniroma2.partyflow.exceptions.daoexception.*;
import it.uniroma2.partyflow.models.User;

/**
 * MySQL implementation of the Data Access Object (DAO) responsible
 * for managing participant persistence.
 * This class provides operations to store participant data in the
 * database using JDBC.
 *
 */

public class ParticipantDaoMySQL {
    public void insertParticipant(User user) throws PersistenceServiceUnavailableException,
            PersistenceConstraintViolationException, DuplicateEntityException, PersistenceGenericException, InvalidPersistenceTypeException {

        QueryMySQLManager.persistAccountType(user.getEmail(), AccountType.Participant);

    }
}
