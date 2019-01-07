package uk.co.umbaska.module.access.skript.mysql.types.model;

/**
 * @author Andrew Tran
 */
public interface DatabaseConnection {
    void setDatabase(String database);
    String getDatabase();
}
