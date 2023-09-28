package Model.Stmts;

import Model.Exceptions.ExecException;
import Model.Exceptions.TypecheckException;
import Model.PrgState;
import Model.States.MyIDictionary;
import Model.Types.Type;
import Model.Values.Value;

public class VarDeclStmt implements IStmt{
    String name;
    Type type;

    public VarDeclStmt(String varname, Type vartype){name=varname; type=vartype;}

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws TypecheckException {
        typeEnv.update(name,type);
        return typeEnv;
    }

    @Override
    public PrgState execute(PrgState state) throws ExecException {
        MyIDictionary<String, Value> dict = state.getTbl();
        if(dict.isDefined(name))
            throw new ExecException("VarDecl - This variable is already defined!");
        else{
            dict.update(name, type.defaultValue());
        }
        return null;
    }

    public String toString(){
        return type.toString() + " " + name;
    }
}
