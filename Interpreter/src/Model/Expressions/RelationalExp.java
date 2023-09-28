package Model.Expressions;

import Model.Exceptions.EvalException;
import Model.Exceptions.TypecheckException;
import Model.States.IHeap;
import Model.States.MyIDictionary;
import Model.Types.BoolType;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntValue;
import Model.Values.Value;

public class RelationalExp implements Exp{

    Exp first, second;
    String operator;

    public RelationalExp(Exp one, String op, Exp two){
        first = one;
        second = two;
        operator = op;
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t1, t2;
        t1 = first.typecheck(typeEnv);
        t2 = second.typecheck(typeEnv);
        if(t1.equals(new IntType()))
            if(t2.equals(new IntType()))
                return new BoolType();
            else throw new TypecheckException("RelExp - Second operator is not an integer.");
        else throw new TypecheckException("RelExp - First operator is not an integer.");
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap myHeap) throws EvalException {
        Value firstVal = first.eval(tbl, myHeap);
        if(!firstVal.getType().equals(new IntType()))
            throw new EvalException("RelExp - Invalid type for the first expression.");

        Value secondVal = second.eval(tbl, myHeap);
        if(!secondVal.getType().equals(new IntType()))
            throw new EvalException("RelExp - Invalid type for the second expression.");

        return switch (operator){
            case "<" -> new BoolValue(((IntValue)firstVal).getVal() < ((IntValue)secondVal).getVal());
            case "<=" -> new BoolValue(((IntValue)firstVal).getVal() <= ((IntValue)secondVal).getVal());
            case ">" -> new BoolValue(((IntValue)firstVal).getVal() > ((IntValue)secondVal).getVal());
            case ">-" -> new BoolValue(((IntValue)firstVal).getVal() >= ((IntValue)secondVal).getVal());
            case "==" -> new BoolValue(((IntValue)firstVal).getVal() == ((IntValue)secondVal).getVal());
            case "!=" -> new BoolValue(((IntValue)firstVal).getVal() != ((IntValue)secondVal).getVal());
            default -> throw new EvalException("RelExp - Invalid operand.");
        };
    }

    public String toString(){
        return first.toString() + " " + operator + " " + second.toString();
    }
}
