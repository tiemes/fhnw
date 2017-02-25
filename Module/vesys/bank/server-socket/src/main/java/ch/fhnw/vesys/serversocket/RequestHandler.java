package ch.fhnw.vesys.serversocket;

import ch.fhnw.vesys.shared.BankDriver;
import ch.fhnw.vesys.shared.socket.Task;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

    private final BankDriver bankdriver;

    private final Socket socket;

    public RequestHandler(BankDriver bankdriver, Socket socket) {
        this.bankdriver = bankdriver;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());
            Task task = (Task) inputstream.readObject();
            task.executeHandledTask(bankdriver);
            ObjectOutputStream outputstream = new ObjectOutputStream(socket.getOutputStream());
            outputstream.writeObject(task);
            inputstream.close();
            outputstream.close();
            socket.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
