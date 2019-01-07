package uk.co.umbaska.module.access.skript.mysql.expressions.connection;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.DatabaseConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.NormalAuthConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "%uconnection% [with] database %string%", bind = {"connection","database"})
public class ExprConnWithDatabase extends UmbaskaExpression<AccessConnection> {
    @Override
    public AccessConnection getValue() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return null;
        }
        if (!(accessConnection instanceof DatabaseConnection)){
            AccessModule.getInstance().skriptError("Could not set database for connection with ID %s" +
                            " because connection is not of type Database Connection",
                    accessConnection.getId());
            return null;
        }
        ((DatabaseConnection) accessConnection).setDatabase(exp().getString("database"));
        return accessConnection;
    }
}