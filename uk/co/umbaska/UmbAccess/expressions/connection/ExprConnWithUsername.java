package uk.co.umbaska.module.access.skript.mysql.expressions.connection;

import ch.njol.skript.Skript;
import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.NormalAuthConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "%uconnection% [with] user[name] %string%", bind = {"connection","username"})
public class ExprConnWithUsername extends UmbaskaExpression<AccessConnection> {
    @Override
    public AccessConnection getValue() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return null;
        }
        if (!(accessConnection instanceof NormalAuthConnection)){
            AccessModule.getInstance().skriptError("Could not set username for connection with ID %s" +
                    " because connection is not of type Normal Authentication",
                    accessConnection.getId());
            return null;
        }
        ((NormalAuthConnection) accessConnection).setUsername(exp().getString("username"));
        return accessConnection;
    }
}
