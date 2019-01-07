package uk.co.umbaska.module.access.skript.mysql.effects;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.expressions.ExprLastResult;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.PreparedStatement;
import uk.co.umbaska.module.access.skript.mysql.types.model.SQLConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaAsyncEffect;

import java.sql.SQLException;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "query [statement] %string% [(with|for) [connection] %uconnection%]"
        , bind = {"statement","connection"})
public class EffQueryStatement extends UmbaskaAsyncEffect{
    @Override
    public void execute() {
        String statement = exp().getString("statement");
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (statement == null || accessConnection == null){
            return;
        }
        if (!(accessConnection instanceof SQLConnection)){
            AccessModule.getInstance().skriptError(
                    String.format("Cannot query Statement on connection with ID %s because it is not an SQL-like connection",
                            accessConnection.getId()));
            return;
        }
        try {
            ExprLastResult.lastResult = ((SQLConnection) accessConnection).getConnection().createStatement().executeQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
