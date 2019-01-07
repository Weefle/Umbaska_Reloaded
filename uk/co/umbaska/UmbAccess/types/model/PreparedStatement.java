package uk.co.umbaska.module.access.skript.mysql.types.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrew Tran
 */
public class PreparedStatement {
    private String query;
    private int index = 1;
    private Map<Integer, Object> bindings = new HashMap<>();
    private List<Map<Integer, Object>> batch = new ArrayList<>();


    public PreparedStatement(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<Integer, Object> getBindings() {
        return bindings;
    }

    public void bind(Object object){
        bindings.put(index, object);
        index++;
    }

    public void addToBatch(){
        batch.add(new HashMap<>(bindings));
        clearBindings();
        index = 1;
    }

    public void resetBatch(){
        batch.clear();
    }

    public void clearBindings(){
        bindings.clear();
    }

    public void setBindings(Map<Integer, Object> bindings) {
        this.bindings = bindings;
    }

    public ResultSet query(SQLConnection sqlConnection) throws SQLException {
        java.sql.PreparedStatement preparedStatement = sqlConnection.getConnection().prepareStatement(query);
        for (Map.Entry<Integer, Object> bind : bindings.entrySet()){
            preparedStatement.setObject(bind.getKey(), bind.getValue());
        }
        return preparedStatement.executeQuery();
    }

    public void update(SQLConnection accessConnection) throws SQLException {
        if (batch.size() == 0){
            updateSingle(accessConnection);
        }else{
            updateBatch(accessConnection);
        }
    }

    public void updateSingle(SQLConnection accessConnection) throws SQLException {
        addToBatch();
        updateBatch(accessConnection);
    }

    public void updateBatch(SQLConnection accessConnection) throws SQLException {
        Connection connection = accessConnection.getConnection();
        try{
            if (batch.size() == 1){
                java.sql.PreparedStatement preparedStatement = accessConnection.getConnection().prepareStatement(query);
                for (Map.Entry<Integer, Object> bind : batch.get(0).entrySet()){
                    preparedStatement.setObject(bind.getKey(), bind.getValue());
                }
                preparedStatement.executeUpdate();
                return;
            }

            connection.setAutoCommit(false);
            java.sql.PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (Map<Integer, Object> item : batch){
                for (Map.Entry<Integer, Object> bind : item.entrySet()){
                    preparedStatement.setObject(bind.getKey(), bind.getValue());
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e){
            connection.rollback();
            e.printStackTrace();
        }
    }
}
