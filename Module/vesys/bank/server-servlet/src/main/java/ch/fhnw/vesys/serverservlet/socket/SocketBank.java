package ch.fhnw.vesys.serverservlet.socket;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.OverdrawException;

import java.io.IOException;
import java.util.Set;

public class SocketBank implements Bank {

    @Override
    public String createAccount(String owner) throws IOException {
        Task task = Sender.send(new Task.CreateAccountTask(owner));
        task.throwPossibleIoException();
        return (String) task.getResult();
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        Task task = Sender.send(new Task.CloseAccountTask(number));
        task.throwPossibleIoException();
        return (boolean) task.getResult();
    }

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        Task task = Sender.send(new Task.GetAccountNumbersTask());
        task.throwPossibleIoException();
        return (Set<String>) task.getResult();
    }

    @Override
    public Account getAccount(String number) throws IOException {
        Task task = Sender.send(new Task.GetAccountTask(number));
        task.throwPossibleIoException();
        return Sender.fromLocalAccountToSocketAccount((Account) task.getResult());
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Task task = Sender.send(new Task.TransferTask(Sender.fromSocketAccountToLocalAccount(from), Sender.fromSocketAccountToLocalAccount(to), amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleInactiveEception();
        task.throwPossibleOverdrawException();
    }
}
