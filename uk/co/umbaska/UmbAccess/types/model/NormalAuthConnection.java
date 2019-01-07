package uk.co.umbaska.module.access.skript.mysql.types.model;

/**
 * @author Andrew Tran
 */
public interface NormalAuthConnection{
    void setUsername(String username);
    String getUsername();
    void setPassword(String password);
    String getPassword();

}
