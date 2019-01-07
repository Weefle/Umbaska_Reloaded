package uk.co.umbaska.module.access.skript.mysql.effects;

import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaAsyncEffect;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "connect to %uconnection%", bind = "connection")
public class EffConnect extends UmbaskaAsyncEffect {
    @Override
    public void execute() {
        AccessConnection accessConnection = (AccessConnection) exp().get("connection");
        if (accessConnection == null){
            return;
        }
        accessConnection.connect();
    }
}
