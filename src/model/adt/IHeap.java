package model.adt;

import model.exception.DictionaryException;
import model.exception.StackException;
import model.value.IValue;

import java.util.Set;

public interface IHeap<A, V> {
    void insert(V v);
    void remove(A a) throws DictionaryException;
    boolean contains(A a);
    V lookup(A a) throws DictionaryException;
    Set<A> keys();
}
