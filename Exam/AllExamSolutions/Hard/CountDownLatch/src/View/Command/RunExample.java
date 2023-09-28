package View.Command;

import Controller.Controller;
import Exceptions.MyException;

import java.io.IOException;

public class RunExample extends Command {
    private final Controller controller;

    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try {
            controller.fullExecution();
        } catch (MyException | IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
