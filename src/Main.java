import controller.Controller;
import repository.IRepository;
import repository.Repository;
import view.View;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        IRepository repository = new Repository("src/files");  //TODO READ THE PATH FROM THE KEYBOARD
        Controller controller = new Controller(repository);
        View view = new View(controller);
        view.runProgram();
    }
}