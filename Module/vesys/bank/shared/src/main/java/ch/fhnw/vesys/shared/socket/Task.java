package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.BiFunction;

public class Task implements Serializable {

    private TaskFunction taskfunction;

    private Object[] parameters;

    private Object result;

    Task(TaskFunction taskfunction, Object... parameters) {
        this.taskfunction = taskfunction;
        this.parameters = parameters;
    }

    public void execute(BankDriver bankdriver) {
        try {
            result = taskfunction.apply(bankdriver, parameters);
        } catch (Exception exception) {
            result = exception;
        }
    }

    Object getResult() {
        return result;
    }

    void throwPossibleIoException() throws IOException {
        if (result != null && result instanceof IOException) {
            throw (IOException) result;
        }
    }

    void throwPossibleIllegalArgumentException() throws IllegalArgumentException {
        if (result instanceof IllegalArgumentException) {
            throw (IllegalArgumentException) result;
        }
    }

    void throwPossibleInactiveEception() throws InactiveException {
        if (result instanceof InactiveException) {
            throw (InactiveException) result;
        }
    }

    void throwPossibleOverdrawException() throws OverdrawException {
        if (result instanceof OverdrawException) {
            throw (OverdrawException) result;
        }
    }

    interface TaskFunction extends BiFunction<BankDriver, Object[], Object>, Serializable {

        @Override
        Object apply(BankDriver bankdriver, Object[] parameters);
    }

    static TaskFunction invalidTask() {
        return (bankdriver, parameters) -> null;
    }

    static TaskFunction createAccount() throws IOException {
        return (bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().createAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        };
    }

    static TaskFunction closeAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().closeAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskFunction getAccountNumbers() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccountNumbers();
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskFunction getAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskFunction transferAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().transfer((Account) parameters[0], (Account) parameters[1], (double) parameters[2]);
                return null;
            } catch (IOException | OverdrawException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskFunction isActiveAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]).isActive();
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskFunction depositAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().getAccount((String) parameters[0]).deposit((double) parameters[1]);
                return null;
            } catch (IOException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskFunction withdrawAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().getAccount((String) parameters[0]).withdraw((double) parameters[1]);
                return null;
            } catch (IOException | OverdrawException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskFunction getBalance() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]).getBalance();
            } catch (IOException exception) {
                return exception;
            }
        });
    }
}
