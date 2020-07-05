package Utils;

import Controller.ControllerLoginPage;
import Controller.ControllerMainPage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;

public class StageManager {

    public void setStageHomepage(Stage primaryStage) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/homePage.fxml"));

            root = fxmlLoader.load();
            ControllerLoginPage controllerLoginPage = fxmlLoader.getController();

            primaryStage.setTitle("Login - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageMainPage(Stage primaryStage) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/page1.fxml"));

            root = fxmlLoader.load();
            ControllerMainPage controllerMainPage = fxmlLoader.getController();

            primaryStage.setTitle("MainPage - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
