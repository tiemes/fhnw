package ch.fhnw.vesys.shared.core;

import ch.fhnw.vesys.shared.api.Account;
import ch.fhnw.vesys.shared.api.InactiveException;

import java.io.IOException;

interface Converter {

    Account fromLocalToRemoteAccount(Sender sender, Account localaccount) throws IOException;

    Account fromRemoteToLocalAccount(Account remoteaccount) throws IOException, InactiveException;
}
