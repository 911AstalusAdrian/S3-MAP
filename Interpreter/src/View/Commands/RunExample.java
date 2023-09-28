package View.Commands;
import Controller.ControllerException;
import Controller.Controller;

public class RunExample extends Command{

    private final Controller controller;

    public RunExample(String key, String desc, Controller cont){
        super(key,desc);
        this.controller = cont;
    }

    @Override
    public void execute() {
        try {
            controller.allSteps();
        } catch (ControllerException e) {
            System.out.println(e.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
