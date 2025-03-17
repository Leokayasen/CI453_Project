package com.ci453.atmapp.atmapp_v07a;

// The View class creates and manages the GUI for the application.

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

class View {
    int H = 622;         // Height of window pixels
    int W = 450;         // Width  of window pixels

    // variables for components of the user interface
    Label title;
    TextField message;
    TextArea reply;
    ScrollPane scrollPane;
    GridPane grid;
    TilePane buttonPane;



    // The other parts of the model-view-controller setup
    public Model model;
    public Controller controller;

    // We don't really need a constructor method, but include one to print a debugging message if required
    public View() {
        Debug.trace("DEBUG | View::View()");
    }

    // Start is called from Main, to start the GUI up
    public void start(Stage window) {
        Debug.trace("DEBUG | View::start()");
        mainWindow = window;
        changeScene("Login");
        mainWindow.show();
        // layout objects
        //changeScene("Default");
    }

    Stage mainWindow;

    public void changeScene(String type) {
        Scene scene = null;
        if (type.equals("Default")) {
            Debug.trace("DEBUG | View::changeScene()");
            grid = new GridPane();
            grid.setId("Layout");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);

            message = new TextField();
            message.setEditable(false);
            grid.add(message, 0, 1);

            reply = new TextArea();
            reply.setEditable(false);
            scrollPane = new ScrollPane();
            scrollPane.setContent(reply);
            grid.add(scrollPane, 0, 2);

            mainWindow.setResizable(false);
            mainWindow.setTitle("CI453 Banking App");

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {"CLEAR", "0", "ENTER"},
                    {"", "Finish", ""},
                    {"Account", "", "Balance"},
                    {"Deposit", "", "Withdraw"}
            };

            // loop through the array, making a Button object for each label
            for (String[] row : labels) {
                for (String label : row) {
                    if (!label.isEmpty()) {
                        Button b = new Button(label);
                        b.setOnAction(this::buttonClicked); // set the method to call when pressed
                        buttonPane.getChildren().add(b);    // and add to tiled pane
                    } else {
                        // empty string - add an empty text element as a spacer
                        buttonPane.getChildren().add(new Text());
                    }
                }
            }
            grid.add(buttonPane, 0, 3); // add the tiled pane of buttons to the grid
            scene = new Scene(grid, W, H);
            // add the complete GUI to the window and display it
        } else if (type.equals("Login")) {
            grid = new GridPane();
            grid.setId("Layout");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");

            // controls
            title = new Label();
            grid.add(title, 0, 0);

            message = new TextField();
            message.setEditable(false);
            grid.add(message, 0, 1);

            reply = new TextArea();
            reply.setMaxSize(400, 200);
            reply.setEditable(false);
            scrollPane = new ScrollPane();
            scrollPane.setContent(reply);
            grid.add(scrollPane, 0, 2);

            mainWindow.setResizable(false);
            mainWindow.setTitle("CI453 Banking App");


            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {"CLEAR", "0", "ENTER"}};

            // loop through the array, making a Button object for each label
            for (String[] row : labels) {
                for (String label : row) {
                    if (!label.isEmpty()) {
                        Button b = new Button(label);
                        b.setOnAction(this::buttonClicked); // set the method to call when pressed
                        buttonPane.getChildren().add(b);    // and add to tiled pane
                    } else {
                        // empty string - add an empty text element as a spacer
                        System.out.println("spacer");
                        buttonPane.getChildren().add(new Text());
                    }
                }
            }
            grid.add(buttonPane, 0, 3); // add the tiled pane of buttons to the grid
            scene = new Scene(grid, 422, 450);
        } else {
            changeScene("Login");
        }
        assert scene != null;
        scene.getStylesheets().add("/atm.css"); // tell the app to use our css file
        mainWindow.setScene(scene);
    }

    // This is how the View talks to the Controller
    public void buttonClicked(ActionEvent event) {
        // this line asks the event to provide the actual Button object that was clicked
        Button b = ((Button) event.getSource());
        if (controller != null) {
            String label = b.getText();   // get the button label
            Debug.trace("DEBUG | View::buttonClicked() label = " + label);
            // Try setting a breakpoint here
            controller.process(label);  // Pass it to the controller's process method
        }
    }

    // This is how the Model talks to the View
    public void update() {
        if (model != null) {
            Debug.trace("View::update");
            String message1 = model.title;        // get the new title from the model
            title.setText(message1);              // set the message text to be the title
            String message2 = model.display1;     // get the new message1 from the model
            message.setText(message2);          // add it as text of GUI control output1
            String message3 = model.display2;     // get the new message2 from the model
            reply.setText(message3);            // add it as text of GUI control output2
        }

    }
}