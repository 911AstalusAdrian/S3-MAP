package Model.Values;

import Model.Types.ReferenceType;
import Model.Types.Type;

public class ReferenceValue implements Value {
    private final int address;
    private final Type locationType;

    public ReferenceValue(Integer address, Type referenceType) {
        this.address = address;
        this.locationType = referenceType;
    }

    @Override
    public Type getType() {
        return new ReferenceType(locationType);
    }
    //    public Type getType() {
    //        return new ReferenceType(new ReferenceType(locationType));
    //    }
    public int getAddress() {
        return address;
    }

    public Type getLocationType() {
        return locationType;
    }

    @Override
    public Value deepCopy() {
        return new ReferenceValue(address, locationType.deepCopy());
    }

    @Override
    public String toString() {
        return String.format("(%d -> %s)", address, locationType);
    }
}
