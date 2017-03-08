package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.io.Serializable;
import java.util.function.BiFunction;

public interface TaskBiFunction extends BiFunction<BankDriver, Object[], Object>, Serializable {

    @Override
    Object apply(BankDriver bankdriver, Object[] parameters);

    static TaskBiFunction invalidTask() {
        return (bankdriver, parameters) -> null;
    }

    static TaskBiFunction createAccount() throws IOException {
        return (bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().createAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        };
    }

    static TaskBiFunction closeAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().closeAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction getAccountNumbers() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccountNumbers();
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction getAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]);
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction transferAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().transfer((Account) parameters[0], (Account) parameters[1], (double) parameters[2]);
                return null;
            } catch (IOException | OverdrawException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction isActiveAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]).isActive();
            } catch (IOException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction depositAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().getAccount((String) parameters[0]).deposit((double) parameters[1]);
                return null;
            } catch (IOException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction withdrawAccount() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                bankdriver.getBank().getAccount((String) parameters[0]).withdraw((double) parameters[1]);
                return null;
            } catch (IOException | OverdrawException | InactiveException exception) {
                return exception;
            }
        });
    }

    static TaskBiFunction getBalance() throws IOException {
        return ((bankdriver, parameters) -> {
            try {
                return bankdriver.getBank().getAccount((String) parameters[0]).getBalance();
            } catch (IOException exception) {
                return exception;
            }
        });
    }
}
