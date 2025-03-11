package com.ci453.atmapp.atmapp_v07a;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// The model represents all the actual content and functionality of the app.
public class Model {
    final String ACCOUNT_NO = "account_no";
    final String PASSWORD = "password";
    final String LOGGED_IN = "logged_in";

    final String SETTINGS = "settings";

    // variables representing the ATM model
    String state = ACCOUNT_NO;
    int number = 0;
    Bank bank = null;
    int accNumber = -1;
    String accPasswd = "";

    // These three are what are shown on the View display
    String title = "Bank ATM";
    String display1 = null;
    String display2 = null;

    // The other parts of the model-view-controller setup
    public View view;
    public Controller controller;

    // Model constructor - we pass it a Bank object representing the bank we want to talk to
    public Model(Bank b) {
        Debug.trace("Model::<constructor>");
        bank = b;
    }

    // Initialising the ATM (or resetting after an error or logout)
    public void initialise(String message) {
        setState(ACCOUNT_NO);
        number = 0;
        display1 = message;
        display2 = "Enter your account number\n" +
                "Followed by \"Ent\"";
    }

    // use this method to change state - mainly so we print a debugging message whenever the state changes
    public void setState(String newState) {
        if (!state.equals(newState)) {
            String oldState = state;
            state = newState;
            Debug.trace("Model::setState: changed state from " + oldState + " to " + newState);
        }
    }

    // process a number key (the key is specified by the label argument)
    public void processNumber(String label) {
        // a little magic to turn the first char of the label into an int and update number
        char c = label.charAt(0);
        number = number * 10 + c - '0';
        if (!state.equals("password")) {
            if (number > 999999999) {
                display2 = "you cannot enter a number this big";
                number = 0;
            }
            display1 = "" + number;
        } else {
            accPasswd = String.valueOf(number);
            display1 += "*";
        }
        view.update();  // update the GUI
    }

    // process the Clear button - reset the number (and number display string)
    public void processClear() {
        // clear the number stored in the model
        number = 0;
        display1 = "";
        view.update();  // update the GUI
    }

    public void processEnter() {
        // Enter was pressed - what we do depends on what state the ATM is already in
        switch (state) {
            case ACCOUNT_NO:
                accNumber = number;
                number = 0;
                setState(PASSWORD);
                display1 = "";
                display2 = "Now enter your password\n" +
                        "Followed by \"Ent\"";
                break;
            case PASSWORD:
                Debug.trace("DEBUG | Model::processEnter() case PASSWORD");
                number = 0;
                display1 = "";
                if (bank.login(accNumber, accPasswd)) {
                    view.changeScene("Default");
                    setState(LOGGED_IN);
                    display2 = "Accepted\n" +
                            "Now enter the transaction you require";
                } else {
                    initialise("Unknown account/password");
                }
                break;
            case LOGGED_IN:
                Debug.trace("DEBUG | Model::processEnter()::case LOGGED_IN");
                switch (state) {
                    case SETTINGS:
                        Debug.trace("DEBUG | Model::processEnter()::case SETTINGS");
                }
            default:
        }
        view.update();  // update the GUI
    }

    public void processWithdraw() {
        if (state.equals(LOGGED_IN)) {
            if (bank.account.withdraw(number) == 1) {
                display2 = "Withdrawn: " + number;
            } else if (bank.account.withdraw(number) == 2) {
                display2 = "You do not have sufficient funds";
            } else {
                display2 = "Withdraw amount exceeds maximum allowed.";
            }
            number = 0;
            display1 = "";
        } else {
            initialise("You are not logged in");
        }
        view.update();
    }

    // Deposit button - check we are logged in and if so try and deposit some money into
    // the bank (number is the amount showing in the interface display)
    public void processDeposit() {
        if (state.equals(LOGGED_IN)) {
            if (bank.deposit(number)) {
                display1 = "";
                display2 = "Deposited: " + number;
                number = 0;
            } else {
                display2 = "You have reached the maximum amount of money allowed to be held in the account." +
                        "\nDeposit rejected.";
            }


        } else {
            initialise("You are not logged in");
        }
        view.update();  // update the GUI
    }

    public void processBalance() {
        if (state.equals(LOGGED_IN)) {
            number = 0;
            display1 = "";
            display2 = "Your balance is: " + bank.account.getBalance() +
                    "\n Your overdraft is: " + bank.account.getOverdraft();
        } else {
            initialise("You are not logged in");
        }
        view.update();  // update the GUI
    }

    // Finish button - check we are logged in and if so log out
    public void processFinish() {
        Debug.trace("DEBUG | Model::processFinish()");
        initialise("You are not logged in");
        setState(ACCOUNT_NO);
        number = 0;
        display2 = "Welcome: Enter your account number";
        bank.logout();
        view.changeScene("Login");
        view.update(); // Update Gui

    }

    // Any other key results in an error message and a reset of the GUI
    public void processUnknownKey(String action) {
        // unknown button, or invalid for this state - reset everything
        Debug.trace("DEBUG | Model::processUnknownKey() | unknown button \"" + action + "\", re-initialising");
        // go back to initial state
        initialise("Invalid command");
        view.update();
    }

    public void processAccount() {
        Debug.trace("DEBUG | Model::processAccount()");
        if (state.equals(LOGGED_IN)) {
            setState(SETTINGS);
            Debug.trace("DEBUG | Model::processAccount() setState(SETTINGS)");
            display1 = "";
            display2 = "Account Number: " + accNumber + "\n" +
                    "Account Balance: " + bank.account.getBalance() + "\n" +
                    "Account Overdraft: " + bank.account.getOverdraft() + "\n";

        } else {
            initialise("You are not logged in");
        }
        view.update();
    }
}

