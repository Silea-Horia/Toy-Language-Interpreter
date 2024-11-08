package model.adt;

import model.exception.StackException;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExeStack<T> implements IStack<T> {
    private Deque<T> stack;

    public ExeStack() { this.stack = new ArrayDeque<T>(); }

    @Override
    public void push(T t) {
        this.stack.push(t);
    }

    @Override
    public T pop() throws StackException {
        if (this.stack.isEmpty()) throw new StackException("ExeStack is empty!\n");
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
            str.append(t).append("\n");
        });
        return "ExeStack:\n" + str;

        // TODO
        // Note that each stack position must be saved as a string that contains the statement which is on that position of the stack.
        // Since internally the statements (and expressions) are represented as binary tree
        // please use the left-root-right binary tree traversal in order to print in infix form the statements and the expressions
    }
}
