package model.adt;

import model.exception.StackException;

import java.util.Stack;

public class MyStack<T> implements IMyStack<T> {
    private Stack<T> stack;

    public MyStack() { this.stack = new Stack<T>(); }

    @Override
    public void push(T t) {
        this.stack.push(t);
    }

    @Override
    public T pop() throws StackException {
        if (this.stack.isEmpty()) throw new StackException("Stack is empty!\n");
        return this.stack.pop();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        this.stack.forEach((t) -> {
            str.append(t).append(" ");
        });
        return "Stack is: " + str + "\n";
    }
}
