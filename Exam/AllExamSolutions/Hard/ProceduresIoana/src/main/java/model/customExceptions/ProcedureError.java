package model.customExceptions;

public class ProcedureError extends RuntimeException {
    public ProcedureError(String s) {
        super(s);
    }
}
