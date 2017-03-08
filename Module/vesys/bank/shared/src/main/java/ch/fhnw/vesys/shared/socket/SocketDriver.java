package ch.fhnw.vesys.shared.socket;

import ch.fhnw.vesys.shared.core.Driver;
import ch.fhnw.vesys.shared.core.Sender;
import ch.fhnw.vesys.shared.core.Task;
import ch.fhnw.vesys.shared.core.TaskBiFunction;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketDriver extends Driver {

    private static final SocketSender sender = new SocketSender();

    public SocketDriver() {
        super(sender);
    }

    @Override
    public void connect(String[] args) {
        SocketSender.hostname = args[0];
        SocketSender.port = Integer.parseInt(args[1]);
        System.out.println("Connected to socket driver.");
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected from socket driver");
    }

    private static class SocketSender implements Sender {

        static String hostname;

        static int port;

        @Override
        public Task sendTask(TaskBiFunction taskbifunction, Object... parameters) {
            try (Socket socket = new Socket(hostname, port); ObjectOutput outputstream = new ObjectOutputStream(socket.getOutputStream()); ObjectInputStream inputstream = new ObjectInputStream(socket.getInputStream());) {
                Task task = new Task(taskbifunction, parameters);
                outputstream.writeObject(task);
                task = (Task) inputstream.readObject();
                return task;
            } catch (Exception exception) {
                return new Task(TaskBiFunction.invalidTask());
            }
        }
    }
}
