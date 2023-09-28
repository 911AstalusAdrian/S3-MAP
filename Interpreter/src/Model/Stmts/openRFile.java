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
import java.io.FileReader;
import java.io.IOException;

public class openRFile implements IStmt{

    Exp exp;

    public openRFile(Exp e){exp = e;}


    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        Type t = exp.typecheck(typeEnv);
        if (t.equals(new StringType()))
            return typeEnv;
        else throw new TypecheckException("openRFile - Requires a string.");
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        try {
            Value val = exp.eval(state.getTbl(), state.getHeap());
            if (!val.getType().equals(new StringType()))
                throw new ExecException("openRFile - Invalid String value in openRFile");
            StringValue fileName = (StringValue) val;
            MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
            if (fileTable.isDefined(fileName.getVal()))
                throw new ExecException("openRFile - " + fileName + "is already opened.");
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(fileName.getVal()));
            } catch (IOException e) {
                throw new ExecException("openRFile - " + fileName + " could not be opened.");
            }
            fileTable.update(fileName.getVal(), reader);
            return null;
        }
        catch (EvalException ee){throw new ExecException("openRFile exec - " + ee.toString());}
    }

    public String toString(){
        return "OpenReadFile(" + exp.toString() + ")";
    }
}
