package com.atm;

import javafx.application.Application;
import javafx.stage.Stage;


//Main Running Class
//Handles Defaults

public class Main extends Application
{

    public void start(Stage window)
    {
        // set up debugging and print initial debugging message
        Debug.set(true);
        Debug.trace("ATM starting");
        Debug.trace("Main::start");

        // Create a Bank object for this ATM
        Bank b = new Bank();
        // add some test bank accounts
        b.addBankAccount(117, 111, 100, 1000);
        b.addBankAccount(10002, 22222, 50, 1000);

        // Create the Model, View and Controller objects
        Model model = new Model(b);   // the model needs the Bank object to 'talk to' the bank
        View  view  = new View();
        Controller controller  = new Controller();

        // Link them together so that they can talk to each other
        // Each one has instances variable for the other two
        model.view = view;
        model.controller = controller;

        controller.model = model;
        controller.view = view;

        view.model = model;
        view.controller = controller;

        // start up the GUI (view), and then tell the model to initialise and display itself
        view.start(window);
        model.initialise("Welcome to the ATM");
        view.update();

        // application is now running
        Debug.trace("atm running");
    }

    public static void main( String args[] )
    {
        // The main method only gets used when launching from the command line
        // launch initialises the system and then calls start
        // In BlueJ, BlueJ calls start itself
        launch(args);
    }
}
