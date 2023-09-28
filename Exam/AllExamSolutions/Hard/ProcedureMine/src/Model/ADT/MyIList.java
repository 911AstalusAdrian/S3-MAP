package Model.ADT;


import Model.Values.Value;

import java.util.List;

public interface MyIList<TElem> extends Iterable<TElem> {
    void add(TElem element);

    TElem pop() throws Exception;

    boolean isEmpty();

    List<TElem> getList();
}
