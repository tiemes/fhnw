package ch.fhnw.vesys.serverservlet.socket;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.local.LocalDriver;

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

        Account localaccount = new LocalDriver.LocalAccount(socketaccount.getOwner(), socketaccount.getNumber());

        if (socketaccount.isActive()) {
            localaccount.deposit(socketaccount.getBalance());
        } else {
            // TODO: Fix method call
            // localaccount.close();
        }

        return localaccount;
    }
}