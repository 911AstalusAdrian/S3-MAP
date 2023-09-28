package Model.Values;
import Model.Types.IntType;
import Model.Types.Type;

public class IntValue implements Value{
    int val;

    public IntValue(int v){val = v;}
    public int getVal(){return val;}
    public String toString(){return Integer.toString(val);}

    @Override
    public boolean equals(Object another){return another instanceof IntValue;}

    @Override
    public Type getType() {
        return new IntType();
    }
}
