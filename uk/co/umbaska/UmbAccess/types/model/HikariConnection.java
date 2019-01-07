package uk.co.umbaska.module.access.skript.mysql.types.model;

/**
 * @author Andrew Tran
 */
public interface HikariConnection {
    void setDataSourceProperty(String key, Object value);
    Object getDataSourceProperty(String key);
}
