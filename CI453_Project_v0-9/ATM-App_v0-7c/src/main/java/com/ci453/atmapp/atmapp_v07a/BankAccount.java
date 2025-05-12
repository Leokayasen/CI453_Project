package com.ci453.atmapp.atmapp_v07a;

// BankAccount class
// This class has instance variables for the account number, password and balance, and methods to withdraw, deposit, check balance etc.

public class BankAccount {
    public int accNumber = 0;
    public String accPasswd = "0";
    public int balance = 0;
    public int overdraft = 0;
    public int defaultOverdraft = 0;
    public int withdrawLimit = 0;

    public BankAccount(int num, String pass, int bal, AccountType type) {
        accNumber = num;
        accPasswd = pass;
        balance = bal;
        switch(type){
            case BASIC:
                withdrawLimit = 500;
                overdraft = 0;
                defaultOverdraft = 0;
                break;
            case PREMIUM:
                withdrawLimit = 1000000;
                overdraft = 2000;
                defaultOverdraft = 2000;
                break;
            case SAVINGS:
                withdrawLimit = 1000000000;
                overdraft = 0;
                defaultOverdraft = 0;
                break;
            case STUDENT:
                withdrawLimit = 1000000000;
                overdraft = 1000;
                defaultOverdraft = 1000;
                break;
            case null, default:
                withdrawLimit = 1000;
                overdraft = 500;
                defaultOverdraft = 500;
                break;
        }
    }

    // withdraw money from the account. Return true if successful, or
    // false if the amount is negative, or less than the amount in the account
    public byte withdraw(int amount) {
        Debug.trace("BankAccount::withdraw: amount =" + amount);
        if (amount>withdrawLimit) {
            return 3;
        }
        int newAmount = balance - amount;
        if (newAmount < 0) {
            if (overdraft + newAmount < -defaultOverdraft) {
                Debug.trace("Overdraft too low to withdraw");
                return 2;
            } else if (balance >= 0) {
                overdraft += newAmount;
                balance -= amount;
                return 1;
            } else {
                overdraft -= amount;
                balance -= amount;
                return 1;
            }
        } else {
            balance = newAmount;
            return 1;
        }
    }
    public boolean isLow()
    {
        if (balance < 100)
        {
            return true;
        }
        return false;
    }

    // deposit the amount of money into the account. Return true if successful,
    // or false if the amount is negative
    public boolean deposit(int amount) {
        Debug.trace("LocalBank::deposit: amount = " + amount);
        int newAmount = overdraft + amount;
        int newBalance = balance + amount;
        if (newBalance > 1000000000){
            Debug.trace("account limit exceeded. Deposit rejected.");
            return false;
        } else

        if (newAmount >= defaultOverdraft) {
            overdraft = defaultOverdraft;
        } else {
            overdraft = overdraft + amount;
        }
        balance = balance + amount;
        return true;
    }

    // Return the current balance in the account
    public int getBalance() {
        Debug.trace("LocalBank::getBalance");
        return balance;
    }

    public int getOverdraft() {
        Debug.trace("LocalBank::getOverdraft");
        return overdraft;
    }
}

