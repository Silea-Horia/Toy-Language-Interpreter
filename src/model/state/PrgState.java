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
    private IMyDictionary<StringValue, BufferedReader> fileTable;   // TODO MAKE THIS A CLASS

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

    public IMyDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return "PrgState is:\n" + this.exeStack + this.symTable + this.out;
        // TODO ADD THE FILE TABLE AT THE END
    }

    public String toStringFile() {
        StringBuilder s = new StringBuilder("File table:\n");
        for (StringValue v : this.fileTable.keys()) {
            s.append(v.getValue()).append("\n");
        }
        return s.toString();
    }

    
}
