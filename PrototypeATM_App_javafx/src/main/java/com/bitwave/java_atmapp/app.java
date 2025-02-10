package com.bitwave.java_atmapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class app extends Application implements numpadwindow.NumberPadCallback {

    private Label statusLabel;
    private double balance = 1000.0;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Label pinLabel = new Label("Enter PIN");
        PasswordField pinField = new PasswordField();
        Button submitButton = new Button("Submit");

        statusLabel = new Label("Status: Waiting for PIN...");

        submitButton.setOnAction(e -> handlePinEntry(pinField.getText()));

        VBox pinLayout = new VBox(10, pinLabel, pinField, submitButton, statusLabel);
        pinLayout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        primaryStage.setScene(new Scene(pinLayout, 400, 500));
        primaryStage.setTitle("Java ATM App v0.1");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handlePinEntry(String pin) {
        if (pin.equals("1234")) {
            statusLabel.setText("PIN Accepted. Loading Account...");
            openAccountOptions();
        } else {
            statusLabel.setText("Incorrect PIN.Try again.");
        }
    }

    private void openAccountOptions() {
        Label balanceLabel = new Label("Balance: £" + balance);
        Button widrawButton = new Button("Widraw");
        Button depositButton = new Button("Deposit");
        Button exitButton = new Button("Exit");

        statusLabel = new Label("Select an option.");

        widrawButton.setOnAction(e -> openNumberPad("Withdraw"));
        depositButton.setOnAction(e -> openNumberPad("Deposit"));
        exitButton.setOnAction(e -> primaryStage.close());

        VBox optionsLayout = new VBox(10, balanceLabel, widrawButton, depositButton, exitButton, statusLabel);
        optionsLayout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        primaryStage.setScene(new Scene(optionsLayout, 400, 500));

    }

    private void openNumberPad(String action) {
        numpadwindow numPad = new numpadwindow(this, action);
        numPad.start(new Stage());
    }

    @Override
    public void onAmountEntered(double amount, String action) {
        if (action.equals("Withdraw")) {
            if (amount > balance) {
                statusLabel.setText("Insufficient funds.");
            } else {
                balance -= amount;
                statusLabel.setText("Withdrawal successful.");
                statusLabel.setText("New Balance: £" + balance);
            }
        } else if (action.equals("Deposit")) {
            balance += amount;
            statusLabel.setText("Deposit successful.");
            statusLabel.setText("New Balance: £" + balance);
        }
    }

}