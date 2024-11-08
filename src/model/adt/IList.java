package model.adt;

import java.util.List;

public interface IList<T> {
    void add(T t);
    List<T> getAll();
}
