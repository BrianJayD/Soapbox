package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Read README.txt on how to run
 */

public class Main extends Application implements writeOnInterface {

    // set global variables
    private TextArea messagesArea = new TextArea();
    private TextField recievePort = new TextField();
    private TextField targetPort = new TextField();
    private TextField userField = new TextField();
    private Label uName = new Label();
    private TextField sendMsgField = new TextField();
    private Button connectButton = new Button("Connect");
    private Button sendButton = new Button("Send");

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setPrefSize(600, 350);

        //Set up bar to enter new chat and new username
        recievePort.setPromptText("Receive Port");
        userField.setPromptText("Username");
        targetPort.setPromptText("Target Port");
        uName.setText("Username: ");
        uName.setPrefWidth(75);


        connectButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connectButtonAction(event);
                messagesArea.appendText("SUCCESS: Connected to port: " + targetPort.getText() + "\nUsername: " +
                                        userField.getText() + "\nPort: " + recievePort.getText() + "\n\n");
                uName.setText(userField.getText() + ": ");
            }
        });

        HBox newChat = new HBox(10, userField, recievePort, targetPort, connectButton);
        newChat.setMaxHeight(30);

        messagesArea.setEditable(false);
        messagesArea.setPrefHeight(260);

        sendMsgField.setEditable(true);
        sendMsgField.setPrefWidth(425);

        sendButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendButtonAction(event);
                messagesArea.appendText(userField.getText() + ": " + sendMsgField.getText() + "\n");
                sendMsgField.clear();
            }
        });

        HBox msgSendArea = new HBox(10, uName, sendMsgField, sendButton);
        VBox chatWin = new VBox(10, newChat, messagesArea, msgSendArea);


        root.setTop(chatWin);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Soapbox");
        primaryStage.show();

    }

    // write() method from Writeable GUI interface
    @Override
    public void write(String s) {
        messagesArea.appendText(s + "\n");
    }

    Server listener;

    //connectButtonAction method to call on the server and starts the thread
    private void connectButtonAction(ActionEvent e) {
        int newPort = Integer.parseInt(recievePort.getText());
        // "this" is the gui
        listener = new Server(this, newPort);

        //Start Thread
        listener.start();
    }

    //Same as the connectButtonAction method but with the send button
    private void sendButtonAction(ActionEvent e) {
        String user = uName.getText();
        String msg = sendMsgField.getText();
        //Set ip default to localhost
        String ip = "localhost";
        int port = Integer.parseInt(targetPort.getText());

        Client transmit = new Client(user, msg, ip, port);

        //Start Thread
        transmit.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
