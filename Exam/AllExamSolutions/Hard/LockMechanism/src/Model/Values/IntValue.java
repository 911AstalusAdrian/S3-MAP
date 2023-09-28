package Model.Values;

import Model.Types.IntType;
import Model.Types.Type;

import java.util.Objects;

public class IntValue implements Value {
    public final int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    public boolean equals(Value anotherValue) {
        if (!(anotherValue instanceof IntValue)) {
            return false;
        }
        return value == ((IntValue) anotherValue).getValue();
    }

    @Override
    public Value deepCopy() {
        return new IntValue(value);
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
