package uk.co.umbaska.UmbAccess.types;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;
import java.util.Map;

import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.lang.ParseContext;
import ch.njol.yggdrasil.Fields;
import uk.co.umbaska.UmbAccess.types.model.PreparedStatement;
import uk.co.umbaska.skript.UmbaskaType;

/**
 * @author Andrew Tran
 */
public class TypePreparedStatement extends UmbaskaType<PreparedStatement>{
    public ClassInfo<PreparedStatement> getClassInfo() {
        return new ClassInfo<>(PreparedStatement.class, "upreparedstatement")
            .parser(new Parser<PreparedStatement>() {
                @Override
                public String toString(PreparedStatement preparedStatement, int i) {
                    return preparedStatement.getQuery();
                }

                @Override
                public String toVariableNameString(PreparedStatement preparedStatement) {
                    return preparedStatement.getQuery();
                }

                @Override
                public PreparedStatement parse(String s, ParseContext context) {
                    return null;
                }

                @Override
                public boolean canParse(ParseContext context) {
                    return false;
                }

                @Override
                public String getVariableNamePattern() {
                    return ".+";
                }
            }).serializer(new Serializer<PreparedStatement>() {
                    @Override
                    public Fields serialize(PreparedStatement preparedStatement) throws NotSerializableException {
                        Fields fields = new Fields();
                        fields.putObject("query", preparedStatement.getQuery());
                        fields.putObject("bindings", preparedStatement.getBindings());
                        return fields;
                    }

                    @Override
                    protected PreparedStatement deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
                        PreparedStatement preparedStatement = new PreparedStatement((String) fields.getObject("query"));
                        Map<Integer, Object> bindings = (Map<Integer, Object>) fields.getObject("bindings");
                        if (bindings != null){
                            preparedStatement.setBindings(bindings);
                        }
                        return preparedStatement;
                    }

                    @Override
                    public void deserialize(PreparedStatement preparedStatement, Fields fields) throws StreamCorruptedException, NotSerializableException {
                        assert false;
                    }

                    @Override
                    public boolean mustSyncDeserialization() {
                        return false;
                    }

                    @Override
                    protected boolean canBeInstantiated() {
                        return false;
                    }
                });
    }
}
