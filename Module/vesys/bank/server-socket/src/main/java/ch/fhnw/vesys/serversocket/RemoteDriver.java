package ch.fhnw.vesys.serversocket;

import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;

public class RemoteDriver implements BankDriver {

    private final LocalDriver localdriver = new LocalDriver();

    @Override
    public void connect(String[] args) {
        localdriver.connect(args);
    }

    @Override
    public void disconnect() {
        localdriver.disconnect();
    }

    @Override
    public Bank getBank() {
        return localdriver.getBank();
    }
}
