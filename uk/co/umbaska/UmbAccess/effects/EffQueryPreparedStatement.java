package uk.co.umbaska.module.access.skript.mysql.effects;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.expressions.ExprLastResult;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.PreparedStatement;
import uk.co.umbaska.module.access.skript.mysql.types.model.SQLConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaAsyncEffect;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "query [prepared] [statement] %upreparedstatement% [(with|for) [connection] %uconnection%]"
        , bind = {"preparedstatement","connection"})
public class EffQueryPreparedStatement {
    @Override
    public void execute() {
        PreparedStatement preparedStatement = (PreparedStatement) exp().get("preparedstatement");
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (preparedStatement == null || accessConnection == null){
            return;
        }
        if (!(accessConnection instanceof SQLConnection)){
            AccessModule.getInstance().skriptError(
                    String.format("Cannot query Prepared Statement on connection with ID %s because it is not an SQL-like connection",
                            accessConnection.getId()));
            return;
        }
        try {
            ExprLastResult.lastResult = preparedStatement.query((SQLConnection) accessConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
