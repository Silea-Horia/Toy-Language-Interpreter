package model.adt;

import model.exception.DictionaryException;
import model.value.IValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<A, V> implements IHeap<A, V> {
    private int nextFree;
    private Map<Integer, IValue> memory;

    public Heap() {
        this.nextFree = 1;
        this.memory = new HashMap<>();
    }

    private void getNextFree() {
        this.nextFree = 1;
        while (this.memory.containsKey(this.nextFree)) {
            this.nextFree += 1;
        }
    }

    @Override
    public void insert(V v) {
        this.memory.put(this.nextFree, (IValue) v);
        this.getNextFree();
    }

    @Override
    public void remove(A a) throws DictionaryException {
        if (!this.memory.containsKey((Integer)a)) {
            throw new DictionaryException("Heap address is not instantiated!\n");
        }
        this.memory.remove((Integer)a);
        if ((Integer)a < this.nextFree) this.nextFree = (Integer)a;
    }

    @Override
    public boolean contains(A a) {
        return this.memory.containsKey((Integer)a);
    }

    @Override
    public V lookup(A a) throws DictionaryException {
        if (!this.memory.containsKey((Integer)a)) {
            throw new DictionaryException("Heap address is not instantiated!\n");
        }
        return (V) this.memory.get((Integer)a);
    }

    @Override
    public Set<A> keys() {
        return (Set<A>) this.memory.keySet();
    }
}
