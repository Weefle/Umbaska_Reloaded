package uk.co.umbaska.UmbAccess.types;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.lang.util.SimpleLiteral;
import uk.co.umbaska.UmbAccess.types.model.AccessConnection;
import uk.co.umbaska.UmbAccess.types.model.EmptyAccessConnection;
import uk.co.umbaska.module.access.AccessModule;

/**
 * @author Andrew Tran
 */
public class TypeConnection {
    @Override
    public ClassInfo<AccessConnection> getClassInfo() {
        return new ClassInfo<AccessConnection>(AccessConnection.class, "uconnection")
            .parser(new Parser<AccessConnection>() {
                @Override
                public String toString(AccessConnection accessConnection, int i) {
                    return "Connection ID: " + accessConnection.getHost();
                }

                @Override
                public String toVariableNameString(AccessConnection accessConnection) {
                    return toString(accessConnection, 0);
                }

                @Override
                public AccessConnection parse(String s, ParseContext context) {
                    return null;
                }

                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }

                @Override
                public String getVariableNamePattern() {
                    return "Connection ID: %d+";
                }
            }).defaultExpression(new TypeConnectionDefault());
    }
    public static class TypeConnectionDefault extends SimpleLiteral<AccessConnection>{

        TypeConnectionDefault() {
            super(new EmptyAccessConnection(), true);
        }

        @Override
        public AccessConnection getSingle() {
            if (AccessModule.getInstance().getConnectionManager().keyIsUsed("default")){
                return AccessModule.getInstance().getConnectionManager().getConnection("default");
            }
            return null;
        }
    }
}
