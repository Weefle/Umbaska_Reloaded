package uk.co.umbaska.module.access.skript.mysql.expressions;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.SQLConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "all databases [of %uconnection%]", bind = "connection")
public class ExprAllDatabases extends UmbaskaExpression<String>{
    @Override
    public String[] getValues() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return null;
        }
        if (!(accessConnection instanceof SQLConnection)){
            AccessModule.getInstance().skriptError(
                    String.format("Cannot get all databases of connection with ID %s because it is not an SQL-like connection",
                    accessConnection.getId()));
            return null;
        }
        return ((SQLConnection) accessConnection).getDatabases();
    }
}
