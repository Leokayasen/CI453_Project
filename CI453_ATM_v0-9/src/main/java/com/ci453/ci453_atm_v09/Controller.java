package com.ci453.ci453_atm_v09;

// The ATM controller is quite simple - the process method is passed
public class Controller {
    public Model model;
    public View view;

    // we don't really need a constructor method, but include one to print a
    // debugging message if required
    public Controller() {
        Debug.trace("Controller::<constructor>");
    }

    // This is how the View talks to the Controller, which talks to the Model
    public void process(String action) {
        Debug.trace("Controller::process: action = " + action);
        boolean hasSound = false;
        switch (action) {
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
            case "0":
                model.processNumber(action);
                break;
            case "CLEAR":
                model.processClear();
                break;
            case "ENTER":
                model.processEnter();
                break;
            case "Withdraw":
                model.processWithdraw();
                break;
            case "Deposit":
                model.processDeposit();
                break;
            case "Balance":
                model.processBalance();
                break;
            case "Account":
                model.processAccount();
                break;
            case "Start":
                model.processFinish(true);
                break;
            case "Finish":
                hasSound = true;
                model.processFinish(false);
                //view.changeScene("Start");
                break;
            case "Back":
                model.processBack();
                break;
            case "Transfer":
                model.ProcessTransfer(false);
                break;
            case "Receipt":
                view.showReceipt(model.receipt);
                break;
            case "Pass":
                //view.changeScene("ConfirmMenu");
                System.out.println("not implemented yet");
                break;
            case "Acc":
                model.createAccount();
                break;
            case "No":
                model.processNo();
                break;
            case "Yes":
                model.processYes();
                break;
            default:
                model.processUnknownKey(action);
                break;
        }
        if (!hasSound) {
            view.playButtonSound("ButtonDing.mp3");
        }
    }

}

