package ch.fhnw.vesys.shared.servlet;

import ch.fhnw.vesys.shared.core.Driver;
import ch.fhnw.vesys.shared.core.Sender;
import ch.fhnw.vesys.shared.core.Task;

public class ServletDriver extends Driver {

    private static final ServletSender sender = new ServletSender();

    public ServletDriver() {
        super(sender);
    }

    @Override
    public void connect(String[] args) {
        ServletSender.hostname = args[0];
        ServletSender.port = Integer.parseInt(args[1]);
        System.out.println("Connected to servlet driver.");

    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected from servlet driver");
    }

    private static class ServletSender implements Sender {

        static String hostname;

        static int port;

        @Override
        public Task sendTask(Task task) {
            return null;
        }
    }
}
