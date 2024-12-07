package repository;

import model.exception.RepoException;
import model.state.PrgState;

import java.util.List;

public interface IRepository {
    void addPrgState(PrgState state);
    void logPrgState(PrgState state) throws RepoException;
    void setState(int option) throws RepoException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
}
