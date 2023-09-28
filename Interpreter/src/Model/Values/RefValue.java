package Model.Values;

import Model.Types.RefType;
import Model.Types.Type;

public class RefValue implements Value{
    int heapAddr;
    Type locationType;

    public RefValue(int addr, Type locType){this.heapAddr = addr; this.locationType = locType;}

    public int getAddr(){return heapAddr;}
    public Type getLocationType(){return locationType;}

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(heapAddr);
        sb.append("->");
        sb.append(locationType.toString());
        return sb.toString();
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }
}
