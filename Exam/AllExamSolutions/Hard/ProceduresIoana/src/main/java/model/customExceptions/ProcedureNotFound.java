package model.customExceptions;

public class ProcedureNotFound extends RuntimeException {
    public ProcedureNotFound(String procName) {
        super(procName+ " is not defined!");
    }
}
