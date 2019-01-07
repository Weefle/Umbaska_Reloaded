package uk.co.umbaska.UmbAccess.effects;

import uk.co.umbaska.UmbAccess.types.model.PreparedStatement;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaEffect;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "(set|bind) %object% (of|for|to) %upreparedstatement%",
        bind = {"value","preparedstatement"})
public class EffBindParameterToPreparedStatement extends UmbaskaEffect{
    @Override
    public void execute() {
        Object value = exp().get("value");
        PreparedStatement preparedStatement = (PreparedStatement) exp().get("preparedstatement");
        if (value == null || preparedStatement == null){
            return;
        }
        preparedStatement.bind(value);
    }
}
