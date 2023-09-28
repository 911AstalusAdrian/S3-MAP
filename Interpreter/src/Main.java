//import Controller.Controller;
//import Model.*;
//import Model.Expressions.*;
//import Model.States.MyDictionary;
//import Model.States.MyIDictionary;
//import Model.Stmts.*;
//import Model.Stmts.IStmt;
//import Model.Types.*;
//import Model.Values.BoolValue;
//import Model.Values.IntValue;
//import Model.Values.StringValue;
//import Repo.IRepo;
//import Repo.Repo;
//import View.Commands.ExitCommand;
//import View.Commands.RunExample;
//import View.TextMenu;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.PrintWriter;
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//
//
//        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
//        typeCheck(ex1);
//        PrgState prg1 = new PrgState(ex1);
//        IRepo repo1 = new Repo("log1.txt");
//        repo1.addState(prg1);
//        Controller ctr1 = new Controller(repo1);
//
//
//        IStmt ex2 = new CompStmt( new VarDeclStmt("a",new IntType()),
//                new CompStmt(new VarDeclStmt("b",new IntType()),
//                        new CompStmt(new AssignStmt("a", new ArithExp('+',new ValueExp(new IntValue(2)),new ArithExp('*',new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
//                                new CompStmt(new AssignStmt("b",new ArithExp('+',new VarExp("a"), new ValueExp(new IntValue(1)))), new PrintStmt(new VarExp("b"))))));
//        typeCheck(ex2);
//        PrgState prg2 = new PrgState(ex2);
//        IRepo repo2 = new Repo("log2.txt");
//        repo2.addState(prg2);
//        Controller ctr2 = new Controller(repo2);
//
//
//        IStmt ex3 = new CompStmt(new VarDeclStmt("a",new BoolType()),
//                new CompStmt(new VarDeclStmt("v", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
//                                new CompStmt(new IfStmt(new VarExp("a"),new AssignStmt("v",new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3)))), new PrintStmt(new
//                                        VarExp("v"))))));
//        typeCheck(ex3);
//        PrgState prg3 = new PrgState(ex3);
//        IRepo repo3 = new Repo("log3.txt");
//        repo3.addState(prg3);
//        Controller ctr3 = new Controller(repo3);
//
//
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
//        typeCheck(ex4);
//        PrgState prg4 = new PrgState(ex4);
//        IRepo repo4 = new Repo("log4.txt");
//        repo4.addState(prg4);
//        Controller ctr4 = new Controller(repo4);
//
//        IStmt ex5 = new CompStmt(new VarDeclStmt("a", new IntType()),
//                new CompStmt(new VarDeclStmt("b", new IntType()),
//                        new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
//                                new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
//                                        new IfStmt(new RelationalExp( new VarExp("a"), "<",
//                                                new VarExp("b")),new PrintStmt(new VarExp("a")),
//                                                new PrintStmt(new VarExp("b")))))));
//        typeCheck(ex5);
//        PrgState prg5 = new PrgState(ex5);
//        IRepo repo5 = new Repo("log5.txt");
//        repo5.addState(prg5);
//        Controller ctr5 = new Controller(repo5);
//
//
//        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType( new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
//        typeCheck(ex6);
//        PrgState prg6 = new PrgState(ex6);
//        IRepo repo6 = new Repo("log6.txt");
//        repo6.addState(prg6);
//        Controller ctr6 = new Controller(repo6);
//
//
//        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                                new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))), new ValueExp(new IntValue(5)))))))));
//        typeCheck(ex7);
//        PrgState prg7 = new PrgState(ex7);
//        IRepo repo7 = new Repo("log7.txt");
//        repo7.addState(prg7);
//        Controller ctr7 = new Controller(repo7);
//
//
//        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new PrintStmt(new rH(new VarExp("v"))),
//                                new CompStmt(new wH("v", new ValueExp(new IntValue(30))),
//                                new PrintStmt(new ArithExp('+', new rH(new VarExp("v")), new ValueExp(new IntValue(5))))))));
//        typeCheck(ex8);
//        PrgState prg8 = new PrgState(ex8);
//        IRepo repo8 = new Repo("log8.txt");
//        repo8.addState(prg8);
//        Controller ctr8 = new Controller(repo8);
//
//
//        IStmt ex9 = new CompStmt(new CompStmt(new VarDeclStmt("v", new IntType()),
//            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
//                    new WhileStmt(new RelationalExp(new VarExp("v"), ">",
//                            new ValueExp(new IntValue(0))), new CompStmt(new PrintStmt(new VarExp("v")),
//                                new AssignStmt("v", new ArithExp('-',new VarExp("v"), new ValueExp(new IntValue(1)))))))), new PrintStmt(new VarExp("v")));
//        typeCheck(ex9);
//        PrgState prg9 = new PrgState(ex9);
//        IRepo repo9 = new Repo("log9.txt");
//        repo9.addState(prg9);
//        Controller ctr9 = new Controller(repo9);
//
//
//        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
//        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
//                new CompStmt(new New("v", new ValueExp(new IntValue(20))),
//                        new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
//                                new CompStmt(new New("a", new VarExp("v")),
//                                        new CompStmt(new New("v", new ValueExp(new IntValue(30))),
//                                                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
//        typeCheck(ex10);
//        PrgState prg10 = new PrgState(ex10);
//        IRepo repo10 = new Repo("log10.txt");
//        repo10.addState(prg10);
//        Controller ctr10 = new Controller(repo10);
//
//
//        /* int v; Ref int a; v = 10; new(a,22);
//         fork(wH(a,30);v=32;print(v);print(rH(a)));
//         print(v);print(rH(a));
//        at the end:
//            id = 1
//            symTable_1 = {v->10, a->(1,int)}
//
//            id = 10
//            symTable_10 = {v->32, a->(1,int)}
//
//            heap = {1->30}
//            out = {10, 30, 32, 30}
//         */
//
//        IStmt ex11 = new CompStmt(new VarDeclStmt("v", new IntType()),
//                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
//                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
//                                new CompStmt(new New("a", new ValueExp(new IntValue(22))),
//                                        new CompStmt(new Fork(new CompStmt(new wH("a", new ValueExp(new IntValue(30))),
//                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
//                                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))),
//                                        new CompStmt(new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));
//        typeCheck(ex11);
//        PrgState prg11 = new PrgState(ex11);
//        IRepo repo11 = new Repo("log11.txt");
//        repo11.addState(prg11);
//        Controller ctr11 = new Controller(repo11);
//
//
//        TextMenu menu = new TextMenu();
//        menu.addCommand(new ExitCommand("0", "exit"));
//        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
//        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
//        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
//        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
//        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
//        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
//        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
//        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
//        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
//        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
//        menu.addCommand(new RunExample("11", ex11.toString(), ctr11));
//        menu.show();
//
//    }
//
//    static void typeCheck(IStmt statement){
//        MyIDictionary<String, Type> check = new MyDictionary<>();
//        try{
//            statement.typecheck(check);
//        } catch (Exception e) {
//            System.out.println(e.toString());
//        }
//    }
//}