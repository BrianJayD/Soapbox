package sample;

import com.sun.org.apache.xerces.internal.impl.xs.SchemaNamespaceSupport;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

/**
 * Read README.txt on how to run
 */

public class Main extends Application implements writeOnInterface {
    // Integers for User information
    private int count = 0;
    private int portID = 1010;
    private int usersPort = 0;
    private int peersPort = 0;

    //set global variables for the log in screen
    private BorderPane logPane = new BorderPane();
    private BorderPane titlePane = new BorderPane();
    private GridPane logGrid = new GridPane();
    private TextField userInput = new TextField();
    private PasswordField passInput = new PasswordField();
    private Label titleLabel = new Label();
    private Label userLabel = new Label();
    private Label passLabel = new Label();
    private Label errLabel = new Label("");
    private Button signInButton = new Button("Sign In");
    private Button regButton = new Button("Register");

    // set global variables for the main chat
    private Stage chatStage = new Stage();
    private TextArea messagesArea = new TextArea();
    private Label uName = new Label();
    private TextField sendMsgField = new TextField();
    private Button sendButton = new Button("Send");

    // set global variables for the menu
    private MenuBar menuBar = new MenuBar();
    private Menu sbMenu = new Menu("Soapbox");
    private Menu helpMenu = new Menu("Help");
    private MenuItem chatConnect = new MenuItem("New Chat");
    private MenuItem signOut = new MenuItem("Sign Out");
    private MenuItem exitProgram = new MenuItem("Exit");
    private MenuItem helpItem = new MenuItem("FAQ");

    // set global variables for Register User
    private Button addUser = new Button("Register");
    private TextField regUser = new TextField();
    private PasswordField regPass = new PasswordField();

    // set global variables for new chat connect
    private Stage newConnect = new Stage();
    private GridPane connectGrid = new GridPane();
    private Label searchUser = new Label();
    private Label searchPort = new Label();
    private TextField uSearchField = new TextField();
    private TextField pSearchField = new TextField();
    private Button connectUserButton = new Button("Connect by User");
    private Button connectPortButton = new Button("Connect by Port");

