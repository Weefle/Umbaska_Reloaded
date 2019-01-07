package uk.co.umbaska.module.access.skript.mysql.types.model;

/**
 * @author Andrew Tran
 */
public class EmptyAccessConnection extends AccessConnection{
    public EmptyAccessConnection() {
        super("~UNUSED~");
    }

    @Override
    public void connect() {

    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void disconnect() {

    }
}
