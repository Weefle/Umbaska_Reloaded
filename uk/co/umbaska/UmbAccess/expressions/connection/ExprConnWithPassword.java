package uk.co.umbaska.module.access.skript.mysql.expressions.connection;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.NormalAuthConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "%uconnection% [with] pass[word] %string%", bind = {"connection","password"})
public class ExprConnWithPassword extends UmbaskaExpression<AccessConnection> {
    @Override
    public AccessConnection getValue() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return null;
        }
        if (!(accessConnection instanceof NormalAuthConnection)){
            AccessModule.getInstance().skriptError("Could not set password for connection with ID %s" +
                            " because connection is not of type Normal Authentication",
                    accessConnection.getId());
            return null;
        }
        ((NormalAuthConnection) accessConnection).setPassword(exp().getString("password"));
        return accessConnection;
    }
}