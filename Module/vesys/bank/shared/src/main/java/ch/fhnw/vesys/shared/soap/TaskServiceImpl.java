package ch.fhnw.vesys.shared.soap;

import ch.fhnw.vesys.shared.api.BankDriver;
import ch.fhnw.vesys.shared.core.Task;
import ch.fhnw.vesys.shared.local.LocalDriver;

import javax.jws.WebService;
import java.io.*;

@WebService(endpointInterface = "ch.fhnw.vesys.shared.soap.TaskService")
public class TaskServiceImpl implements TaskService {

    private final BankDriver bankdriver = new LocalDriver();

    @Override
    public byte[] handleTask(byte[] data) throws Exception {
        Task task = fromByteArrayToTask(data);
        task.executeHandledTask(bankdriver);
        return fromTaskToByteArray(task);
    }

    static byte[] fromTaskToByteArray(Task task) throws IOException {
        ByteArrayOutputStream byteoutputstream = new ByteArrayOutputStream();
        ObjectOutputStream objectoutputstream = new ObjectOutputStream(byteoutputstream);
        objectoutputstream.writeObject(task);
        byte[] data = byteoutputstream.toByteArray();
        objectoutputstream.close();
        byteoutputstream.close();
        return data;
    }

    static Task fromByteArrayToTask(byte[] data) throws Exception {
        ByteArrayInputStream byteinputstream = new ByteArrayInputStream(data);
        ObjectInputStream inputstream = new ObjectInputStream((byteinputstream));
        Task task = (Task) inputstream.readObject();
        inputstream.close();
        byteinputstream.close();
        return task;
    }
}