    @Override
    public void start(Stage primaryStage) throws Exception {
        //BorderPane for log in window
        logGrid.setPadding(new Insets(10, 10, 10, 10));
        logGrid.setPrefSize(400, 400);

        //Set up menu
        menuBar.getMenus().addAll(sbMenu, helpMenu);
        sbMenu.getItems().addAll(chatConnect, signOut, new SeparatorMenuItem(), exitProgram);
        helpMenu.getItems().addAll(helpItem);

        titlePane.setPadding(new Insets(25, 0, 0, 0));

        //Set up for log in window
        titleLabel.setText("Soapbox");
        titleLabel.setFont(Font.font(35));
        userLabel.setText("Username: ");
        passLabel.setText("Password: ");

        userInput.setPromptText("Enter username");
        passInput.setPromptText("Enter password");

        logGrid.setHgap(10);
        logGrid.setVgap(10);
        logGrid.setAlignment(Pos.CENTER);
        logGrid.add(userLabel, 0, 0);
        logGrid.add(userInput, 1, 0);
        logGrid.add(passLabel, 0, 1);
        logGrid.add(passInput, 1, 1);
        logGrid.add(signInButton, 0, 2);
        logGrid.add(regButton, 1, 2);
        logGrid.add(errLabel, 0, 3);

        titlePane.setCenter(titleLabel);
        logPane.setTop(titlePane);
        logPane.setCenter(logGrid);


        //Set up bar to enter new chat and new username
        uName.setText("Username: ");
        uName.setPrefWidth(75);


        //Set up chat window
        messagesArea.setEditable(false);
        messagesArea.setPrefHeight(260);

        sendMsgField.setEditable(true);
        sendMsgField.setPrefWidth(425);

        HBox msgSendArea = new HBox(10, uName, sendMsgField, sendButton);


        // Show log screen
        Scene logScene = new Scene(logPane);
        primaryStage.setScene(logScene);
        primaryStage.setTitle("Soapbox");
        primaryStage.show();

        //Show new Scene when Sign In button pressed
        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    String fileName = "User.txt";
                    File file = new File(fileName);
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    String name = userInput.getText().toLowerCase();
                    String pword = passInput.getText();
                    String line;
                    String[] nameCheck;
                    boolean info = false;

                    //if the file doesn't exist, will prompt the user to register first
                    if(!file.exists()) {
                        errLabel.setText("Error! no user data. Please Register");
                        ((Node)(event.getSource())).getScene().getWindow().hide();
                    }
                    /*Reads the file line by line and sees if the username and password the user enters match with
                    data from the file*/
                    else {
                        count = 0;
                        while ((line = br.readLine()) != null) {
                            nameCheck = line.split(",");
                            if (name.equals(nameCheck[0]) && pword.equals(nameCheck[1])) {
                                info = true;
                                break;
                            } else {
                                info = false;
                            }
                            count++;
                        }
                    }

                    if (info == true) {
                        System.out.println("Login successful");
                        //gives each username a unique port id
                        String usr = userInput.getText();

                        //Hides current window and displays chat window
                        primaryStage.hide();

                        //BorderPane for chat window
                        BorderPane chatWindow = new BorderPane();
                        BorderPane root = new BorderPane();
                        root.setPadding(new Insets(25, 25, 25, 25));
                        root.setPrefSize(625, 350);

                        root.setCenter(messagesArea);
                        root.setBottom(msgSendArea);
                        chatWindow.setTop(menuBar);
                        chatWindow.setCenter(root);

                        Scene chatScene = new Scene(chatWindow);
                        chatStage.setScene(chatScene);
                        chatStage.setTitle("Soapbox");
                        chatStage.show();

                        messagesArea.appendText("Username: " + usr + "\nPort ID: " + (portID + ((count)*10))
                                + "\nClick on 'New Chat' in the Soapbox menu to start chatting.\n\n");
                        usersPort = (portID + ((count)*10));
                        uName.setText(userInput.getText());
                    }
                    else {
                        errLabel.setText("Error! Invalid information");
                    }
                } catch (IOException e) {}
            }
        });


        //Setting up button, text field and menu item functions
        regButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage regStage = new Stage();
                GridPane regPane = new GridPane();
                regPane.setPadding(new Insets(10, 10, 10, 10));
                regPane.setVgap(5);
                regPane.setHgap(5);
                Label errLabel = new Label("");

                regUser.setPromptText("Enter username (no spaces)");
                regPass.setPromptText("Enter your password");

                regPane.add(regUser, 0, 0);
                regPane.add(regPass, 0, 1);
                regPane.add(addUser, 0, 2);
                regPane.add(errLabel, 0, 3);

                //4................Add User Button
                addUser.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    /*creates a user file and stores the username and password created by
                    the user in the file
                     */
                    public void handle(ActionEvent event) {
                        try {
                            String fileName = "User.txt";
                            File file = new File(fileName);
                            PrintWriter outWrite = null;
                            //If the file doesn't exist, the program then creates the file
                            if (!file.exists()) {
                                outWrite = new PrintWriter(new FileOutputStream(new File(fileName), true));
                                outWrite.println(regUser.getText() + "," + regPass.getText() + "," + portID);
                                outWrite.close();
                                ((Node) (event.getSource())).getScene().getWindow().hide();
                            } else {
                                //If the file already exits, this concats the newest username and password to the file

                                FileWriter fw = new FileWriter(fileName, true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter pw = new PrintWriter(bw);
                                BufferedReader br = new BufferedReader(new FileReader(fileName));
                                String line;
                                String[] nameCheck = null;
                                String name = regUser.getText().toLowerCase();

                                boolean duplicate = false;
                                count = 0;
                                //checks to see if their are duplicate usernames in the file
                                while ((line = br.readLine()) != null) {
                                    nameCheck = line.split(",");
                                    if (name.equals(nameCheck[0].toLowerCase())) {
                                        duplicate = true;
                                    }
                                    count++;
                                }

                                //displays an error if their are duplicate names in the file
                                if (duplicate == true) {
                                    errLabel.setText("Error! Username already in use!");
                                } else {
                                    pw.println(regUser.getText() + "," + regPass.getText() + "," + (portID + (count * 10)));
                                    System.out.println("Registration complete");
                                    ((Node) (event.getSource())).getScene().getWindow().hide();
                                }
                                pw.flush();
                                pw.close();
                                bw.close();
                                fw.close();
                            }
                        } catch (IOException e) {
                        }
                    }
                });
                regStage.setTitle("Register");
                regStage.setScene(new Scene(regPane,225,125));
                regStage.show();
            }
        });


        chatConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Reset new chat connect fields
                uSearchField.clear();
                pSearchField.clear();

                searchUser.setText("Search by user:");
                searchPort.setText("Search by port:");

                Label connectTitle = new Label();
                connectTitle.setText("New Chat");
                connectTitle.setFont(Font.font(35));
                connectGrid.setHgap(10);
                connectGrid.setVgap(10);
                connectGrid.add(connectTitle, 0, 0);
                connectGrid.add(searchUser, 0, 1);
                connectGrid.add(uSearchField, 1, 1);
                connectGrid.add(connectUserButton, 0, 2);
                connectGrid.add(searchPort, 0, 3);
                connectGrid.add(pSearchField, 1, 3);
                connectGrid.add(connectPortButton, 0, 4);

                Scene connectScene = new Scene(connectGrid);
                newConnect.setScene(connectScene);
                newConnect.show();

                connectUserButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        messagesArea.clear();

                        if (!uSearchField.getText().isEmpty()) {
                            peersPort = searchName(uSearchField.getText());

                            connectButtonAction(event, peersPort);
                            messagesArea.appendText("Your port: " + usersPort + "\nSUCCESS: Connected to port: "
                                    + peersPort + "\n\n");

                            //gets information for searched user
                            Search(peersPort);
                        } else {
                            messagesArea.appendText("Your port: " + usersPort);
                            messagesArea.appendText("Unable to connect");
                        }

                        newConnect.hide();
                    }
                });

                connectPortButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        messagesArea.clear();

                        if (!pSearchField.getText().isEmpty()) {
                            peersPort = Integer.parseInt(pSearchField.getText());

                            connectButtonAction(event, peersPort);
                            messagesArea.appendText("Your port: " + usersPort + "\nSUCCESS: Connected to port: "
                                    + peersPort + "\n\n");

                            //gets information for searched user
                            Search(peersPort);

                        } else {
                            messagesArea.appendText("Your port: " + usersPort);
                            messagesArea.appendText("Unable to connect");
                        }

                        newConnect.hide();
                    }
                });
            }
        });

        signOut.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //reset chat window
                messagesArea.clear();
                sendMsgField.clear();
                chatStage.close();

                //Re-open log in window
                userInput.clear();
                passInput.clear();
                primaryStage.show();
            }
        });

        exitProgram.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        sendMsgField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER) {
                    sendButtonAction(event);
                    messagesArea.appendText(uName.getText() + ": " + sendMsgField.getText() + "\n");
                    sendMsgField.clear();
                }
            }
        });

        sendButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sendButtonAction(event);
                messagesArea.appendText(uName.getText() + ": " + sendMsgField.getText() + "\n");
                sendMsgField.clear();
            }
        });

        helpItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                messagesArea.appendText("\n\nSOAPBOX\nTo chat with a peer, click on the Soapbox drop down menu and"
                        + " click on the 'New Chat' option.\nYou can either connect by username by entering" +
                        " in the username and hitting the 'Search by User' button.\nYou can also connect by " +
                        "port number if you know a users port ID.\n\nCreators:\nBrian Domingo\nWesley Lawrence" +
                        "\nOmar Khan\n\n");
            }
        });
    }

    // write() method from Writeable GUI interface
    @Override
    public void write(String s) {
        messagesArea.appendText(s + "\n");
    }

    Server listener;

    //connectButtonAction method to call on the server and starts the thread
    private void connectButtonAction(ActionEvent e, int port) {
        // "this" is the gui
        listener = new Server(this, port);

        //Start Thread
        listener.start();
    }

    //Same as the connectButtonAction method but with the send button
    private void sendButtonAction(ActionEvent e) {
        String user = uName.getText();
        String msg = sendMsgField.getText();
        //Set ip default to localhost
        String ip = "localhost";

        Client transmit = new Client(user, msg, ip, usersPort);

        //Start Thread
        transmit.start();
    }

    private void sendButtonAction(KeyEvent e) {
        String user = uName.getText();
        String msg = sendMsgField.getText();
        //Set ip default to localhost
        String ip = "localhost";

        Client transmit = new Client(user, msg, ip, usersPort);

        //Start Thread
        transmit.start();
    }

    public void Search(int port) {
        try {
            String fileName = "User.txt";
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String[] userCheck = null;
            String line;
            String userInfo= " ";
            String portValue = Integer.toString(port);

            if (!file.exists())
            {
                System.out.println("Error! file doesn't exist");
            }
            else
            {
                while((line = br.readLine()) != null) {
                    userCheck = line.split(",");
                    if(portValue.equals(userCheck[2]))
                    {
                        userInfo = "SEARCHED FOR...\nUser: " + userCheck[0] + "\nPort: " + portValue + "\n";
                    }
                }
            }
            messagesArea.appendText(userInfo);
        } catch (IOException e){
            messagesArea.appendText("Could not find user.");
        }
    }

    public int searchName(String name) {
        try {
            String fileName = "User.txt";
            File file = new File(fileName);
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String[] userCheck = null;
            String line;
            String userInfo= " ";

            if (!file.exists()) {
                System.out.println("Error! file doesn't exist");
            }
            else {
                while((line = br.readLine()) != null) {
                    userCheck = line.split(",");
                    if(name.equals(userCheck[0])) {
                        String uPortCheck = userCheck[2];
                        int uPortCheckInt = Integer.parseInt(uPortCheck);
                        return uPortCheckInt;
                    }
                }
            }
        } catch (IOException e) {
            messagesArea.appendText("Could not find user.");
        }
        return 0;
    }

    public static void main(String[] args) {
        launch(args);
    }
}