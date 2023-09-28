package Model.Values;

import Model.Types.StringType;
import Model.Types.Type;

import java.util.Objects;

public class StringValue implements Value{
    public final String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value deepCopy() {
        return new StringValue(value);
    }


    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof StringValue)) {
            return false;
        }
        return Objects.equals(value, ((StringValue) anotherValue).getValue());
    }
}
