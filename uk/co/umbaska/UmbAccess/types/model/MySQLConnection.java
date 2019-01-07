package uk.co.umbaska.module.access.skript.mysql.types.model;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import uk.co.umbaska.module.access.AccessModule;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tran
 */
public class MySQLConnection extends AccessConnection implements DatabaseConnection, NormalAuthConnection, HikariConnection, SQLConnection{
    private String database, username, password;

    private HikariConfig hikariConfig = new HikariConfig();
    private DataSource dataSource;
    private Connection connection;

    public MySQLConnection(String id) {
        super(id);
    }

    public ReadyStatus isReady(){
        if (getHost() == null){
            return ReadyStatus.MISSING_HOST;
        }else if (username == null){
            return ReadyStatus.MISSING_USERNAME;
        }else if (password == null){
            return ReadyStatus.MISSING_PASSWORD;
        }
        return ReadyStatus.READY;
    }

    @Override
    public String[] getDatabases() {
        try(ResultSet rs = connection.getMetaData().getCatalogs();){
            List<String> databases = new ArrayList<>();
            while (rs.next()) {
                databases.add(rs.getString("TABLE_CAT"));
            }
            return databases.stream().toArray(String[]::new);
        } catch (SQLException e){
          e.printStackTrace();
        }
        return null;
    }

    public enum ReadyStatus{
        READY, MISSING_HOST, MISSING_USERNAME, MISSING_PASSWORD;
    }

    @Override
    public void connect() {

        if (!isReady().equals(ReadyStatus.READY)){
            switch (isReady()){
                case MISSING_HOST:
                    AccessModule.getInstance().skriptError(
                            "Could not connect: Host was not set for MySQL Connection with ID %s",
                            getId());
                    break;
                case MISSING_USERNAME:
                    AccessModule.getInstance().skriptError(
                            "Could not connect: Username was not set for MySQL Connection with ID %s",
                            getId());
                    break;
                case MISSING_PASSWORD:
                    AccessModule.getInstance().skriptError(
                            "Could not connect: Password was not set for MySQL Connection with ID %s",
                            getId());
                    break;
            }
            return;
        }

        //SET JBDC URL
        String jbdcURL = String.format("jdbc:mysql://%s:%d/%s",
                getHost() == null ? "localhost" : getHost(),
                getPort() == null ? 3306 : getPort(),
                getDatabase() == null ? "" : getDatabase());
        hikariConfig.setDataSourceClassName(null);
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl(jbdcURL);
        hikariConfig.setUsername(getUsername());
        hikariConfig.setPassword(getPassword());

        dataSource = new HikariDataSource(hikariConfig);
        try {
            connection = dataSource.getConnection();
            AccessModule.getInstance().getLogger().info("Successfully connected to MySQL Connection with ID " + getId());
        } catch (SQLException e) {
            AccessModule.getInstance().skriptError("Could not connect to MySQL Connection with ID %s (Error in Console)", getId());
        }
    }

    @Override
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void disconnect() {
        if (isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                AccessModule.getInstance().skriptError(e, "Could not close MySQL Connection with ID %s (Error in Console)", getId());
            }
        }
    }

    public HikariConfig getHikariConfig() {
        return hikariConfig;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public String getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(String database) {
        this.database = database;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setDataSourceProperty(String key, Object value) {
        hikariConfig.addDataSourceProperty(key, value);
    }

    @Override
    public Object getDataSourceProperty(String key) {
        return hikariConfig.getDataSourceProperties().get(key);
    }
}
