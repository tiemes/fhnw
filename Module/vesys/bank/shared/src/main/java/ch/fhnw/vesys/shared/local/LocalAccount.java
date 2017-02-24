package ch.fhnw.vesys.shared.local;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.OverdrawException;

import java.io.IOException;

class LocalAccount implements Account {

    private String number;

    private String owner;

    private double balance;

    public boolean active = true;

    LocalAccount(String owner, String number) {
        this.owner = owner;
        this.number = number;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void deposit(double amount) throws InactiveException {
        if (!active) {
            throw new InactiveException("The current account is not active");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("The deposit amount has to be greater than 0");
        }

        balance += amount;
    }

    @Override
    public void withdraw(double amount) throws InactiveException, OverdrawException {
        if (!active) {
            throw new InactiveException("The current account is not active");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("The withdraw amount has to be greater than 0");
        }

        if (balance - amount < 0) {
            throw new OverdrawException("The current amount would overdraw the account");
        }

        balance -= amount;
    }

    @Override
    public void close() throws IOException {
        active = false;
    }
}
