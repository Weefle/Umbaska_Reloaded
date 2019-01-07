package uk.co.umbaska.UmbAccess.effects;

import uk.co.umbaska.UmbAccess.types.model.PreparedStatement;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaEffect;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "(clear|reset|delete) [prepared] [statement][[']s] %upreparedstatement% bind[ing][s]",
        bind = "preparedstatement")
public class EffClearPreparedStatementBatch {
    @Override
    public void execute() {
        PreparedStatement preparedStatement = (PreparedStatement) exp().get("preparedstatement");
        if (preparedStatement != null){
           preparedStatement.resetBatch();
        }
    }
}
