package uk.co.umbaska.module.access.skript.mysql.expressions;

import uk.co.umbaska.module.access.skript.mysql.effects.EffRunPreparedStatement;
import uk.co.umbaska.registrations.annotations.Syntax;
import uk.co.umbaska.skript.UmbaskaExpression;

import java.sql.ResultSet;

/**
 * @author Andrew Tran
 */
@Syntax("last [m[ariadb]y][sql] result [set]")
public class ExprLastResult extends UmbaskaExpression<ResultSet>{
    public static ResultSet lastResult;
    @Override
    public ResultSet getValue() {
        return lastResult;
    }
}
