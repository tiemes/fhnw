package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.local.LocalAccount;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Sender {

    public static String hostname = "127.0.0.1";

    public static int port = 8080;

    public static Task send(Task task) {
        try {
            Socket socket = new Socket(hostname, port);
            ObjectOutput outputstream = new ObjectOutputStream(socket.getOutputStream());
            outputstream.writeObject(task);
            ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());
            task = (Task) inputstream.readObject();
            inputstream.close();
            outputstream.close();
            socket.close();
            return task;
        } catch (Exception exception) {
            return new Task.InvalidTask();
        }
    }

    public static Account fromLocalAccountToSocketAccount(Account localaccount) throws IOException {
        if (localaccount == null) {
            return null;
        }
        return new SocketAccount(localaccount.getNumber(), localaccount.getOwner());
    }

    public static Account fromSocketAccountToLocalAccount(Account socketaccount) throws IOException, InactiveException {
        if (socketaccount == null) {
            return null;
        }

        LocalAccount localaccount = new LocalAccount(socketaccount.getOwner(), socketaccount.getNumber());

        if (socketaccount.isActive()) {
            localaccount.deposit(socketaccount.getBalance());
        } else {
            localaccount.close();
        }

        return localaccount;
    }
}
