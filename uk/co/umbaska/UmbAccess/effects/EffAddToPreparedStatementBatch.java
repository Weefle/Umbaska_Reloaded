package uk.co.umbaska.UmbAccess.effects;

import uk.co.umbaska.UmbAccess.types.model.PreparedStatement;
import uk.co.umbaska.registrations.annotations.BSyntax;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "add [current] [bind[ing][s]] for [prepared] [statement] %upreparedstatement% [to] [batch]", bind = "preparedstatement")
public class EffAddToPreparedStatementBatch {
    @Override
    public void execute() {
        PreparedStatement preparedStatement = (PreparedStatement) exp().get("preparedstatement");
        if (preparedStatement != null){
            preparedStatement.addToBatch();
        }
    }
}
