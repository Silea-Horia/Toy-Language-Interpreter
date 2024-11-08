package model.state;

import model.adt.FileTable;
import model.adt.IDictionary;
import model.adt.IList;
import model.adt.IStack;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IStmt originalProgram;
    private FileTable<StringValue, BufferedReader> fileTable;   // TODO MAKE THIS A CLASS

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable, IList<IValue> out, IStmt originalProgram, FileTable<StringValue, BufferedReader> fileTable) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = originalProgram.deepCopy();
        this.exeStack.push(originalProgram);
        this.fileTable = fileTable;
    }

    public IStack<IStmt> getExeStack() { return this.exeStack; }

    public IList<IValue> getOut() { return this.out; }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    public IDictionary<String, IValue> getSymTable() {
        return symTable;
    }

    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        return "PrgState is:\n" + this.exeStack + this.symTable + this.out + this.fileTable;
    }

    public String toStringFile() {
        StringBuilder s = new StringBuilder("File table:\n");
        for (StringValue v : this.fileTable.keys()) {
            s.append(v.getValue()).append("\n");
        }
        return s.toString();
    }

    
}
