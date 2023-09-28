package Model.Expressions;

import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExp implements Exp{
    Exp e1, e2;
    int op; // 1-and, 2-or

    public LogicExp(String operation, Exp exp1, Exp exp2) throws EvalException {
        e1 = exp1;
        e2 = exp2;
        switch (operation){
            case "&&" -> op=1;
            case "||" -> op=2;
            default -> throw new EvalException("Invalid Logic Operation!");
        }
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t1, t2;
        t1 = e1.typecheck(typeEnv);
        t2 = e2.typecheck(typeEnv);

        if(t1.equals(new BoolType()))
            if(t2.equals(new BoolType()))
                return new BoolType();
            else throw new TypecheckException("Logic - Second operand is not a boolean!");
        else throw new TypecheckException("Logic - First operand is not a boolean!");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {
        Value v1, v2;
        v1 = e1.eval(tbl, myHeap);
        if(v1.getType().equals(new BoolType())){
            v2 = e2.eval(tbl, myHeap);
            if(v2.getType().equals(new BoolType())){
                BoolValue i1 = (BoolValue)v1;
                BoolValue i2 = (BoolValue)v2;
                boolean n1,n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                if(op == 1) return new BoolValue(n1 && n2);
                if(op == 2) return new BoolValue(n1 || n2);
            }
            else throw new EvalException("Logic - Second Operand is not a boolean.");
        }
        else throw new EvalException("Logic - First Operand is not a boolean.");
        return new BoolValue(false);
    }

    public String toString(){
        String out = "";
        switch (op){
            case 1 -> {out = e1.toString() + "&&" + e2.toString();}
            case 2 -> {out = e2.toString() + "||" + e2.toString();}
        }
        return out;
    }
}
