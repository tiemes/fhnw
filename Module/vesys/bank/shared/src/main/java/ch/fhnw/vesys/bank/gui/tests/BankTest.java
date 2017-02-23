/*
 * Copyright (c) 2000-2016 Fachhochschule Nordwestschweiz (FHNW)
 * All Rights Reserved.
 */

package ch.fhnw.vesys.bank.gui.tests;

import ch.fhnw.vesys.bank.Bank;

import javax.swing.*;

public interface BankTest {

    String getName();

    boolean isEnabled(int size);

    void runTests(JFrame context, Bank bank, String currentAccountNumber) throws Exception;
}
