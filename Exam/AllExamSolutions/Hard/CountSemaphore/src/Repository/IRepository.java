package Repository;

import Model.ProgramState;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IRepository {
    List<ProgramState> getProgramList();

    void setProgramStates(List<ProgramState> programStates);

    void logProgramStateExecution(ProgramState programState) throws IOException;

    void setPrgStatesList(List<ProgramState> programStates);

    void emptyLogFile() throws IOException;

    void addProgram(ProgramState program);
}
