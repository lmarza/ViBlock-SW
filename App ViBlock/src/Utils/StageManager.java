package Utils;

import Controller.ControllerDaySummary;
import Controller.ControllerLoginPage;
import Controller.ControllerMainPage;
import Data.Entrata;
import Data.Manager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.ArrayList;

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
            primaryStage.getIcons().add(new Image("images/Vi block.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageMainPage(Stage primaryStage, ArrayList<Manager> managers) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/page1.fxml"));

            root = fxmlLoader.load();
            ControllerMainPage controllerMainPage = fxmlLoader.getController();
            controllerMainPage.setManagers(managers);
            controllerMainPage.setSideScreen();



            primaryStage.setTitle("MainPage - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageDaySummary(Stage primaryStage, ArrayList<Manager> managers, ArrayList<Entrata> records) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/riepilogoGiornata.fxml"));

            root = fxmlLoader.load();
            ControllerDaySummary controllerDaySummary = fxmlLoader.getController();
            controllerDaySummary.setManagers(managers);
            controllerDaySummary.setSideScreen();
            controllerDaySummary.setTableView(records);



            primaryStage.setTitle("MainPage - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
