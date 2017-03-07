package ch.fhnw.vesys.serversocket;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.socket.Task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class RequestHandler implements Runnable {

    private final BankDriver bankdriver;

    private final Socket socket;

    RequestHandler(BankDriver bankdriver, Socket socket) {
        this.bankdriver = bankdriver;
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream()); ObjectOutputStream outputstream = new ObjectOutputStream(socket.getOutputStream())) {
            Task task = (Task) inputstream.readObject();
            task.execute(bankdriver);
            outputstream.writeObject(task);
            socket.close();
        } catch (Exception exception) {
            try {
                socket.close();
            } catch (IOException realexception) {
                realexception.printStackTrace();
            }
        }
    }
}
