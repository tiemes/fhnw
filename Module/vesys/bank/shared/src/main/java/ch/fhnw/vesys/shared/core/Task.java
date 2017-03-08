package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.io.Serializable;

public class Task implements Serializable {

    private TaskBiFunction taskbifunction;

    private Object[] parameters;

    private Object result;

    public Task(TaskBiFunction taskbifunction, Object... parameters) {
        this.taskbifunction = taskbifunction;
        this.parameters = parameters;
    }

    public void execute(BankDriver bankdriver) {
        try {
            result = taskbifunction.apply(bankdriver, parameters);
        } catch (Exception exception) {
            result = exception;
        }
    }

    public Object getResult() {
        return result;
    }

    public void throwPossibleIoException() throws IOException {
        if (result != null && result instanceof IOException) {
            throw (IOException) result;
        }
    }

    public void throwPossibleIllegalArgumentException() throws IllegalArgumentException {
        if (result instanceof IllegalArgumentException) {
            throw (IllegalArgumentException) result;
        }
    }

    public void throwPossibleInactiveEception() throws InactiveException {
        if (result instanceof InactiveException) {
            throw (InactiveException) result;
        }
    }

    public void throwPossibleOverdrawException() throws OverdrawException {
        if (result instanceof OverdrawException) {
            throw (OverdrawException) result;
        }
    }
}
