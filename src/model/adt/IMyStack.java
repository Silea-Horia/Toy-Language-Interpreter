package model.adt;

import model.exception.StackException;

public interface IMyStack<T> {
    void push(T t);
    T pop() throws StackException;
    int size();
    boolean isEmpty();
}
