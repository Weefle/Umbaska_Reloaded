package uk.co.umbaska.module.access.skript.mysql.expressions.connection;

import uk.co.umbaska.module.access.AccessModule;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnection;
import uk.co.umbaska.module.access.skript.mysql.types.model.AccessConnectionType;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Andrew Tran
 */
@BSyntax(
        syntax = {"new conn[ection] [with] type %uconnectiontype% [with id %-string%]",
                "new %uconnectiontype% conn[ection] [with id %-string%]"}
        , bind = {"connectionType", "id"})
public class ExprNewConnection extends UmbaskaExpression<AccessConnection>{
    @Override
    public AccessConnection getValue() {
        //MySQL as fallback
        AccessConnectionType accessConnectionType = (AccessConnectionType) exp().get("connectionType", AccessConnectionType.MYSQL);
        //Default name is always "default"
        String id = exp().getString("id", "default");
        Class<? extends AccessConnection> connectionClass = accessConnectionType.getAccessConnectionClass();
        try {
            Constructor<? extends AccessConnection> constructor = connectionClass.getDeclaredConstructor(String.class);
            AccessConnection accessConnection = constructor.newInstance(id);
            //Remove old if exists
            AccessModule.getInstance().getConnectionManager().removeConnection(id);
            AccessModule.getInstance().getConnectionManager().addConnection(id, accessConnection);
            return accessConnection;
        } catch (NoSuchMethodException e) {
            AccessModule.getInstance().skriptError(e, "Failed to instantiate connection type %s with ID %s " +
                    "because connection type's class does not " +
                    "have a constructor with a parameter of a string (id) (Full Error in Console)",
                    accessConnectionType.name(), id);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            AccessModule.getInstance().skriptError(e, "Failed to instantiate connection type %s with ID %s " +
                    "(Full Error in Console)", accessConnectionType.name(), id);
        }
        return null;
    }
}
