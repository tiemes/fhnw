package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.InactiveException;
import ch.fhnw.vesys.shared.api.OverdrawException;

import java.io.IOException;
import java.util.Set;

public interface AbstractSender {

    String createAccont(String owner) throws IOException;

    boolean closeAccount(String number) throws IOException;

    Set<String> getAccountNumbers() throws IOException;

    Account getAccount(String number) throws IOException;

    void transferMoney(String fromnumber, String tonumber, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException;

    boolean isAccountActive(String number) throws IOException;

    void depositMoney(String number, double amount) throws IOException, IllegalArgumentException, InactiveException;

    void withdrawMoney(String number, double amount) throws IOException, IllegalArgumentException, OverdrawException, InactiveException;

    double getBalance(String number) throws IOException;
}
