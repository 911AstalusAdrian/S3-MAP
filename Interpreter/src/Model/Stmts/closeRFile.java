package Model.Stmts;

import Model.Exceptions.EvalException;
import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.Expressions.Exp;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class closeRFile implements IStmt{

    Exp exp;

    public closeRFile(Exp e){exp = e;}




    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t = exp.typecheck(typeEnv);
        if(t.equals(new StringType()))
            return typeEnv;
        else throw new TypecheckException("closeRFile needs a String as an argument.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {

        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        try {
            Value val = exp.eval(state.getTbl(), state.getHeap());
            if (!val.getType().equals(new StringType()))
                throw new ExecException("closeRFile - Expression does not evaluate to StringType");
            StringValue fileName = (StringValue) val;
            if (!fileTable.isDefined(fileName.getVal()))
                throw new ExecException("closeRFile - This value is not defined in the fileTable");
            BufferedReader reader = fileTable.get(fileName.getVal());
            try {
                reader.close();
            } catch (IOException e) {
                throw new ExecException("closeRFile - Could not close file.");
            }
            fileTable.remove(fileName.getVal());
            return null;
        }
        catch (EvalException ee){
            throw new ExecException("closeRFile exec - " + ee.toString());
        }
    }

    public String toString(){
        return "CloseRFile(" + exp + ")";
    }
}
