package com.ci453.atmapp.atmapp_v07a;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

// Bank class - simple implementation of a bank, with a list of bank accounts,
// a current account that we are logged in to.
public class Bank {
    // Instance variables containing the bank information
    int nextAccountNumber = 118;
    int maxAccounts = 10;       // maximum number of accounts the bank can hold
    int numAccounts = 0;        // the number of accounts currently in the bank
    BankAccount[] accounts = new BankAccount[maxAccounts];  // array to hold the bank accounts
    BankAccount account = null; // currently logged in account ('null' if no-one is logged in)

    // Constructor method - this provides a couple of example bank accounts to work with
    public Bank() {
        Debug.trace("DEBUG | Bank::Bank(<constructor>)");

    }

    // a variant of addBankAccount which makes the account and adds it all in one go.
    // Using the same name for this method is called 'method overloading' - two methods
    // can have the same name if they take different argument combinations

    public boolean addBankAccount(String password)
    {
        if (addBankAccount(nextAccountNumber,password,0,AccountType.BASIC))
        {
            nextAccountNumber++;
            return true;
        }
        else{
            return false;
        }
    }

    public boolean addBankAccount(int accNumber, String accPasswd, int balance, AccountType type) {

        String hashedPasswd = Hashing.sha256()
                .hashString(accPasswd, StandardCharsets.UTF_8)
                .toString();

        BankAccount a = new BankAccount(accNumber, hashedPasswd, balance, type);
        if (numAccounts < maxAccounts) {
            accounts[numAccounts] = a;
            numAccounts++;
            Debug.trace("DEBUG | Bank::addBankAccount()" +
                    a.accNumber+" "+hashedPasswd +" £"+a.balance);
            return true;
        } else {
            Debug.trace("DEBUG | Bank::addBankAccount() Can't add bank account - too many accounts");
            return false;
        }
    }

    public boolean isFull()
    {
        for (BankAccount a : accounts) {
            if (a == null) {
                return false;
            }
        }
        return true;
    }
    public boolean login(int newAccNumber, String AccPasswd) {
        Debug.trace("DEBUG | Bank::login: accNumber = " + newAccNumber);
        logout();

        String hashedPasswd = Hashing.sha256()
                .hashString(AccPasswd, StandardCharsets.UTF_8)
                .toString();

        Debug.trace("DEBUG | hashedPasswd = "+hashedPasswd);

        for (int i = 0; i <= numAccounts - 1; i++) {
            if (accounts[i].accNumber == newAccNumber) {
                if(accounts[i].accPasswd.equals(hashedPasswd)){
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
            Debug.trace("DEBUG | Bank::logout: logging out, accNumber = " + account.accNumber);
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

    public boolean transferMoney(int ID,int amount) {
        for (BankAccount a : accounts) {
            if (a != null && a.accNumber == ID)
            {
                if (account.withdraw(amount) == 1)
                {
                    a.deposit(amount);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
