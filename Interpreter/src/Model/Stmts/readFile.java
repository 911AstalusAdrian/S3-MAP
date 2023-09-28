package Model.Stmts;

import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.IntType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class readFile implements IStmt{

    Exp expr;
    String varName;

    public readFile(Exp e, String vn){
        expr = e;
        varName = vn;
    }


    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t1 = expr.typecheck(typeEnv);
        Type t2 = typeEnv.get(varName);

        if(!t1.equals(new StringType()))
            throw new TypecheckException("readFile - requires a string first.");

        if(!t2.equals(new IntType()))
            throw new TypecheckException("readFile - requires an Int too.");

        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIDictionary<String, Value> symTable = state.getTbl();
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        if(!symTable.isDefined(varName))
            throw new ExecException("readFile - " + varName + " is not in the symTable.");
        Value val = symTable.get(varName);
        if(!val.getType().equals(new IntType()))
            throw new ExecException("readFile - value is not of IntType.");
        try {
            val = expr.eval(symTable, state.getHeap());
            if (!val.getType().equals(new StringType()))
                throw new ExecException("readFile - value does not evaluate to StringType.");

            StringValue castVal = (StringValue) val;
            if (!fileTable.isDefined(castVal.getVal()))
                throw new ExecException("readFile - the fileTable does not contain the StringValue");

            BufferedReader reader = fileTable.get(castVal.getVal());
            try {
                String line = reader.readLine();
                if (line == null)
                    line = "0";
                symTable.update(varName, new IntValue(Integer.parseInt(line)));
            } catch (IOException e) {
                throw new ExecException("readFile - Could not read from the file.");
            }
            return null;
        }
        catch (EvalException ee){throw new ExecException("readFile exec - " + ee.toString());}
    }

    public String toString(){
        return "Readfile(" + expr.toString() + varName + ")";
    }
}
