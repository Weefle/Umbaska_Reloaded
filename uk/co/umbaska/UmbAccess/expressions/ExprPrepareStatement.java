package uk.co.umbaska.module.access.skript.mysql.expressions;

import uk.co.umbaska.module.access.skript.mysql.types.model.PreparedStatement;
import uk.co.umbaska.registrations.annotations.BSyntax;
import uk.co.umbaska.skript.UmbaskaExpression;

/**
 * @author Andrew Tran
 */
@BSyntax(syntax = "[(new|create)] prepare[d] statement [with] [(query|statement)] %string%", bind = "query")
public class ExprPrepareStatement extends UmbaskaExpression<PreparedStatement>{
    @Override
    public PreparedStatement getValue() {
        String query = exp().getString("query");
        if (query == null){
            return null;
        }
        return new PreparedStatement(query);
    }
}
