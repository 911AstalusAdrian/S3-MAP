package Model.Expressions;
import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.Value;

public class ArithExp implements Exp{
    Exp e1, e2;
    int op; //1-plus, 2-minus, 3-star, 4-divide

    public ArithExp(char operation, Exp exp1, Exp exp2) throws EvalException {
        e1 = exp1;
        e2 = exp2;
        switch (operation) {
            case '+' -> op = 1;
            case '-' -> op = 2;
            case '*' -> op = 3;
            case '/' -> op = 4;
            default -> throw new EvalException("Invalid arithmetic operation!");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);
        if (t1.equals(new IntType()))
            if (t2.equals(new IntType()))
                return new IntType();
            else throw new TypecheckException("Arith - Second operand is not an integer!");
        else throw new TypecheckException("Arith - First operand is not an integer!");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {
        Value v1,v2;
        v1 = e1.eval(tbl, myHeap);
        if(v1.getType().equals(new IntType())){
            v2 = e2.eval(tbl, myHeap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue)v1;
                IntValue i2 = (IntValue)v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op == 1) return new IntValue(n1+n2);
                if(op == 2) return new IntValue(n1-n2);
                if(op == 3) return new IntValue(n1*n2);
                if(op == 4){
                    if(n2 == 0) throw new EvalException("Arith - Division by zero!");
                    else return new IntValue(n1/n2);
                }
            }
            else throw new EvalException("Arith - Second Operand is not an Integer!");
        }
        else throw new EvalException("Arith - First Operand is not an Integer!");
        return new IntValue(0);
    }

    public String toString(){
        String out="";
        switch (op){
            case 1 -> {out =  e1.toString() + '+' + e2.toString();}
            case 2 -> {out =  e1.toString() + '-' + e2.toString();}
            case 3 -> {out =  e1.toString() + '*' + e2.toString();}
            case 4 -> {out = e1.toString() + '/' + e2.toString();}
        }
        return out;
    }
}
