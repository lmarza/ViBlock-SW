package Utils;

import Controller.*;
import Data.Entrata;
import Data.Manager;
import Data.Person;
import Data.RiepilogoGiornaliero;
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



            primaryStage.setTitle("Riepilogo Giornaliero - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageMonthSummary(Stage primaryStage, ArrayList<Manager> managers, ArrayList<RiepilogoGiornaliero> dailySummaries) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/riepilogoMensile.fxml"));

            root = fxmlLoader.load();
            ControllerMonthSummary controllerMonthSummary = fxmlLoader.getController();
            controllerMonthSummary.setManagers(managers);
            controllerMonthSummary.setSideScreen();
            controllerMonthSummary.setTableView(dailySummaries);



            primaryStage.setTitle("Riepilogo Mensile - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageTryEnter(Stage primaryStage, ArrayList<Manager> managers) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ingressoProva.fxml"));

            root = fxmlLoader.load();
            ControllerTryEnter controllerTryEnter = fxmlLoader.getController();
            controllerTryEnter.setManagers(managers);
            controllerTryEnter.setSideScreen();

            primaryStage.setTitle("Ingresso prova - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageNewMembership(Stage primaryStage, ArrayList<Manager> managers) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/tesseramento.fxml"));

            root = fxmlLoader.load();
            ControllerMembership controllerMembership = fxmlLoader.getController();
            controllerMembership.setManagers(managers);
            controllerMembership.setSideScreen();

            primaryStage.setTitle("Tesseramento - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStageEntrace(Stage primaryStage, ArrayList<Manager> managers, Person person) {
        Parent root;

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/ingresso.fxml"));

            root = fxmlLoader.load();
            ControllerEntrance controllerEntrance = fxmlLoader.getController();
            controllerEntrance.setPerson(person);
            controllerEntrance.setManagers(managers);
            controllerEntrance.setSideScreen();


            primaryStage.setTitle("Ingresso - Vi Block");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
