package ch.fhnw.vesys.serverservlet;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.local.LocalDriver;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ServletServer {

    private final BankDriver bankdriver;

    private ServletServer(int port) throws IOException {
        bankdriver = new LocalDriver();
    }

    private void startServer() throws IOException {
        ExecutorService service = Executors.newCachedThreadPool();
        while (true) {
            /*Socket socket = serversocket.accept();
            service.execute(new RequestHandler(bankdriver, socket));*/
        }
    }

    public static void main(String[] args) throws IOException {
        new ServletServer(1234).startServer();
    }
}