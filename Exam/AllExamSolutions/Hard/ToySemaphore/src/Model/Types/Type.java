package Model.Types;

import Model.Values.Value;

public interface Type {
    boolean equals(Type anotherType);
    Type deepCopy();
    Value getDefault();
}
