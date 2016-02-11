package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        Scene logIn = new Scene(grid, 500, 500);
        primaryStage.setTitle("Soapbox");
        primaryStage.setScene(logIn);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
