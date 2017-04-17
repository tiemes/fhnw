package ch.fhnw.vesys.clientsoap;

import ch.fhnw.vesys.shared.core.Sender;
import ch.fhnw.vesys.shared.core.SerializationDriver;
import ch.fhnw.vesys.shared.core.Task;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

public class SoapDriver extends SerializationDriver {

    private static final SoapSender sender = new SoapSender();

    public SoapDriver() {
        super(sender);
    }

    @Override
    public void connect(String[] args) {
        SoapSender.hostname = args[0];
        SoapSender.port = Integer.parseInt(args[1]);
        System.out.println("Connected to soap driver.");
    }

    @Override
    public void disconnect() {
        System.out.println("Disconnected from soap driver");
    }

    private static class SoapSender implements Sender {

        static String hostname;

        static int port;

        @Override
        public Task sendTask(Task task) {
            try {
                URL wsdlurl = new URL("http://" + hostname + ":" + port + "/task?wsdl");
                QName qname = new QName("http://clientsoap.vesys.fhnw.ch/", "TaskServiceImplService");
                Service service = Service.create(wsdlurl, qname);
                TaskService taskservice = service.getPort(TaskService.class);
                return TaskServiceImpl.fromByteArrayToTask(taskservice.handleTask(TaskServiceImpl.fromTaskToByteArray(task)));
            } catch (Exception exception) {
                exception.printStackTrace();
                return new Task.InvalidTask();
            }
        }
    }
}
