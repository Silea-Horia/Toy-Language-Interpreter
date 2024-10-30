package model.state;

import model.adt.IMyDictionary;
import model.adt.IMyList;
import model.adt.IMyStack;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IMyStack<IStmt> exeStack;
    private IMyDictionary<String, IValue> symTable;
    private IMyList<IValue> out;
    private IStmt originalProgram;
    private IMyDictionary<StringValue, BufferedReader> fileTable;

    public PrgState(IMyStack<IStmt> exeStack, IMyDictionary<String, IValue> symTable, IMyList<IValue> out, IStmt originalProgram, IMyDictionary<StringValue, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
    }

    public IMyStack<IStmt> getExeStack() { return this.exeStack; }

    public IMyList<IValue> getOut() { return this.out; }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IMyDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public void setExeStack(IMyStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setSymTable(IMyDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }

    public void setOut(IMyList<IValue> out) {
        this.out = out;
    }

    public void setOriginalProgram(IStmt originalProgram) {
        this.originalProgram = originalProgram;
    }

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return "PrgState is:\n" + this.exeStack + this.symTable + this.out + "\n";
    }

    public String toStringFile() {
        StringBuilder s = new StringBuilder("File table:\n");
        for (StringValue v : this.fileTable.keys()) {
            s.append(v.getValue()).append("\n");
        }
        return s.toString();
    }

    
}
