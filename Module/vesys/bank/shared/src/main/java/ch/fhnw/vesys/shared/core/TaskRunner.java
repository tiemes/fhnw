package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.util.Set;

public class TaskRunner {

    static String launchCreateAccountTask(Sender sender, String owner) throws IOException {
        Task task = sender.sendTask(new Task.CreateAccountTask(owner));
        task.throwPossibleIoException();
        return (String) task.getResult();
    }

    static boolean launchCloseAccountTask(Sender sender, String number) throws IOException {
        Task task = sender.sendTask(new Task.CloseAccountTask(number));
        task.throwPossibleIoException();
        return (boolean) task.getResult();
    }

    static Set<String> launchGetAccountNumbers(Sender sender) throws IOException {
        Task task = sender.sendTask(new Task.GetAccountNumbersTask());
        task.throwPossibleIoException();
        return (Set<String>) task.getResult();
    }

    static Account launchGetAccountTask(Converter converter, Sender sender, String number) throws IOException {
        Task task = sender.sendTask(new Task.GetAccountTask(number));
        task.throwPossibleIoException();
        return converter.fromLocalToRemoteAccount(sender, (Account) task.getResult());
    }

    static void launchTransferTask(Converter converter, Sender sender, Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Task task = sender.sendTask(new Task.TransferTask(converter.fromRemoteToLocalAccount(from), converter.fromRemoteToLocalAccount(to), amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleInactiveEception();
        task.throwPossibleOverdrawException();
    }

    static boolean launchIsActiveTask(Sender sender, String number) throws IOException {
        Task task = sender.sendTask(new Task.IsActiveTask(number));
        task.throwPossibleIoException();
        return (boolean) task.getResult();
    }

    static void launchDepositTask(Sender sender, String number, double amount) throws IOException, IllegalArgumentException, InactiveException {
        Task task = sender.sendTask(new Task.DepositTask(number, amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleInactiveEception();
    }

    static void launchWithdrawTask(Sender sender, String number, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
        Task task = sender.sendTask(new Task.WithdrawTask(number, amount));
        task.throwPossibleIoException();
        task.throwPossibleIllegalArgumentException();
        task.throwPossibleOverdrawException();
        task.throwPossibleInactiveEception();
    }

    static double launchGetBalanceTask(Sender sender, String number) throws IOException {
        Task task = sender.sendTask(new Task.GetBalanceTask(number));
        task.throwPossibleIoException();
        return (double) task.getResult();
    }
}