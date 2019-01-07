package uk.co.umbaska.UmbAccess.types.model;

import java.sql.Connection;

/**
 * @author Andrew Tran
 */
public interface SQLConnection{
    String[] getDatabases();
    Connection getConnection();
}
