package model.expressions;

import model.customExceptions.TypeCheckError;
import model.customExceptions.WrongType;
import model.dataStructures.IDictionary;
import model.dataStructures.IHeap;
import model.dataTypes.IType;
import model.dataTypes.IntType;
import model.values.IValue;
import model.values.IntValue;

public class MULExpression implements IExpression{
    private final IExpression e1;
    private final IExpression e2;

    public MULExpression(IExpression e1, IExpression e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public IValue evaluate(IDictionary<String, IValue> symtable, IHeap<IValue> heap) throws Exception {
        IValue v1, v2;
        v1 = e1.evaluate(symtable, heap);
        if (!v1.getType().equals(IntType.T))
            throw new WrongType("Operand 1", IntType.T, v1.getType());
        v2 = e2.evaluate(symtable, heap);
        if (!v2.getType().equals(IntType.T))
            throw new WrongType("Operand 2", IntType.T, v1.getType());
        IntValue i1 = (IntValue) v1;
        IntValue i2 = (IntValue) v2;
        int op1 = i1.getWrappedValue();
        int op2 = i2.getWrappedValue();
        return new IntValue((op1*op2)-(op1+op2));
    }

    @Override
    public IExpression deep_copy() {
        return new MULExpression(e1, e2);
    }

    @Override
    public IType typeCheck(IDictionary<String, IType> typeEnv) throws Exception {
        IType type1;
        IType type2;
        type1 = e1.typeCheck(typeEnv);
        type2 = e2.typeCheck(typeEnv);
        if (!type1.equals(IntType.T))
            throw new TypeCheckError("The first operand is not an integer");
        if (!type2.equals(IntType.T))
            throw new TypeCheckError("The second operand is not an integer");
        return IntType.T;
    }

    @Override
    public String toString() {
        return "MUL(" + e1.toString() + ", " + e2.toString()+")";
    }

}
