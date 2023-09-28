package model.expressions;

import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.BoolType;
import model.dataTypes.IType;
import model.values.BoolValue;
import model.values.IValue;

public class NegateExpression implements IExpression{
    private final IExpression exp;

    public NegateExpression(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue v;
        v = exp.evaluate(symtable, heap);
        if (!v.getType().equals(BoolType.T))
            throw new WrongType("Expression", BoolType.T, v.getType());
        BoolValue bv = (BoolValue) v;
        if(bv.getWrappedValue()) {
            return BoolValue.FALSE;
        }
        else {
            return BoolValue.TRUE;
        }
    }

    @Override
    public IExpression deep_copy() {
        return new NegateExpression(exp);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType type;
        type = exp.typeCheck(typeEnv);
        if (!type.equals(BoolType.T))
            throw new TypeCheckError("Expression is not a boolean");
        return BoolType.T;
    }

    @Override
    public String toString() {
        return "!" + exp.toString();
    }
}
