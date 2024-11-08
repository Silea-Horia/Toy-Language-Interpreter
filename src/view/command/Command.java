package view.command;

public class Command {
    private String key, description;

    public Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public void execute() {};
}
