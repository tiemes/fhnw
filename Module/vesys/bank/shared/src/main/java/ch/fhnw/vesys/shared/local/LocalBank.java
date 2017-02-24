package ch.fhnw.vesys.shared.local;

import ch.fhnw.vesys.shared.Account;
import ch.fhnw.vesys.shared.Bank;
import ch.fhnw.vesys.shared.InactiveException;
import ch.fhnw.vesys.shared.OverdrawException;

import java.io.IOException;
import java.util.*;

class LocalBank implements Bank {

    private final Map<String, Account> accounts = new HashMap<>();

    @Override
    public Set<String> getAccountNumbers() throws IOException {
        Set<String> numbers = new HashSet<>();
        Iterator iterator = accounts.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            String number = (String) pair.getKey();
            Account account = (Account) pair.getValue();
            if (account.isActive()) {
                numbers.add(number);
            }
        }
        return numbers;
    }

    @Override
    public String createAccount(String owner) {
        String uuid = UUID.randomUUID().toString();
        Account account = new LocalAccount(owner, uuid);
        accounts.put(uuid, account);
        return uuid;
    }

    @Override
    public boolean closeAccount(String number) throws IOException {
        Account account = getAccount(number);

        if (account == null) {
            return false;
        }

        if (!account.isActive()) {
            return false;
        }

        if (account.getBalance() != 0) {
            return false;
        }

        account.close();
        return true;
    }

    @Override
    public Account getAccount(String number) {
        return accounts.get(number);
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws IOException, InactiveException, OverdrawException {

        if (!to.isActive()) {
            throw new InactiveException("The account of the receiver is inactive");
        }

        from.withdraw(amount);
        to.deposit(amount);
    }
}
