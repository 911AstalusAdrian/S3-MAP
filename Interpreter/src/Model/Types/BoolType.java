package Model.Types;

import Model.Values.BoolValue;
import Model.Values.Value;

public class BoolType implements Type{

    @Override
    public boolean equals(Object another){
        return another instanceof BoolType;
    }

    public String toString(){return "Bool";}

    @Override
    public Value defaultValue() {
        return new BoolValue(false);
    }
}
