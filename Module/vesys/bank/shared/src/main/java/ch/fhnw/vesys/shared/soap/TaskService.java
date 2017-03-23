package ch.fhnw.vesys.shared.soap;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TaskService {

    @WebMethod
    public byte[] handleTask(byte[] data) throws Exception;
}