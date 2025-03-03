package com.ci453.ci453_project;

import java.util.Objects;

// Bank class - simple implementation of a bank, with a list of bank accounts,
// a current account that we are logged in to.
public class Bank {
    // Instance variables containing the bank information
    int maxAccounts = 10;       // maximum number of accounts the bank can hold
    int numAccounts = 0;        // the number of accounts currently in the bank
    BankAccount[] accounts = new BankAccount[maxAccounts];  // array to hold the bank accounts
    BankAccount account = null; // currently logged in account ('null' if no-one is logged in)

    // Constructor method - this provides a couple of example bank accounts to work with
    public Bank() {
        Debug.trace("Bank::<constructor>");

    }

    // a variant of addBankAccount which makes the account and adds it all in one go.
    // Using the same name for this method is called 'method overloading' - two methods
    // can have the same name if they take different argument combinations
    public boolean addBankAccount(int accNumber, String accPasswd, int balance, AccountType type) {
        BankAccount a = new BankAccount(accNumber, accPasswd, balance, type);
        if (numAccounts < maxAccounts) {
            accounts[numAccounts] = a;
            numAccounts++;
            Debug.trace("Bank::addBankAccount: added " +
                    a.accNumber+" "+a.accPasswd +" £"+a.balance);
            return true;
        } else {
            Debug.trace("Bank::addBankAccount: Can't add bank account - too many accounts");
            return false;
        }
    }

    public boolean login(int newAccNumber, String AccPasswd) {
        Debug.trace("Bank::login: accNumber = " + newAccNumber);
        logout();
        Debug.trace("1");
        for (int i = 0; i <= numAccounts - 1; i++) {
            if (accounts[i].accNumber == newAccNumber) {
                Debug.trace("2");
                if(accounts[i].accPasswd.equals(AccPasswd)){
                    Debug.trace("3");
                    account = accounts[i];
                    return true;
                }
            }
        }


        // not found - return false
        account = null;
        return false;
    }

    // Reset the bank to a 'logged out' state
    public void logout() {
        if (loggedIn()) {
            Debug.trace("Bank::logout: logging out, accNumber = " + account.accNumber);
            account = null;
        }
    }

    // test whether the bank is logged in to an account or not
    public boolean loggedIn() {
        if (account == null) {
            return false;
        } else {
            return true;
        }
    }

    // try to deposit money into the account (by calling the deposit method on the
    // BankAccount object)
    public boolean deposit(int amount) {
        if (loggedIn()) {
            return account.deposit(amount);
        } else {
            return false;
        }
    }

    // try to withdraw money into the account (by calling the withdraw method on the
    // BankAccount object)
    public boolean withdraw(int amount) {
        if (loggedIn()) {
            return account.withdraw(amount);
        } else {
            return false;
        }
    }
}
