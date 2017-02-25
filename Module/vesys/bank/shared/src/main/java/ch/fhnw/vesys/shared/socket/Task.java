package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.BankDriver;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.OverdrawException;

import java.io.IOException;
import java.io.Serializable;

public abstract class Task implements Serializable {

    protected Object result;

    protected Exception exception;

    public abstract void executeTask(BankDriver driver) throws Exception;

    public void executeHandledTask(BankDriver driver) {
        try {
            executeTask(driver);
        } catch (Exception exception) {
            this.exception = exception;
        }
    }

    public Object getResult() {
        return result;
    }

    public void throwPossibleIoException() throws IOException {
        if (exception != null && exception instanceof IOException) {
            throw new IOException(exception.getMessage(), exception);
        }
    }

    public void throwPossibleIllegalArgumentException() throws IllegalArgumentException {
        if (exception != null && exception instanceof IllegalArgumentException) {
            throw new IllegalArgumentException(exception.getMessage(), exception);
        }
    }

    public void throwPossibleInactiveEception() throws InactiveException {
        if (exception != null && exception instanceof InactiveException) {
            throw new InactiveException(exception.getMessage(), exception);
        }
    }

    public void throwPossibleOverdrawException() throws OverdrawException {
        if (exception != null && exception instanceof OverdrawException) {
            throw new OverdrawException(exception.getMessage(), exception);
        }
    }

    public static class InvalidTask extends Task {

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            // Do nothing and return a null pointer in the result cast. This will trigger a null pointer exception.
        }
    }

    public static class CreateAccountTask extends Task {

        private final String owner;

        public CreateAccountTask(String owner) {
            this.owner = owner;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().createAccount(owner);
        }
    }

    public static class CloseAccountTask extends Task {

        private final String number;

        public CloseAccountTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().closeAccount(number);
        }
    }

    public static class GetAccountNumbersTask extends Task {

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().getAccountNumbers();
        }
    }

    public static class GetAccountTask extends Task {

        private final String number;

        public GetAccountTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().getAccount(number);
        }
    }

    public static class TransferTask extends Task {

        private final Account from;

        private final Account to;

        private final double amount;

        public TransferTask(Account from, Account to, double amount) {
            this.from = from;
            this.to = to;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().transfer(from, to, amount);
        }
    }

    public static class IsActiveTask extends Task {

        private final String number;

        public IsActiveTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().getAccount(number).isActive();
        }
    }

    public static class DepositTask extends Task {

        private final String number;

        private final double amount;

        public DepositTask(String number, double amount) {
            this.number = number;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().getAccount(number).deposit(amount);
        }
    }

    public static class WithdrawTask extends Task {

        private final String number;

        private final double amount;

        public WithdrawTask(String number, double amount) {
            this.number = number;
            this.amount = amount;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            driver.getBank().getAccount(number).withdraw(amount);
        }
    }

    public static class GetBalanceTask extends Task {

        private final String number;

        public GetBalanceTask(String number) {
            this.number = number;
        }

        @Override
        public void executeTask(BankDriver driver) throws Exception {
            this.result = driver.getBank().getAccount(number).getBalance();
        }
    }
}
