package com.bitwave.java_atmapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class numpadwindow extends Application {
    private NumberPadCallback callback;
    private String action;
    private StringBuilder inputAmount = new StringBuilder();
    private Label displayLabel;

    public numpadwindow(NumberPadCallback callback, String action) {
        this.callback = callback;
        this.action = action;
    }

    @Override
    public void start(Stage stage) {
        displayLabel = new Label("Enter Amount: ");
        GridPane grid = new GridPane();

        int number = 1;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button btn = new Button(String.valueOf(number));
                btn.setMinSize(50, 50);
                btn.setOnAction(e -> inputAmount.append(btn.getText()));
                grid.add(btn, col, row);
                number++;
            }
        }

        Button zeroBtn = new Button("0");
        zeroBtn.setMinSize(50, 50);
        zeroBtn.setOnAction(e -> inputAmount.append("0"));
        grid.add(zeroBtn, 1, 3);

        Button confirmBtn = new Button("✔");
        confirmBtn.setMinSize(50, 50);
        confirmBtn.setOnAction(e -> {
            if (inputAmount.length() > 0) {
                double amount = Double.parseDouble(inputAmount.toString());
                callback.onAmountEntered(amount, action);
                stage.close();
            }
        });

        Button cancelBtn = new Button("❌");
        cancelBtn.setMinSize(50, 50);
        cancelBtn.setOnAction(e -> stage.close());

        grid.add(confirmBtn, 2, 3);
        grid.add(cancelBtn, 0, 3);

        VBox layout = new VBox(10, displayLabel, grid);
        layout.setStyle("-fx-padding: 20px; -fx-alignment: center;");

        stage.setScene(new Scene(layout, 400, 500));
        stage.setTitle(action + " Amount");
        stage.setResizable(false);
        stage.show();

    }

    public interface NumberPadCallback {
        void onAmountEntered(double amount, String action);
    }




























}

