package repository;

import model.adt.*;
import model.exception.RepoException;
import model.exception.StmtException;
import model.expression.*;
import model.state.PrgState;
import model.statement.*;
import model.type.BoolType;
import model.type.IntType;
import model.type.RefType;
import model.type.StringType;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static model.expression.RelationalOperation.GREATER;

public class Repository implements IRepository {
    private List<PrgState> stateList;
    private String logFilePath;
    private IStmt initialStatement;

    public Repository(String logFilePath) {
        this.stateList = new ArrayList<>();
        this.logFilePath = logFilePath;

        try {
            PrintWriter writer = new PrintWriter(logFilePath);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException _) {}
    }

    @Override
    public void addPrgState(PrgState state) {
        this.stateList.add(state);
    }

    @Override
    public void logPrgState(PrgState state) throws RepoException {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            pw.println(state);
            pw.close();
        } catch (IOException ex) {
            throw new RepoException(ex.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.stateList;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        this.stateList = newList;
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

    private void generateState5() {
        this.initialStatement =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                new CompStmt(new PrintStmt(new ArithExp(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))), new ValueExp(new IntValue(5)), '+')),
                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                                                                new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                                                                        new PrintStmt(new ReadHeapExp(new VarExp("v")))))))))));
    }

    private void generateState6() {
        this.initialStatement =  new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
                                new CompStmt(new NewStmt("a", new VarExp("v")),
                                        new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                                                new CompStmt(new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))),
                                                        new NewStmt("v", new ValueExp(new IntValue(50)))))))));
    }

    private void generateState7() {
        this.initialStatement =  new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), GREATER),
                                new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-'))),
                                new PrintStmt(new VarExp("v")))));
    }

    private void generateState8() {
        this.initialStatement = new CompStmt(
          new VarDeclStmt("v", new IntType()), new CompStmt(
                  new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
                          new AssignStmt("v", new ValueExp(new IntValue(10))), new CompStmt(
                                  new NewStmt("a", new ValueExp(new IntValue(22))), new CompStmt(
                                          new ForkStmt(new CompStmt(
                                                  new WriteHeapStmt("a", new ValueExp(new IntValue(30))), new CompStmt(
                                                          new AssignStmt("v", new ValueExp(new IntValue(32))), new CompStmt(
                                                                  new PrintStmt(new VarExp("v")),
                                                                    new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                          )
                                          )
                                          )
                                          ), new CompStmt(
                                                  new PrintStmt(new VarExp("v")), new PrintStmt(new ReadHeapExp(new VarExp("a")))
                                            )
                                )
                        )
                )
            )
        );
    }

    private void generateState9() {
        // Ref inv v; new(v, 20); Ref Ref int a; new(a, v); fork(Ref int b; new(v,30); new(b,v););
        this.initialStatement = new CompStmt(
          new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                  new NewStmt("v", new ValueExp(new IntValue(20))), new CompStmt(
                          new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                                  new NewStmt("a", new VarExp("v")), new CompStmt(
                                      new ForkStmt(
                                              new CompStmt(
                                                      new VarDeclStmt("b", new RefType(new RefType(new IntType()))), new CompStmt(
                                                        new NewStmt("v", new ValueExp(new IntValue(30))),
                                                        new NewStmt("b", new VarExp("v"))
                                              )
                                              )
                                      ), new CompStmt(
                                              new PrintStmt(new ReadHeapExp(new VarExp("v"))), new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a"))))
        )
        )
        )
        )
        )
        );
    }

    @Override
    public void setState(int option) throws RepoException{
        switch (option) {
            case 1 -> this.generateState1();
            case 2 -> this.generateState2();
            case 3 -> this.generateState3();
            case 4 -> this.generateState4();
            case 5 -> this.generateState5();
            case 6 -> this.generateState6();
            case 7 -> this.generateState7();
            case 8 -> this.generateState8();
            case 9 -> this.generateState9();
            default -> this.initialStatement = new NopStmt();
        }
        try {
            this.initialStatement.typeCheck(new Dictionary<>());
        } catch (StmtException e) {
            throw new RepoException(e.getMessage());
        }
        this.stateList.clear();
        this.stateList.add(new PrgState(new ExeStack<>(), new SymTable<>(), new Out<>(), this.initialStatement, new FileTable<>(), new Heap()));
    }
}
