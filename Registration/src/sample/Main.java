package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.security.util.Password;

import java.io.*;


/*
Created by Wesley Lawrence

1................Login button
2................Enter Button
3................Register Button
4................Add User Button

 */

public class Main extends Application {
    //global variables to keep track of the current username, and their port id
    private String Username;
    private int portID = 1010;
    private int count = 0;


    @Override
    public void start(Stage primaryStage) throws Exception{

        //First scene where the user can login or register
        Button register = new Button();
        Button login = new Button();
        Button addUser = new Button();
        Button enter = new Button();
        register.setText("Register");
        addUser.setText("Add User");
        login.setText("Login");
        enter.setText("Enter");

        //1................Login button


        login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage logStage = new Stage();
                GridPane logPane = new GridPane();
                logPane.setPadding(new Insets(15, 15, 15, 15));
                logPane.setVgap(10);
                logPane.setHgap(10);

                //fields created to let the user enter in their username and password
                TextField userName = new TextField();
                PasswordField password = new PasswordField();

                //error label created to display an error message if something goes wrong
                Label errLabel = new Label("");

                userName.setPromptText("Enter username (no spaces)");
                password.setPromptText("Enter your password");

                GridPane.setConstraints(userName,0,0);
                GridPane.setConstraints(password,0,1);
                GridPane.setConstraints(enter,0,2);
                GridPane.setConstraints(errLabel,0,3);
                logPane.getChildren().add(userName);
                logPane.getChildren().add(password);
                logPane.getChildren().add(enter);
                logPane.getChildren().add(errLabel);


                //2................Enter Button
                enter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try{
                            String fileName = "User.txt";
                            File file = new File(fileName);
                            BufferedReader br = new BufferedReader(new FileReader(fileName));
                            String name = userName.getText().toLowerCase();
                            String pword = password.getText();
                            String line;
                            String[] nameCheck;
                            boolean info = false;


                            //if the file doesn't exist, will prompt the user to register firs
                            if(!file.exists())
                            {
                                errLabel.setText("Error! no user data. PLease Register");
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
                                    } else {
                                        info = false;
                                    }
                                    count++;
                                }

                            }

                            if (info == true)
                            {

                                System.out.println("Login successful");
                                //gives each username a unique port id
                                Username = userName.getText();
                                System.out.println("Username: " + Username + " Port ID: " + (portID + ((count-1)*10)));

                                ((Node)(event.getSource())).getScene().getWindow().hide();
                            }

                            else
                            {
                                errLabel.setText("Error! Invalid information");
                            }

                        }catch (IOException e)
                        {

                        }

                    }
                });




                logStage.setTitle("Log in");
                logStage.setScene(new Scene(logPane,225,150));
                logStage.show();
            }



        });



        //3................Register Button

        register.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Stage regStage = new Stage();
                GridPane regPane = new GridPane();
                regPane.setPadding(new Insets(10, 10, 10, 10));
                regPane.setVgap(5);
                regPane.setHgap(5);


                TextField userName = new TextField();
                PasswordField password = new PasswordField();

                Label errLabel = new Label("");

                userName.setPromptText("Enter username (no spaces)");
                password.setPromptText("Enter your password");

                GridPane.setConstraints(userName,0,0);
                GridPane.setConstraints(password,0,1);
                GridPane.setConstraints(addUser,0,2);
                GridPane.setConstraints(errLabel,0,3);
                regPane.getChildren().add(userName);
                regPane.getChildren().add(password);
                regPane.getChildren().add(addUser);
                regPane.getChildren().add(errLabel);

            //4................Add User Button
                addUser.setOnAction(new EventHandler<ActionEvent>() {


                    @Override
                    /*creates a user file and stores the username and password created by
                    the user in the file
                     */

                    public void handle(ActionEvent event){
                        try {
                            String fileName = "User.txt";
                            File file = new File(fileName);
                            PrintWriter outWrite = null;
                            //If the file doesn't exist, the program then creates the file
                            if(!file.exists()) {

                                outWrite = new PrintWriter(new FileOutputStream(new File(fileName), true));
                                outWrite.println(userName.getText() + "," + password.getText() + "," + portID);
                                outWrite.close();


                            }
                            else{
                                //If the file already exits, this concats the newest username and password to the file


                                FileWriter fw = new FileWriter(fileName, true);
                                BufferedWriter bw = new BufferedWriter(fw);
                                PrintWriter pw = new PrintWriter(bw);
                                BufferedReader br = new BufferedReader(new FileReader(fileName));
                                String line;
                                String[] nameCheck = null;
                                String name = userName.getText().toLowerCase();

                                boolean duplicate = false;
                                count = 0;
                                //checks to see if their are duplicate usernames in the file
                                while ((line = br.readLine()) != null)
                                {

                                        nameCheck = line.split(",");
                                    if (name.equals(nameCheck[0].toLowerCase()))
                                    {
                                        duplicate = true;

                                    }
                                    count++;
                                }

                                //displays an error if their are duplicate names in the file
                                if (duplicate == true)
                                {
                                    errLabel.setText("Error! Username already in use!");

                                }
                                else {

                                    pw.println(userName.getText() + "," + password.getText() + "," + (portID + (count*10)));
                                    System.out.println("Registration complete");
                                    ((Node)(event.getSource())).getScene().getWindow().hide();

                                }

                                pw.flush();
                                pw.close();
                                bw.close();
                                fw.close();



                            }
                        } catch (IOException e)
                          {

                          }
                    }
                });


                regStage.setTitle("Register");
                regStage.setScene(new Scene(regPane,225,125));
                regStage.show();
            }
        });


        StackPane spr = new StackPane();
        spr.getChildren().add(register);
        spr.getChildren().add(login);
        VBox buttons = new VBox();
        buttons.setSpacing(10);
        buttons.setPadding(new Insets(30, 20, 10, 20));
        buttons.getChildren().addAll(login,register);
        primaryStage.setTitle("Login/Register");
        primaryStage.setScene(new Scene(buttons, 200, 125));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
