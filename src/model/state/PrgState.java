package model.state;

import model.adt.*;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IExeStack<IStmt> exeStack;
    private ISymTable<String, IValue> symTable;
    private IOutList<IValue> out;
    private IStmt originalProgram;
    private IFileTable<StringValue, BufferedReader> fileTable;
    private IHeap heap;

    public PrgState(IExeStack<IStmt> exeStack, ISymTable<String, IValue> symTable, IOutList<IValue> out, IStmt originalProgram, IFileTable<StringValue, BufferedReader> fileTable, IHeap heap) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.heap = heap;
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
    }

    public IExeStack<IStmt> getExeStack() { return this.exeStack; }

    public IOutList<IValue> getOut() { return this.out; }

    public IStmt getOriginalProgram() {
        return this.originalProgram;
    }

    public IHeap getHeap() { return this.heap; }

    public ISymTable<String, IValue> getSymTable() {
        return this.symTable;
    }

    public IFileTable<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    @Override
    public String toString() {
        return "PrgState is:\n" + this.exeStack + this.symTable + this.out + this.fileTable + this.heap;
    }
}
