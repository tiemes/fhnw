package ch.fhnw.vesys.shared.local;

import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.BankDriver;

public class LocalDriver implements BankDriver {

    private final Bank bank = new LocalBank();

    @Override
    public void connect(String[] args) {
        System.out.println("Bank connected...");
    }

    @Override
    public void disconnect() {
        System.out.println("Bank disconnected...");
    }

    @Override
    public Bank getBank() {
        return bank;
    }
}
