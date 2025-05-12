package com.ci453.ci453_atm_v09;

// The View class creates and manages the GUI for the application.

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;

class View {
    int H = 622;         // Height of window pixels
    int W = 450;         // Width  of window pixels

    // variables for components of the user interface
    Label title;
    Label title2;
    TextField message;
    TextField message2;
    TextArea reply;
    ScrollPane scrollPane;
    GridPane grid;
    TilePane buttonPane;


    String[] imagePaths = new String[]{"OpeningImageTest.png"};

    // The other parts of the model-view-controller setup
    public Model model;
    public Controller controller;
    Stage mainWindow;

    Stage subWindow;

    // We don't really need a constructor method, but include one to print a debugging message if required
    public View() {
        Debug.trace("DEBUG | View::View()");
    }

    // Start is called from Main, to start the GUI up
    public void start(Stage window) {
        Debug.trace("DEBUG | View::start()");
        mainWindow = window;
        changeScene("Start");
        mainWindow.show();
        // layout objects
        //changeScene("Default");
    }

    public void closeReceipt()
    {
        if (subWindow != null)
        {
            subWindow.close();
        }
    }
    public void showReceipt(String text)
    {
        closeReceipt();
        subWindow = new Stage();
        subWindow.setTitle("Receipt");
        subWindow.setResizable(false);
        subWindow.initOwner(mainWindow);
        grid = new GridPane();
        grid.setId("Layout");
        Scene scene = new Scene(grid, 300, 500);

        TextArea receiptText = new TextArea();
        receiptText.setEditable(false);
        receiptText.setText(text);
        receiptText.setPrefHeight(500);
        scrollPane = new ScrollPane();
        scrollPane.setContent(receiptText);

        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);


        grid.add(scrollPane, 0, 2);

        subWindow.setScene(scene);

        subWindow.show();
    }
    public void playButtonSound(String soundName)
    {
        Media media = new Media(new File("src\\main\\resources\\" + soundName).toURI().toString());
        MediaPlayer player = new MediaPlayer(media);
        player.play();
    }




    public void changeScene(String type) {
        Scene scene = null;
        grid = new GridPane();
        grid.setId("Layout");
        if (type.equals("Start")) {
            Debug.trace("DEBUG | View::changeScene()");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);


            ImageView imageView = new ImageView(imagePaths[0]);
            imageView.setFitWidth(380);//450, 425
            imageView.setFitHeight(200);
            grid.add(imageView, 0, 1);

            mainWindow.setResizable(false);

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"", "Start", ""},
                    {"", "", ""}
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
            //117, "111
            scene = new Scene(grid, 400, 425);
        }
        else if (type.equals("ConfirmMenu"))
        {
            Debug.trace("DEBUG | View::changeScene()");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);

            message = new TextField();
            message.setEditable(false);
            grid.add(message, 0, 1);

            mainWindow.setResizable(false);

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"Yes", "", "No"},
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
            //117, "111
            scene = new Scene(grid, 422, 425);
        }
        else if (type.equals("Default")) {
            Debug.trace("DEBUG | View::changeScene()");
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
        }
        else if (type.equals("Transition")) {
            Debug.trace("DEBUG | View::changeScene()");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);

//            message = new TextField();
//            message.setEditable(false);
//            grid.add(message, 0, 1);

            reply = new TextArea();
            reply.setEditable(false);
            reply.setMaxSize(422, 200);

            grid.add(reply, 0, 2);
            mainWindow.setResizable(false);

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"Deposit", "", "Withdraw"},
                    {"Account", "Finish", "Transfer"}
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
            //117, "111
            scene = new Scene(grid, 422, 425);
        }
        else if (type.equals("Settings"))
        {
            Debug.trace("DEBUG | View::changeScene()");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);

//            message = new TextField();
//            message.setEditable(false);
//            grid.add(message, 0, 1);

            reply = new TextArea();
            reply.setEditable(false);
            reply.setMaxSize(422, 200);

            grid.add(reply, 0, 2);
            mainWindow.setResizable(false);

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"Pass", "Acc", "Receipt"},
                    {"", "Back", ""}
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
            //117, "111
            scene = new Scene(grid, 422, 425);
        }
        else if (type.equals("Input")){
            Debug.trace("DEBUG | View::changeScene()");
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
            reply.setMaxSize(422, 200);

            grid.add(reply, 0, 2);

            //scrollPane.fitToWidthProperty();
            //grid.add(scrollPane, 0, 2);

            mainWindow.setResizable(false);
            mainWindow.setTitle("CI453 Banking App");

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {"CLEAR", "0", "ENTER"},
                    {"", "Back", ""},
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
            scene = new Scene(grid, 422, 500);
        }
        else if (type.equals("DoubleInput")){//DoubleInput
            Debug.trace("DEBUG | View::changeScene()");
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");
            // controls
            title = new Label();
            grid.add(title, 0, 0);

            message = new TextField();
            message.setEditable(false);
            grid.add(message, 0, 1);

            title2 = new Label();
            grid.add(title2, 0, 2);

            message2 = new TextField();
            message2.setEditable(false);
            grid.add(message2, 0, 3);

            reply = new TextArea();
            reply.setEditable(false);
            reply.setMaxSize(422, 100);

            grid.add(reply, 0, 4);

            //scrollPane.fitToWidthProperty();
            //grid.add(scrollPane, 0, 2);

            mainWindow.setResizable(false);
            mainWindow.setTitle("CI453 Banking App");

            // Button labels - empty strings are for blank spaces
            String[][] labels = {
                    {"7", "8", "9"},
                    {"4", "5", "6"},
                    {"1", "2", "3"},
                    {"CLEAR", "0", "ENTER"},
                    {"", "Back", ""},
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
            grid.add(buttonPane, 0, 5); // add the tiled pane of buttons to the grid
            scene = new Scene(grid, 422, 500);
        }
        else if (type.equals("Login")) {
            buttonPane = new TilePane();
            buttonPane.setId("Buttons");

            // controls
            title = new Label();
            grid.add(title, 0, 0);
            model.title = "Input account ID";

            message = new TextField();
            message.setEditable(false);
            grid.add(message, 0, 1);

            mainWindow.setResizable(false);

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
            scene = new Scene(grid, 422, 275);//grid,width,height
        } else {
            changeScene("Login");
            return;
        }
        update();
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
            if (reply != null)// add it as text of GUI control output2
            {
                reply.setText(model.display2);
            }
            if (message != null)// add it as text of GUI control output2
            {
                message.setText(model.display1);          // add it as text of GUI control output1
            }
            if (message2 != null)
            {
                message2.setText(model.display3);
            }
            if (title2 != null)
            {
                title2.setText(model.titl2);
            }
            if (title != null)// add it as text of GUI control output2
            {
                title.setText(model.title);              // set the message text to be the title
            }
        }

    }
}