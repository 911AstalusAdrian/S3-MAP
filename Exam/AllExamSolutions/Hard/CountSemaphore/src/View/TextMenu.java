package View;

import Exceptions.MyException;
import Model.ADT.MyDict;
import View.Command.Command;

import java.util.Scanner;

public class TextMenu {
    private final MyDict<String, Command> commands;

    public TextMenu() {
        this.commands = new MyDict<>();
    }

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    public void printMenu() throws MyException {
        for (String key : commands.keySet()) {
            String line = String.format("%4s:%s", commands.getValue(key).getKey(), commands.getValue(key).getDescription());
            System.out.println(line);
        }
    }

    public void show() throws MyException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();
            System.out.println("Input your option!");
            String key = scanner.nextLine();
            Command com = commands.getValue(key);
            if (com == null) {
                System.out.println("Invalid option!");
            } else {
                com.execute();
            }
        }
    }
}
