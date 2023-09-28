package View;
import Controller.Controller;
import Model.Exceptions.EvalException;
import Model.Expressions.*;
import Model.States.*;
import Model.Stmts.*;
import Model.Stmts.IStmt;
import Model.Types.*;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repo.IRepo;
import Repo.Repo;
import Model.PrgState;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.*;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
//                                        VarExp("v"))))));
//
//        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()),
//                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                        new CompStmt(new openRFile(new VarExp("varf")),
//                                new CompStmt(new VarDeclStmt("varc", new IntType()),
//                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                                                new CompStmt(new PrintStmt(new VarExp("varc")),
//                                                        new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                                                                new CompStmt(new PrintStmt(new VarExp("varc")),
//                                                                        new closeRFile(new VarExp("varf"))))))))));
//
//        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
//                new CompStmt(new VarDeclStmt("b", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
//                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
//                                        new IfStmt(new RelationalExp( new VarExp("a"), "<",
//                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
//                                                new PrintStmt(new VarExp("b")))))));
//
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType( new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
//
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                                new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
//
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
//                                        new PrintStmt(new ArithExp('+', new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));
//
//        IStmt ex9 = new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
//                        new WhileStmt(new RelationalExp(new VarExp("v"), ">",
//                                new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")),
//                                new AssignStmt("v", new ArithExp('-',new VarExp("v"), new ValueExp(new IntValue(1)))))))), new PrintStmt(new VarExp("v")));
//
//        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new New("v", new ValueExp(new IntValue(30))),
//                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
//
//        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                                new CompStmt(new New("a", new ValueExp(new IntValue(22))),
//                                        new CompStmt(new Fork(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
//                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
//                                                new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        String one = "Ref int a; Ref int b; int v;new(a,0); new(b,0);wh(a,1); wh(b,2);v=(rh(a)<rh(b))?100:200;print(v);v= ((rh(b)-2)>rh(a))?100:200;print(v);";
        String two = "Ref int v1; Ref int v2; Ref int v3; int cnt;\n" +
                "new(v1,2);new(v2,3);new(v3,4);newLatch(cnt,rH(v2));\n" +
                "fork(wh(v1,rh(v1)*10);print(rh(v1));countDown(cnt);\n" +
                "fork(wh(v2,rh(v2)*10);print(rh(v2));countDown(cnt);\n" +
                "fork(wh(v3,rh(v3)*10);print(rh(v3));countDown(cnt))));\n" +
                "await(cnt);\n" +
                "print(100);\n" +
                "countDown(cnt);\n" +
                "print(100)";

        try{
            stage.setTitle("Toy Language Interpreter");  // Set window title
            ListView statementsList = new ListView(); // Create a list of statements
            statementsList.getItems().add("1. " + one);
            statementsList.getItems().add("2. " + two);
//            statementsList.getItems().add("1. " + ex1);
//            statementsList.getItems().add("2. " + ex2);
//            statementsList.getItems().add("3. " + ex3);
//            statementsList.getItems().add("4. " + ex4);
//            statementsList.getItems().add("5. " + ex5);
//            statementsList.getItems().add("6. " + ex6);
//            statementsList.getItems().add("7. " + ex7);
//            statementsList.getItems().add("8. " + ex8);
//            statementsList.getItems().add("9. " + ex9);
//            statementsList.getItems().add("10. " + ex10);
//            statementsList.getItems().add("11. " + ex11);
            statementsList.setPrefSize(675, 260);

            Button selectStatementButton = new Button("Run example.");
            selectStatementButton.setOnAction(e-> {
                try {
                    int index = statementsList.getSelectionModel().getSelectedIndex()+1;
                    if(index == 0){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid example selected. Choose another one!", ButtonType.OK);
                        alert.showAndWait();
                        System.out.println("Example selection error - Invalid index chosen from the list.");
                    }
                    else
                        showSecondWindow(stage, statementsList.getSelectionModel().getSelectedIndex()+1);
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            });

            GridPane pane = new GridPane();
            pane.setAlignment(Pos.TOP_CENTER);
            pane.add(statementsList, 1, 0);
            pane.add(selectStatementButton, 1, 1);
            GridPane.setHalignment(selectStatementButton, HPos.CENTER); //CENTER THE BUTTON ON THE GRIDPANE CELL
            GridPane.setValignment(selectStatementButton, VPos.CENTER);


            Scene mainScene = new Scene(pane, 675, 300);
            stage.setScene(mainScene);
            stage.show();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
            alert.showAndWait();

        }

    }

    public static void main(String[] args){
        Application.launch(args);
    }

    static void typeCheck(IStmt statement){
        MyIDictionary<String, Type> check = new MyDictionary<>();
        try{
            statement.typecheck(check);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void showSecondWindow(Stage stage, Integer index) throws EvalException {
        IStmt statement = switch (index) {
//            case 1 -> new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//            case 2 -> new CompStmt(new VarDeclStmt("a", new IntType()),
//                    new CompStmt(new VarDeclStmt("b", new IntType()),
//                            new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntValue(2)), new ArithExp('*', new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                    new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
//            case 3 -> new CompStmt(new VarDeclStmt("a", new BoolType()),
//                    new CompStmt(new VarDeclStmt("v", new IntType()),
//                            new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                    new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
//                                            VarExp("v"))))));
//            case 4 -> new CompStmt(new VarDeclStmt("varf", new StringType()),
//                    new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue("test.in"))),
//                            new CompStmt(new openRFile(new VarExp("varf")),
//                                    new CompStmt(new VarDeclStmt("varc", new IntType()),
//                                            new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                                                            new CompStmt(new readFile(new VarExp("varf"), "varc"),
//                                                                    new CompStmt(new PrintStmt(new VarExp("varc")),
//                                                                            new closeRFile(new VarExp("varf"))))))))));
//            case 5 -> new CompStmt(new VarDeclStmt("a", new IntType()),
//                    new CompStmt(new VarDeclStmt("b", new IntType()),
//                            new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
//                                    new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
//                                            new IfStmt(new RelationalExp(new VarExp("a"), "<",
//                                                    new VarExp("b")), new PrintStmt(new VarExp("a")),
//                                                    new PrintStmt(new VarExp("b")))))));
//            case 6 -> new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                    new CompStmt(new New("a", new VarExp("v")),
//                                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
//            case 7 -> new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                    new CompStmt(new New("a", new VarExp("v")),
//                                            new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                                    new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
//            case 8 -> new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                            new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                    new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
//                                            new PrintStmt(new ArithExp('+', new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));
//            case 9 -> new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
//                            new WhileStmt(new RelationalExp(new VarExp("v"), ">",
//                                    new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")),
//                                    new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntValue(1)))))))), new PrintStmt(new VarExp("v")));
//            case 10 -> new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                    new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                    new CompStmt(new New("a", new VarExp("v")),
//                                            new CompStmt(new New("v", new ValueExp(new IntValue(30))),
//                                                    new PrintStmt(new rH(new rH(new VarExp("a")))))))));
//            case 11 -> new CompStmt(new VarDeclStmt("v", new IntType()),
//                    new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                                    new CompStmt(new New("a", new ValueExp(new IntValue(22))),
//                                            new CompStmt(new Fork(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
//                                                    new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                                            new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
//                                                    new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));
            case 1 -> new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                    new CompStmt(new VarDeclStmt("b", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("v", new IntType()),
                                    new CompStmt(new New("a", new ValueExp(new IntValue(0))),
                                            new CompStmt(new New("b", new ValueExp(new IntValue(0))),
                                                    new CompStmt(new wH("a", new ValueExp(new IntValue(1))),
                                                            new CompStmt(new wH("b", new ValueExp(new IntValue(2))),
                                                                    new CompStmt(new CondAssignStmt("v", new RelationalExp(new rH(new VarExp("a")), "<", new rH(new VarExp("b"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                            new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                    new CompStmt(
                                                                                            new CondAssignStmt("v",new RelationalExp(new ArithExp('-', new rH(new VarExp("b")), new ValueExp(new IntValue(2))), ">", new rH(new VarExp("a"))), new ValueExp(new IntValue(100)), new ValueExp(new IntValue(200))),
                                                                                            new PrintStmt(new VarExp("v"))))))))))));

            case 2 -> new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                    new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                            new CompStmt(new VarDeclStmt("v3", new RefType(new IntType())),
                                    new CompStmt(new VarDeclStmt("cnt", new IntType()),
                                            new CompStmt(new New("v1", new ValueExp(new IntValue(2))),
                                                    new CompStmt(new New("v2", new ValueExp(new IntValue(3))),
                                                            new CompStmt(new New("v3", new ValueExp(new IntValue(4))),
                                                                    new CompStmt(new newLatchStmt("cnt", new rH(new VarExp("v2"))),
                                                                            new CompStmt(new Fork(new CompStmt(new wH("v1", new ArithExp('*', new rH(new VarExp("v1")), new ValueExp(new IntValue(10)))),
                                                                                    new CompStmt(new PrintStmt(new rH(new VarExp("v1"))),
                                                                                            new CompStmt(new CountDownStmt("cnt"),
                                                                                                    new Fork(new CompStmt(new wH("v2", new ArithExp('*', new rH(new VarExp("v2")), new ValueExp(new IntValue(10)))),
                                                                                                            new CompStmt(new PrintStmt(new rH(new VarExp("v2"))),
                                                                                                                    new CompStmt(new CountDownStmt("cnt"),
                                                                                                                            new Fork(new CompStmt(new wH("v3", new ArithExp('*', new rH(new VarExp("v3")), new ValueExp(new IntValue(10)))),
                                                                                                                                    new CompStmt(new PrintStmt(new rH(new VarExp("v3"))),
                                                                                                                                            new CountDownStmt("cnt")))))))))))),
                                                                                    new CompStmt(new AwaitStmt("cnt"),
                                                                                            new CompStmt(new PrintStmt(new ValueExp(new IntValue(100))),
                                                                                                    new CompStmt(new CountDownStmt("cnt"),
                                                                                                            new PrintStmt(new ValueExp(new IntValue(100)))))))))))))));
            default -> null;
        };
        try {
            System.out.println(statement.toString());
            typeCheck(statement);
            PrgState programState = new PrgState(statement);
            IRepo programRepo = new Repo("log" + index.toString() + ".txt");
            programRepo.addState(programState);
            Controller programController = new Controller(programRepo);
            GridPane windowGrid = new GridPane();
            windowGrid.setPadding(new Insets(10, 10, 10, 10));

            TextField prgStatesCount = new TextField(programRepo.getPrgStateCount().toString()); // the number of PrgStates as a Text Field
            prgStatesCount.setEditable(false);
            Label countLabel = new Label("PrgState count");
            countLabel.setFont(new Font("Arial", 18));
            countLabel.setStyle("-fx-font-weight: bold");
            VBox count = new VBox();
            count.getChildren().addAll(countLabel, prgStatesCount);
            count.setSpacing(5);
            count.setAlignment(Pos.CENTER);
            windowGrid.add(count, 0, 0);


            Label heapLabel = new Label("Heap Table");
            heapLabel.setFont(new Font("Arial", 18));
            heapLabel.setStyle("-fx-font-weight: bold");
            VBox heap = new VBox();
            TableView<Map.Entry<String, String>> heapTable = new TableView<>();
            heapTable.setEditable(true);
            TableColumn<Map.Entry<String, String>, String> addressCol = new TableColumn<>("Address");
            TableColumn<Map.Entry<String, String>, String> valueCol = new TableColumn("Value");
            addressCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
            valueCol.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));

            heapTable.getColumns().addAll(addressCol, valueCol);
            heap.getChildren().addAll(heapLabel, heapTable);
            heap.setSpacing(5);
            heap.setAlignment(Pos.CENTER);
            windowGrid.add(heap, 1, 0);


            Label outLabel = new Label("Out");
            outLabel.setFont(new Font("Arial", 18));
            outLabel.setStyle("-fx-font-weight: bold");
            VBox out = new VBox();
            ListView outList = new ListView();
            out.getChildren().addAll(outLabel, outList);
            out.setSpacing(5);
            out.setAlignment(Pos.CENTER);
            windowGrid.add(out, 2, 0);


            Label fTableLabel = new Label("File Table");
            fTableLabel.setFont(new Font("Arial", 18));
            fTableLabel.setStyle("-fx-font-weight: bold");
            VBox fileTable = new VBox();
            ListView fileTableList = new ListView();
            fileTable.getChildren().addAll(fTableLabel, fileTableList);
            fileTable.setSpacing(5);
            fileTable.setAlignment(Pos.CENTER);
            windowGrid.add(fileTable, 0, 1);


            Label identifiersLabel = new Label("PrgState Identifiers");
            identifiersLabel.setFont(new Font("Arial", 18));
            identifiersLabel.setStyle("-fx-font-weight: bold");
            ListView prgStateIdentifiersList = new ListView();
            prgStateIdentifiersList.getItems().add(programState);
            prgStateIdentifiersList.setCellFactory(TextFieldListCell.forListView(new StringConverter<PrgState>() {
                @Override
                public String toString(PrgState programState) {
                    return Integer.toString(programState.getID());
                }

                @Override
                public PrgState fromString(String s) {
                    return null;
                }
            }));


            VBox prgStateIdentifiers = new VBox();
            prgStateIdentifiers.getChildren().addAll(identifiersLabel, prgStateIdentifiersList);
            prgStateIdentifiers.setSpacing(5);
            prgStateIdentifiers.setAlignment(Pos.CENTER);
            windowGrid.add(prgStateIdentifiers, 1, 1);


            Label symTableLabel = new Label("Symbol Table");
            symTableLabel.setFont(new Font("Arial", 18));
            symTableLabel.setStyle("-fx-font-weight: bold");
            TableView<Map.Entry<String, String>> symTable = new TableView<>();
            symTable.setEditable(true);
            TableColumn<Map.Entry<String, String>, String> varName = new TableColumn<>("Var Name");
            TableColumn<Map.Entry<String, String>, String> varValue = new TableColumn<>("Var Value");
            varName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey()));
            varValue.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getValue()));
            symTable.getColumns().addAll(varName, varValue);
            VBox sTable = new VBox();
            sTable.getChildren().addAll(symTableLabel, symTable);
            sTable.setSpacing(5);
            sTable.setAlignment(Pos.CENTER);
            windowGrid.add(sTable, 2, 1);

            Label latchTableLabel = new Label("Latch Table");
            latchTableLabel.setFont(new Font("Arial", 18));
            latchTableLabel.setStyle("-fx-font-weight: bold");
            TableView<Map.Entry<String,String>> latchTable = new TableView<>();
            latchTable.setEditable(true);
            TableColumn<Map.Entry<String,String>, String> latchLocationCol = new TableColumn<>("Location");
            TableColumn<Map.Entry<String,String>, String> latchValueCol = new TableColumn<>("Value");
            latchTable.getColumns().addAll(latchLocationCol, latchValueCol);
            VBox lTable = new VBox();
            lTable.getChildren().addAll(latchTableLabel, latchTable);
            lTable.setSpacing(5);
            lTable.setAlignment(Pos.CENTER);
            windowGrid.add(lTable, 1, 2);



            Label stackLabel = new Label("Execution Stack");
            stackLabel.setFont(new Font("Arial", 18));
            stackLabel.setStyle("-fx-font-weight: bold");
            ListView exeStackList = new ListView();
            VBox exeStack = new VBox();
            exeStack.getChildren().addAll(stackLabel, exeStackList);
            exeStack.setSpacing(5);
            exeStack.setAlignment(Pos.CENTER);
            windowGrid.add(exeStack, 0, 2);


            prgStateIdentifiersList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            prgStateIdentifiersList.getSelectionModel().selectedItemProperty().addListener((a, b, state) -> {
                if (state != null)
                    update((PrgState) state, symTable, exeStackList);

            });


            Button oneStep = new Button("Run One Step");
            oneStep.setAlignment(Pos.CENTER);


            oneStep.setOnAction(e -> {
                try {
                    programController.oneStepForAllPrg(programController.getRepo().getPrgList());
                } catch (InterruptedException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
                    alert.showAndWait();
                }

                exeStackList.getItems().clear();  //update ExeStack
                Stack<IStmt> newStack = programState.getStk().cloneStack();
                while (!newStack.isEmpty()) {
                    exeStackList.getItems().add(newStack.pop().toString());
                }

                outList.getItems().clear();        //update Out
                MyIList<Value> outTemp = programState.getOut();
                for (int i = 0; i < outTemp.size(); i++)
                    outList.getItems().add(outTemp.getElByIndex(i));


                prgStateIdentifiersList.getItems().clear(); // update prgState IDs
                for (PrgState p : programRepo.getPrgList())
                    prgStateIdentifiersList.getItems().add(p);


                ///symbol table
                symTable.getItems().clear();
                MyIDictionary<String, Value> symTbl = programState.getTbl();
                List<Map.Entry<String, String>> symTblList = new ArrayList<>();
                for (Map.Entry<String, Value> elem : symTbl.getContent().entrySet()) {
                    Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey(), elem.getValue().toString());
                    symTblList.add(el);
                }
                symTable.setItems(FXCollections.observableList(symTblList));
                symTable.refresh();


                // latchTable
                latchTable.getItems().clear();
                ILatch latchTbl = programState.getLatchTable();
                List<Map.Entry<String,String>> latchTblList = new ArrayList<>();
                for(Map.Entry<Integer,Integer> elem : latchTbl.getLatchTable().entrySet()){
                    Map.Entry<String,String> el = new AbstractMap.SimpleEntry<>(elem.getKey().toString(), elem.getValue().toString());
                    latchTblList.add(el);
                }
                latchTable.setItems(FXCollections.observableList(latchTblList));
                latchTable.refresh();

                //heap
                heapTable.getItems().clear();
                IHeap myHeap = programState.getHeap();
                List<Map.Entry<String, String>> heapTableList = new ArrayList<>();
                for (Map.Entry<Integer, Value> elem : myHeap.getHeapContent().entrySet()) {
                    Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey().toString(), elem.getValue().toString());
                    heapTableList.add(el);
                }
                heapTable.setItems(FXCollections.observableList(heapTableList));
                heapTable.refresh();

                prgStatesCount.setText(programRepo.getPrgStateCount().toString());

            });


            Button exit = new Button("Exit");
            exit.setAlignment(Pos.CENTER);
            windowGrid.add(oneStep, 2, 2);

            Scene newScene = new Scene(windowGrid, 800, 800);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Example " + index.toString());
            stage.hide();
            newStage.show();
        }
        catch (IOException | RuntimeException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    void update(PrgState state, TableView sT, ListView exeS){

        sT.getItems().clear();
        exeS.getItems().clear();

        //We are going to repopulate them back with the "new values" if it's the case
        Stack<IStmt> newStack = state.getStk().cloneStack();
        while(!newStack.isEmpty()) {
            exeS.getItems().add(newStack.pop().toString());
        }

        sT.getItems().clear();
        MyIDictionary<String, Value> symTbl= state.getTbl();
        List<Map.Entry<String,String>> symTblList = new ArrayList<>();
        for(Map.Entry<String, Value> elem: symTbl.getContent().entrySet()){
            Map.Entry<String, String> el = new AbstractMap.SimpleEntry<>(elem.getKey(), elem.getValue().toString());
            symTblList.add(el);
        }
        sT.setItems(FXCollections.observableList(symTblList));
        sT.refresh();
    }
}
