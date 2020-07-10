package Controller;


import Data.Manager;
import Data.RiepilogoGiornaliero;
import Model.ModelDBRiepilogoGiorn;
import Model.ModelRiepilogoGiorn;
import Utils.StageManager;
import View.ViewRiepilogoMensile;
import View.ViewRiepilogoMensileJFX;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerMonthSummary {

    @FXML
    private ImageView homePageImageView;

    @FXML
    private Label user1Label;

    @FXML
    private Label user2Label;

    @FXML
    private Label saldoAperturaLabel;

    @FXML
    private Label saldoGiorLabel;

    @FXML
    private Label totCassaLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label PersEntrateLabel;

    @FXML
    private Label prelievoLabel;

    @FXML
    private JFXComboBox<String> monthJFXComboBox;
    private ObservableList<String> month = FXCollections.observableArrayList();
    @FXML
    private TableView<RiepilogoGiornaliero> riepilogoMensileTableView;

    @FXML
    private JFXButton searchJFXButton;

    private ArrayList<Manager> managers;

    @FXML
    private void initialize()
    {
        populateComboBox();
        monthJFXComboBox.setItems(month);
        homePageImageView.setOnMouseClicked(this::handleHomePageImageView);
        searchJFXButton.setOnAction(this::handleSearchMonthJFXButton);
    }


    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }

    public void setTableView(ArrayList<RiepilogoGiornaliero> dailySummaries)
    {
        riepilogoMensileTableView.getColumns().clear();
        ViewRiepilogoMensile viewRiepilogoMensile= new ViewRiepilogoMensileJFX();
        viewRiepilogoMensile.buildMonthSummary(dailySummaries, riepilogoMensileTableView);

    }

    private void handleHomePageImageView(MouseEvent mouseEvent) {
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);
    }

    private void populateComboBox() {

        month.addAll("Annuale ","1 - Gennaio","2 - Febbraio","3 - Marzo","4 - Aprile","5 - Maggio","6 - Giugno","7 - Luglio","8 - Agosto",
                            "9 - Settembre","10 - Ottobre","11 - Novembre","12 - Dicembre");
    }

    private void handleSearchMonthJFXButton(ActionEvent actionEvent) {
        ArrayList<RiepilogoGiornaliero> monthSummary = new ArrayList<>();
        ModelRiepilogoGiorn modelRiepilogoGiornDB = new ModelDBRiepilogoGiorn();
        String[] parts = monthJFXComboBox.getValue().split(" ");

        if (monthJFXComboBox.getValue().equalsIgnoreCase("Annuale "))
            monthSummary = modelRiepilogoGiornDB.getDailySummaries();
        else
            monthSummary = modelRiepilogoGiornDB.getMonthSummaries(parts[0]);

        riepilogoMensileTableView.getColumns().clear();
        ViewRiepilogoMensile viewRiepilogoMensile= new ViewRiepilogoMensileJFX();
        viewRiepilogoMensile.buildMonthSummary(monthSummary, riepilogoMensileTableView);
    }

}

