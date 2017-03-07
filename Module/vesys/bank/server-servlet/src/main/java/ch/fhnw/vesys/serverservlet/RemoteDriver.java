package ch.fhnw.vesys.serverservlet;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RemoteDriver {

    private final ServerSocket serversocket;

    private final BankDriver bankdriver;

    public RemoteDriver(int port) throws IOException {
        serversocket = new ServerSocket(port);
        bankdriver = new LocalDriver();
    }

    public void startServer() throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            Socket socket = serversocket.accept();
            service.execute(new RequestHandler(bankdriver, socket));
        }
    }

    public static void main(String[] args) throws IOException {
        new RemoteDriver(1234).startServer();
    }
}
