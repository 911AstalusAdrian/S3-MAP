package Model.Values;
import Model.Types.BoolType;
import Model.Types.Type;

public class BoolValue implements Value{
    boolean val;
    public BoolValue(boolean v){val = v;}
    public boolean getVal(){return val;}
    public String toString(){return Boolean.toString(val);}

    @Override
    public boolean equals(Object another){return another instanceof BoolValue;}

    @Override
    public Type getType(){return new BoolType();}
}
