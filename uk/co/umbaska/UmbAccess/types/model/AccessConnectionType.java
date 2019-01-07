package uk.co.umbaska.module.access.skript.mysql.types.model;

/**
 * @author Andrew Tran
 */
public enum AccessConnectionType {
    MYSQL(MySQLConnection.class), MARIADB(MySQLConnection.class);

    private Class<? extends AccessConnection> accessConnectionClass;
    AccessConnectionType(Class<? extends AccessConnection> accessConnectionClass){
        this.accessConnectionClass = accessConnectionClass;
    }

    public Class<? extends AccessConnection> getAccessConnectionClass() {
        return accessConnectionClass;
    }
}
