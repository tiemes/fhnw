package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.api.*;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.*;
import java.net.Socket;
import java.util.Set;

public class SocketDriver implements BankDriver {

    private final SocketBank socketbank = new SocketBank();

    private static String hostname = "127.0.0.1";

    private static int port = 8080;

    @Override
    public void connect(String[] args) {
        hostname = args[0];
        port = Integer.parseInt(args[1]);
    }

    @Override
    public void disconnect() {
        // Do nothing
    }

    @Override
    public Bank getBank() {
        return socketbank;
    }

    private static class SocketBank implements Bank {

        @Override
        public String createAccount(String owner) throws IOException {
            Task task = Sender.send(Task.createAccount(), owner);
            task.throwPossibleIoException();
            return (String) task.getResult();
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            Task task = Sender.send(Task.closeAccount(), number);
            task.throwPossibleIoException();
            return (boolean) task.getResult();
        }

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            Task task = Sender.send(Task.getAccountNumbers());
            task.throwPossibleIoException();
            return (Set<String>) task.getResult();
        }

        @Override
        public Account getAccount(String number) throws IOException {
            Task task = Sender.send(Task.getAccount(), number);
            task.throwPossibleIoException();
            return Sender.fromLocalAccountToSocketAccount((Account) task.getResult());
        }

        @Override
        public void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            Task task = Sender.send(Task.transferAccount(), Sender.fromSocketAccountToLocalAccount(from), Sender.fromSocketAccountToLocalAccount(to), amount);
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleInactiveEception();
            task.throwPossibleOverdrawException();
        }
    }

    private static class SocketAccount implements Account, Serializable {

        private final String number;

        private final String owner;

        SocketAccount(String number, String owner) {
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
            Task task = Sender.send(Task.isActiveAccount(), number);
            task.throwPossibleIoException();
            return (boolean) task.getResult();
        }

        @Override
        public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            Task task = Sender.send(Task.depositAccount(), number, amount);
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleInactiveEception();
        }

        @Override
        public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            Task task = Sender.send(Task.withdrawAccount(), number, amount);
            task.throwPossibleIoException();
            task.throwPossibleIllegalArgumentException();
            task.throwPossibleOverdrawException();
            task.throwPossibleInactiveEception();
        }

        @Override
        public double getBalance() throws IOException {
            Task task = Sender.send(Task.getBalance(), number);
            task.throwPossibleIoException();
            return (double) task.getResult();
        }
    }

    private static class Sender {

        private static Task send(Task.TaskFunction taskfunction, Object... parameters) {
            try (Socket socket = new Socket(hostname, port); ObjectOutput outputstream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());) {
                Task task = new Task(taskfunction, parameters);
                outputstream.writeObject(task);
                task = (Task) inputstream.readObject();
                return task;
            } catch (Exception exception) {
                return new Task(Task.invalidTask());
            }
        }

        private static Account fromLocalAccountToSocketAccount(Account localaccount) throws IOException {
            if (localaccount == null) {
                return null;
            }
            return new SocketDriver.SocketAccount(localaccount.getNumber(), localaccount.getOwner());
        }

        private static Account fromSocketAccountToLocalAccount(Account socketaccount) throws IOException, InactiveException {
            if (socketaccount == null) {
                return null;
            }

            LocalDriver.LocalAccount localaccount = new LocalDriver.LocalAccount(socketaccount.getOwner(), socketaccount.getNumber());

            localaccount.active = socketaccount.isActive();

            if (localaccount.active) {
                localaccount.deposit(socketaccount.getBalance());
            }

            return localaccount;
        }
    }
}
