package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.OverdrawException;

import java.io.IOException;
import java.io.Serializable;

public class SocketAccount implements Account, Serializable {

    private final String number;

    private final String owner;

    public SocketAccount(String number, String owner) {
        this.number = number;
        this.owner = owner;
    }

    @Override
    public String getNumber() throws IOException {
        return number;
    }

    @Override
    public String getOwner() throws IOException {
        return owner;
    }

    @Override
    public boolean isActive() throws IOException {
        Task task = Sender.send(new Task.IsActiveTask(number));
        task.throwPossibleIoException();
        return (boolean) task.getResult();
    }

    @Override
    public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
        Task task = Sender.send(new Task.DepositTask(number, amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleInactiveEception();
    }

    @Override
    public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Task task = Sender.send(new Task.WithdrawTask(number, amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleOverdrawException();
        task.throwPossibleInactiveEception();
    }

    @Override
    public double getBalance() throws IOException {
        Task task = Sender.send(new Task.GetBalanceTask(number));
        task.throwPossibleIoException();
        return (double) task.getResult();
    }

    @Override
    public void close() throws IOException {
        // Do nothing, because the method is never called and the functionality is already defined in BankSocket.
    }
}
