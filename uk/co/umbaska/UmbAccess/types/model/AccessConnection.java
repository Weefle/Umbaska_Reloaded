package uk.co.umbaska.UmbAccess.types.model;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Andrew Tran
 */
public abstract class AccessConnection implements Closeable{
    private String id, host;
    private Integer port;

    public AccessConnection(String id) {
        this.id = id;
    }

    public AccessConnection(String id, String host, int port) {
        this.id = id;
        this.host = host;
        this.port = port;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void close() throws IOException {
        disconnect();
    }

    public abstract void connect();
    public abstract boolean isConnected();
    public abstract void disconnect();
}
