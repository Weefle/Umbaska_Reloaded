package uk.co.umbaska.module.access.skript.mysql.effects;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.SQLConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaEffect;

import java.sql.SQLException;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "(run|update) [statement] %string% [for|of] [connection %uconnection%]"
        , bind = {"statement","connection"})
public class EffRunStatement extends UmbaskaEffect{
    @Override
    public void execute() {
        String statement = exp().getString("statement");
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (statement == null || accessConnection == null){
            return;
        }
        if (!(accessConnection instanceof SQLConnection)){
            AccessModule.getInstance().skriptError(
                    String.format("Cannot run statement on connection with ID %s because it is not an SQL-like connection",
                            accessConnection.getId()));
            return;
        }
        try {
            ((SQLConnection) accessConnection).getConnection().createStatement().execute(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
