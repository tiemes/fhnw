package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.*;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.IOException;
import java.io.Serializable;
import java.util.Set;

public abstract class Driver implements BankDriver {

    private final InternalBank bank;

    public Driver(Sender sender) {
        this.bank = new InternalBank(sender);
    }

    abstract public void connect(String[] args);

    abstract public void disconnect();

    @Override
    public Bank getBank() {
        return bank;
    }

    private static class InternalBank implements Bank {

        private final Converter converter;

        private final Sender sender;

        InternalBank(Sender sender) {
            this.converter = new InternalConverter();
            this.sender = sender;
        }

        @Override
        public String createAccount(String owner) throws IOException {
            return TaskRunner.launchCreateAccountTask(sender, owner);
        }

        @Override
        public boolean closeAccount(String number) throws IOException {
            return TaskRunner.launchCloseAccountTask(sender, number);
        }

        @Override
        public Set<String> getAccountNumbers() throws IOException {
            return TaskRunner.launchGetAccountNumbers(sender);
        }

        @Override
        public Account getAccount(String number) throws IOException {
            return TaskRunner.launchGetAccountTask(converter, sender, number);
        }

        @Override
        public void transfer(Account from, Account to, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            TaskRunner.launchTransferTask(converter, sender, from, to, amount);
        }
    }

    private static class InternalAccount implements Account, Serializable {

        private final Sender sender;

        private final String number;

        private final String owner;

        InternalAccount(Sender sender, String number, String owner) {
            this.sender = sender;
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
            return TaskRunner.launchIsActiveTask(sender, number);
        }

        @Override
        public void deposit(double amount) throws IOException, IllegalArgumentException, InactiveException {
            TaskRunner.launchDepositTask(sender, number, amount);
        }

        @Override
        public void withdraw(double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException {
            TaskRunner.launchWithdrawTask(sender, number, amount);
        }

        @Override
        public double getBalance() throws IOException {
            return TaskRunner.launchGetBalanceTask(sender, number);
        }
    }

    private static class InternalConverter implements Converter {

        @Override
        public Account fromLocalToRemoteAccount(Sender sender, Account localaccount) throws IOException {
            if (localaccount == null) {
                return null;
            }
            return new InternalAccount(sender, localaccount.getNumber(), localaccount.getOwner());
        }

        @Override
        public Account fromRemoteToLocalAccount(Account remoteaccount) throws IOException, InactiveException {
            if (remoteaccount == null) {
                return null;
            }

            LocalDriver.LocalAccount localaccount = new LocalDriver.LocalAccount(remoteaccount.getOwner(), remoteaccount.getNumber());

            localaccount.active = remoteaccount.isActive();

            if (localaccount.active) {
                localaccount.deposit(remoteaccount.getBalance());
            }

            return localaccount;
        }
    }
}
