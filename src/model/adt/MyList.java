package model.adt;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IMyList<T> {
    private List<T> list;

    public MyList() { this.list = new ArrayList<T>(); }

    @Override
    public void add(T t) {
        this.list.add(t);
    }

    @Override
    public List<T> getAll() {
        return this.list;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        this.list.forEach((t) -> {
            str.append(t).append(" ");
        });
        return "List is: [ " + str + "\b]\n";
    }
}
