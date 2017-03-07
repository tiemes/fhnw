package ch.fhnw.vesys.serverservlet.socket;

import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.BankDriver;

public class SocketDriver implements BankDriver {

    private final SocketBank socketbank = new SocketBank();

    @Override
    public void connect(String[] args) {
        Sender.hostname = args[0];
        Sender.port = Integer.parseInt(args[1]);
    }

    @Override
    public void disconnect() {
    }

    @Override
    public Bank getBank() {
        return socketbank;
    }
}
