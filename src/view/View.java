package view;

import controller.Controller;
import model.exception.ControllerException;

import java.io.IOException;
import java.util.Scanner;

public class View {
    private Controller controller;

    public View(Controller controller) {
        this.controller = controller;
    }

    private void getSetOption() {
        System.out.println("Choose a program predefined program(1,2,3,4): ");
        Scanner input = new Scanner(System.in);
        int opt = input.nextInt();
        this.controller.generateInitialState(opt);
    }

    private void printMenu() {
        System.out.print("\nMain menu:\n1.Select a default program to run\n2.Set display flag\n3.Exit\n>>");
    }

    public void runProgram() {
        Scanner input = new Scanner(System.in);
        int opt = -1;
        while (opt != 3) {
            this.printMenu();
            opt = input.nextInt();
            switch (opt) {
                case 1:
                    getSetOption();
                    try {
                        controller.allStep();
                    } catch (ControllerException e) {
                        System.out.println(e.getMessage());
                    }
                    continue;
                case 2:
                    this.controller.setDisplayFlag(!this.controller.getDisplayFlag());
                    continue;
                case 3:
                    break;
                default:
                    System.out.println("Bad option!\n");
            }
        }
    }
}
