package Controller;


import Data.Entrata;
import Data.Manager;
import Utils.StageManager;
import View.ViewRiepilogoGiornata;
import View.ViewRiepilogoGiornataJFX;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerDaySummary {

    @FXML
    private ImageView homePageImageView;

    @FXML
    private Label user1Label;

    @FXML
    private Label user2Label;

    @FXML
    private Label dateLabel;

    @FXML
    private Label saldoAperturaLabel;

    @FXML
    private Label saldoGiorLabel;

    @FXML
    private Label totCassaLabel;

    @FXML
    private Label PersEntrateLabel;

    @FXML
    private Label prelievoLabel;

    @FXML
    private TableView<Entrata> RiepilogoTableView;

    private ArrayList<Manager> managers;

    @FXML
    private void initialize()
    {
        homePageImageView.setOnMouseClicked(this::handleHomePageImageView);
    }



    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }

    public void setTableView(ArrayList<Entrata> records)
    {
        RiepilogoTableView.getColumns().clear();
        ViewRiepilogoGiornata viewRiepilogoGiornata = new ViewRiepilogoGiornataJFX();
        viewRiepilogoGiornata.buildDaySummary(records, RiepilogoTableView);

    }

    private void handleHomePageImageView(MouseEvent mouseEvent) {
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);
    }
}

