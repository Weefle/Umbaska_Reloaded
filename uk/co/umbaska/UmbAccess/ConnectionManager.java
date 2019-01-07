package uk.co.umbaska.UmbAccess;

import java.io.IOException;
import java.util.HashMap;

import uk.co.umbaska.UmbAccess.types.model.AccessConnection;

/**
 * @author Andrew Tran
 */
public class ConnectionManager {
    private HashMap<String,AccessConnection> connections = new HashMap<>();

    public boolean keyIsUsed(String key){
        return connections.containsKey(key);
    }

    public void addConnection(String key, AccessConnection accessConnection){
        if (keyIsUsed(key)){
            throw new IllegalArgumentException("Connection with key %s is already added!");
        }
        connections.put(key, accessConnection);
    }

    public void removeConnection(String key){
        if (keyIsUsed(key)){
            if (connections.get(key).isConnected()){
                try {
                    connections.get(key).close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connections.remove(key);
        }
    }

    public AccessConnection getConnection(String key){
        return connections.get(key);
    }

}
