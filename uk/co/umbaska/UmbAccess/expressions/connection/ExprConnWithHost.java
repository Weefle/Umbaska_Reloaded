package uk.co.umbaska.module.access.skript.mysql.expressions.connection;

import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "%uconnection% [with] host %string%", bind = {"connection","host"})
public class ExprConnWithHost extends UmbaskaExpression<AccessConnection>{
    @Override
    public AccessConnection getValue() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return null;
        }
        accessConnection.setHost(exp().getString("host"));
        return accessConnection;
    }
}
