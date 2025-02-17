package com.atm;

// BankAccount class
// This class has instance variables for the account number, password and balance, and methods
// to withdraw, deposit, check balance etc.

// This class contains methods which you need to complete to make the basic ATM work.
// Tutors can help you get this part working in lab sessions.

// If you choose the ATM for your project, you should make other modifications to
// the system yourself, based on similar examples we will cover in lectures and labs.
public class BankAccount
{
    public int accNumber = 0;
    public int accPasswd = 0;
    public int balance = 0;
    public int overdraft = 0;

    public BankAccount()
    { // an empty constructor

    }

    public BankAccount(int n, int p, int b, int o)
    {
        accNumber = n;
        accPasswd = p;
        balance = b;
        overdraft = o;
    }

    // withdraw money from the account. Return true if successful, or
    // false if the amount is negative, or less than the amount in the account
    public boolean withdraw( int amount )
    {
        Debug.trace( "BankAccount::withdraw: amount =" + amount );
        int newAmount = balance - amount;
        if (newAmount < 0) {
            if (overdraft + newAmount < -1000) {
                Debug.trace("Overdraft too low to withdraw");
                return false;
            }
            else if (balance >= 0) {
                overdraft += newAmount;
                balance -= amount;
                return true;
            } else {
                overdraft -= amount;
                balance -= amount;
                return true;
            }
        }
        else {
            balance = newAmount;
            return true;
        }
    }

    // deposit the amount of money into the account. Return true if successful,
    // or false if the amount is negative
    public boolean deposit( int amount )
    {
        Debug.trace( "LocalBank::deposit: amount = " + amount );
        int newAmount = overdraft + amount;
        if (newAmount >= 1000) {
            overdraft = 1000;
        } else {
            overdraft = overdraft + amount;
        }
        balance = balance + amount;


        return false;
    }

    // Return the current balance in the account
    public int getBalance()
    {
        Debug.trace( "LocalBank::getBalance" );
        return balance;
    }

    public int getOverdraft()
    {
        Debug.trace( "LocalBank::getOverdraft" );
        return overdraft;
    }
}

