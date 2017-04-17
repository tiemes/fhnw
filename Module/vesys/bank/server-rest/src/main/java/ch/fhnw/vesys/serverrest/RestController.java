package ch.fhnw.vesys.serverrest;

import ch.fhnw.vesys.shared.api.BankDriver;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Singleton
@Path("/accounts")
public class RestController {

    private final BankDriver bankdriver;

    RestController(BankDriver bankdriver) {
        this.bankdriver = bankdriver;
    }

    @GET
    @Path("/hello")
    public String sayHello() {
        System.out.println("sayHello()");
        return "Hello from the REST API!";
    }
}
