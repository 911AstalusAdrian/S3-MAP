package Model.Types;

import Model.Values.StringValue;
import Model.Values.Value;

public class StringType implements Type{

    @Override
    public boolean equals(Object another){return another instanceof StringType;}

    public String toString(){return "String";}

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
