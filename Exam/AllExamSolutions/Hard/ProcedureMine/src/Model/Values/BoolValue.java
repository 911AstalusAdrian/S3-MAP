package Model.Values;

import Model.Types.BoolType;
import Model.Types.Type;

import java.util.Objects;

public class BoolValue implements Value {
    private final boolean value;

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof BoolValue)) {
            return false;
        }
        return value == ((BoolValue)anotherValue).getValue();
    }

    @Override
    public Value deepCopy() {
        return new BoolValue(value);
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }
}
