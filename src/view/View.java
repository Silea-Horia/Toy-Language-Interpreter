package view;

import controller.Controller;

import java.io.IOException;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    private void getSetOption() {
        System.out.println("Choose a program predefined program(1,2,3): ");
        try {
            int opt = System.in.read();
            this.controller.generateInitialState(opt);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void runProgram() {
        getSetOption();
        controller.allStep();
    }
}
