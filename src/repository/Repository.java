package repository;

import model.adt.*;
import model.exception.RepoException;
import model.expression.ArithExp;
import model.expression.ValueExp;
import model.expression.VarExp;
import model.state.PrgState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> stateList;
    private String logFilePath;
    private int  currentProgramState;
    private IStmt initialStatement;

    public Repository(String logFilePath) {
        this.stateList = new ArrayList<>();
        this.logFilePath = logFilePath;
        this.currentProgramState = 0;

        try {
            PrintWriter writer = new PrintWriter(logFilePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException _) {}
    }

    @Override
    public PrgState getCrtState() {
        return this.stateList.get(this.currentProgramState);
    }

    @Override
    public void addPrgState(PrgState state) {
        this.stateList.add(state);
    }

    @Override
    public void logPrgState() throws RepoException {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            pw.println(this.getCrtState());
            pw.close();
        } catch (IOException ex) {
            throw new RepoException(ex.getMessage());
        }
    }

    private void generateState1() {
        this.initialStatement = new CompStmt(new VarDeclStmt("v", new IntType()),
                            new CompStmt(new AssignStmt("v",new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
    }

    private void generateState2() {
        this.initialStatement = new CompStmt(new VarDeclStmt("a",new IntType()),
                new CompStmt(new VarDeclStmt("b",new IntType()),
                        new CompStmt(new AssignStmt("a", new ArithExp(new ValueExp(new IntValue(2)),new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*'), '+')),
                                new CompStmt(new AssignStmt("b",new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)), '+')),
                                        new PrintStmt(new VarExp("b"))))));
    }

    private void generateState3() {
        this.initialStatement = new CompStmt(new VarDeclStmt("a",new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new
                                        IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new VarExp("v"))))));
    }

    private void generateState4() {
        this.initialStatement =  new CompStmt(new VarDeclStmt("varf",new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("src/files/test.in"))),
                        new CompStmt(new OpenRFile(new VarExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                        new CompStmt(new ReadFile(new VarExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
                                                                        new CloseRFile(new VarExp("varf"))))))))));
    }

    @Override
    public void setState(int option) {
        switch (option) {
            case 1 -> this.generateState1();
            case 2 -> this.generateState2();
            case 3 -> this.generateState3();
            case 4 -> this.generateState4();
            default -> this.initialStatement = new NopStmt();
        };
        this.stateList.clear();
        this.stateList.add(new PrgState(new ExeStack<>(), new SymTable<>(), new Out<>(), this.initialStatement, new FileTable<>(), new Heap()));
    }
}
