package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.BankDriver;

public class SocketDriver implements BankDriver {

    private final SocketBank socketbank = new SocketBank();

    @Override
    public void connect(String[] args) {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public Bank getBank() {
        return socketbank;
    }
}
