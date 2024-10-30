package repository;

import model.exception.RepoException;
import model.state.PrgState;

public interface IRepository {
    PrgState getCrtState();
    void addPrgState(PrgState state);
    void logPrgState() throws RepoException;
}
