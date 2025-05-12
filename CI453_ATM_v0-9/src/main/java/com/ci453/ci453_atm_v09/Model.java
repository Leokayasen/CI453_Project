package com.ci453.ci453_atm_v09;

import java.util.Objects;

// The model represents all the actual content and functionality of the app.
public class Model {
    final String ACCOUNT_NO = "account_no";
    final String PASSWORD = "password";

    final String LOGGED_IN = "logged_in";
    final String SETTINGS = "settings";

    final String MANAGMENT = "manage";
    final String WITHDRAW = "withdraw";
    final String DEPOSIT = "deposit";
    final String ACCOUNTCREATION = "accCreate";
    final String MONEYTRANSFER = "monTrans";

    // variables representing the ATM model
    String state = ACCOUNT_NO;
    int number = 0;
    Bank bank = null;
    int accNumber = -1;
    String accPasswd = "";
    String accPasswordStore = "";

    // These three are what are shown on the View display
    String title = "Bank ATM";
    String titl2 = "";
    String display1 = "";
    String display2 = "";
    String display3 = "";

    // The other parts of the model-view-controller setup
    public View view;
    public Controller controller;
    String receipt = "";

    // Model constructor - we pass it a Bank object representing the bank we want to talk to
    public Model(Bank b) {
        Debug.trace("DEBUG | Model::Model()");
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
            Debug.trace("DEBUG | Model::setState(): changed state from " + oldState + " to " + newState);
        }
    }

    // process a number key (the key is specified by the label argument)
    public void processNumber(String label) {
        // a little magic to turn the first char of the label into an int and update number
        char c = label.charAt(0);
        if (state.equals("password"))
        {
            number = number * 10 + c - '0';
            accPasswd = String.valueOf(number);
            display1 += "*";
        }
        else if (state.equals(ACCOUNTCREATION))
        {
            number = number * 10 + c - '0';
            if (accNumber == -1)
            {
                display1 += "*";
                accPasswd = String.valueOf(number);
            }
            else{
                display3 += "*";
                accPasswordStore = String.valueOf(number);
            }
        }
        else if (state.equals(MONEYTRANSFER))
        {
            if (Objects.equals(accPasswd, ""))
            {
                if (number <= 99999999) {
                    number = number * 10 + c - '0';
                    display1 = "" + number;
                }
            }
            else{
                if (number <= 99999999) {
                    number = number * 10 + c - '0';
                    display3 = "" + number;
                }
            }
        }
        else{
            number = number * 10 + c - '0';
            if (number > 99999999) {
                display2 = "ERROR: You cannot enter a larger value than 99999999.";
                number = 0;
            }
            display1 = "" + number;
        }
        System.out.println("The state is : " + state);
        view.update();  // update the GUI
    }

    // process the Clear button - reset the number (and number display string)
    public void processClear() {
        // clear the number stored in the model
        number = 0;
        display1 = "";
        view.update();  // update the GUI
    }
    public void toTransition()
    {
        setState(MANAGMENT);
        view.changeScene("Transition");
        display1 = "";
        display2 = "Account balance: " + bank.account.getBalance() +
                "\nAccount overdraft: " + bank.account.getOverdraft();
        title = "Select Account Option";
        number = 0;
        view.update();
    }
    public boolean processEnter() {
        boolean isClosing = false;
        // Enter was pressed - what we do depends on what state the ATM is already in
        switch (state) {
            case ACCOUNT_NO:
                accNumber = number;
                number = 0;
                setState(PASSWORD);
                title = "Input account Password";
                display1 = "";
                break;
            case PASSWORD:
                Debug.trace("DEBUG | Model::processEnter() case PASSWORD");
                number = 0;
                display1 = "";
                if (bank.login(accNumber, accPasswd)) {
                    receipt = "Account number - " + accNumber;
                    toTransition();
                } else {
                    processFinish(false);
                    initialise("Unknown account/password");
                }
                accPasswd = "";
                accNumber = 0;
                break;
            case WITHDRAW:
                processWithdraw();
                break;
            case ACCOUNTCREATION:
                if (accNumber == -1)
                {
                    accNumber = 0;
                    number = 0;
                    //then inputing first password
                }
                else{
                    if (Objects.equals(accPasswd, accPasswordStore))
                    {
                        //make account and go back
                        System.out.println("Account created");
                        bank.addBankAccount(accPasswd);
                        isClosing = true;
                        processFinish(false);
                    }
                    else{
                        accPasswd = "";
                        accPasswordStore = "";
                        accNumber = -1;
                        display1 = "";
                        display2 = "Input New Account Password ( Last Set Different )";
                        display3 = "";
                    }
                    view.update();
                    //uinputing second password
                }
                break;
            case DEPOSIT:
                processDeposit();
                break;
            case LOGGED_IN:
                Debug.trace("DEBUG | Model::processEnter() case LOGGED_IN");
                break;
            case SETTINGS:
                Debug.trace("DEBUG | Model::processEnter() case SETTINGS");
                break;
            case MONEYTRANSFER:
                Debug.trace("DEBUG | Model::processEnter() case MONEYTRANSFER");
                if (Objects.equals(accPasswd, " "))
                {
                    if (bank.transferMoney(accNumber,number))
                    {
                        display2 = "Account Number: " + bank.account.accNumber + "\nAccount Password: *** \nAccount Balance: " + bank.account.getBalance() +
                                "\n Transfer ["+ number +"] to [" + accNumber + "] Valid";

                        if (bank.account.isLow())
                        {
                            display2 += "\nAccount Funds Low";
                        }
                        receipt += "\nTransfer ["+ number +"] to [" + accNumber + "] Completed";
                    }
                    else{
                        display2 = "Account Number: " + bank.account.accNumber + "\nAccount Password: *** \nAccount Balance: " + bank.account.getBalance() +
                                "\nTransfer ["+ number +"] to [" + accNumber + "] Invalid";
                        receipt += "\nTransfer ["+ number +"] to [" + accNumber + "] Failed";
                    }
                    setState(MANAGMENT);
                    ProcessTransfer(true);
                }
                else{
                    accNumber = number;
                    number = 0;
                    accPasswd = " ";
                }
                break;
            default:
        }
        view.update();  // update the GUI
        return isClosing;
    }

    public void openSettings()
    {
        Debug.trace("DEBUG | Model::processAccount() setState(SETTINGS)");
        setState(SETTINGS);
        display2 = "Account Number: " + bank.account.accNumber + "\nBalance: " + bank.account.getBalance() +"\nOverdraft: "+ bank.account.getOverdraft();
        view.changeScene("Settings");
    }

    public void processWithdraw() {

        if (state.equals(MANAGMENT))
        {
            view.changeScene("Input");
            setState(WITHDRAW);
            title = "Input Withdraw Amount";
            display1 = "";
            display2 = "";
            number = 0;
        }
        else {
            int withdrawResult = bank.account.withdraw(number);
            if (withdrawResult == 1) {
                display2 = "Withdrawn: " + number;
                if (bank.account.isLow())
                {
                    display2 += "\n Balance is Low!";
                }
                receipt += "\nMoney Withdrawn: " + number;
            } else if (withdrawResult == 2) {
                display2 = "You do not have sufficient funds";
            } else {
                display2 = "Withdraw amount exceeds maximum allowed.";
            }
            number = 0;
            display1 = "";
        }
        view.update();
    }
    public void processNo()
    {
        if (state.equals(ACCOUNTCREATION))
        {
            openSettings();
        }
        else{
            toTransition();
        }
    }
    public void ProcessTransfer(boolean changeDisplay)
    {
        if (state.equals(MANAGMENT))
        {
            accPasswd = "";
            setState(MONEYTRANSFER);
            display1 = "";
            if (!changeDisplay)
            {
                display2 = "Account Number: " + bank.account.accNumber + "\nAccount Password: *** \nAccount Balance: " + bank.account.getBalance();
            }
            display3 = "";
            title = "Recipient ID number";
            titl2 = "Transfer Amount";
            number = 0;
            view.changeScene("DoubleInput");
        }
    }
    public void processYes()
    {
        if (state.equals(ACCOUNTCREATION))
        {
            if (bank.isFull())
            {
                display1 = "Bank is Full";
                view.update();
            }
            else{
                accNumber = -1;
                accPasswd = "";
                setState(ACCOUNTCREATION);
                display1 = "";//numbers appear here
                display2 = "new Account ID : " + bank.nextAccountNumber + "\n" +
                        "new AccountPassword ### \n" +
                        "Account type : BASIC";//data apears here
                display3 = "";
                title = "New Account Password";
                titl2 = "New Password Again";
                number = 0;
                view.changeScene("DoubleInput");
            }
        }
        else{
            toTransition();
        }
    }
    public void createAccount()
    {
        if (state.equals(SETTINGS))
        {
            display1 = "Would you like to create a new account?";
            view.changeScene("ConfirmMenu");
            view.update();
            setState(ACCOUNTCREATION);
        }
    }
    // Deposit button - check we are logged in and if so try and deposit some money into
    // the bank (number is the amount showing in the interface display)
    public void processDeposit() {
        if (state.equals(MANAGMENT))
        {
            view.changeScene("Input");
            setState(DEPOSIT);
            title = "Input Deposit Amount";
            display1 = "";
            display2 = "";
            number = 0;
        }
        else{
            if (bank.deposit(number)) {
                display1 = "";
                display2 = "Deposited: " + number;
                receipt += "\nMoney Deposited: " + number;
                number = 0;
            } else {
                display2 = "You have reached the maximum amount of money allowed to be held in the account." +
                        "\nDeposit rejected.";
            }
        }
        view.update();  // update the GUI
    }

    public void processBalance() {
        if (state.equals(LOGGED_IN) || state.equals(SETTINGS)) {
            view.changeScene("Default");
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
    public void processFinish(boolean firstTime) {
        if (!firstTime) {
            view.playButtonSound("Close.mp3");
        }
        receipt = "";
        view.closeReceipt();
        Debug.trace("DEBUG | Model::processFinish()");
        initialise("You are not logged in");
        setState(ACCOUNT_NO);
        view.changeScene("Login");
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
        if (state.equals(MANAGMENT))
        {
            openSettings();
        }
        view.update();
    }
    public void processBack()
    {
        if (state.equals(ACCOUNTCREATION))
        {
            openSettings();
        }
        else{
            toTransition();
        }
    }
}

