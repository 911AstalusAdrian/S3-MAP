package Model.Types;

import Model.Values.ReferenceValue;
import Model.Values.Value;

public class ReferenceType implements Type {
    private final Type innerType;

    public ReferenceType(Type innerType) {
        this.innerType = innerType;
    }

    public Type getInnerType() {
        return innerType;
    }

    @Override
    public boolean equals(Type anotherType) {
        if (anotherType instanceof ReferenceType) {
            return innerType.equals(((ReferenceType) anotherType).getInnerType());
        } else {
            return false;
        }
    }

    @Override
    public Type deepCopy() {
        return new ReferenceType(innerType.deepCopy());
    }

    @Override
    public Value getDefault() {
        return new ReferenceValue(0, innerType);
    }

    @Override
    public String toString() {
        return String.format("Ref(%s)", innerType.toString());
    }
}
