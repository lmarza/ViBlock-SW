package Controller;

import Data.Entrata;
import Data.Manager;
import Data.RiepilogoGiornaliero;
import Model.*;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.xml.validation.Validator;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class ControllerMainPage {

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
    private JFXTextField searchJFXTextField;

    @FXML
    private ImageView searchImageView;

    @FXML
    private JFXButton tesseramentoJFXButton;

    @FXML
    private JFXButton ingressoProvaJFXButton;

    @FXML
    private JFXButton riepGiornataJFXButton;

    @FXML
    private JFXButton riepMensileJFXButton;

    @FXML
    private JFXButton prelievoJFXButton;

    @FXML
    private ImageView logoutImageView;

    @FXML
    private JFXButton esportaSaldoGiorJFXButton;

    private ArrayList<Manager> managers;

    @FXML
    private void initialize()
    {
        //set a listener to check if qrcode is passed
        tesseramentoJFXButton.setOnAction(this::handleTesseramentoJFXButton);
        ingressoProvaJFXButton.setOnAction(this::handleIngressoProvaJFXButton);
        riepGiornataJFXButton.setOnAction(this::handleRiepGiornataJFXButton);
        riepMensileJFXButton.setOnAction(this::handleRiepMensileJFXButton);
        prelievoJFXButton.setOnAction(this::handlePrelievoJFXButton);
        esportaSaldoGiorJFXButton.setOnAction(this::handleEsportaSaldoGJFXButton);
        searchImageView.setOnMouseClicked(this::handleSearchImageView);
        logoutImageView.setOnMouseClicked(this::handleLogOutImageView);
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }


    private void handleLogOutImageView(MouseEvent mouseEvent) {
        managers.removeAll(managers);
        StageManager loginPageState = new StageManager();
        loginPageState.setStageHomepage((Stage) logoutImageView.getScene().getWindow());
    }

    private void handleSearchImageView(MouseEvent mouseEvent) {
    }

    private void handleEsportaSaldoGJFXButton(ActionEvent actionEvent) {
        /*first check if one balance for db is already into DB */
        ModelRiepilogoGiorn modelRiepilogoGiornDB = new ModelDBRiepilogoGiorn();
        int row = modelRiepilogoGiornDB.checkIfRecordExists();
        RiepilogoGiornaliero riepilogo = new RiepilogoGiornaliero();
        ControllerAlert confirm = new ControllerAlert();

        Date d = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        String finaleDate = dateFormat.format(d);
        riepilogo.setData(finaleDate);

        String[] s = totCassaLabel.getText().split(" ");
        riepilogo.setSaldoFinale(new BigDecimal(s[0]));
        riepilogo.setTesseramenti(modelRiepilogoGiornDB.getDailyMembership());
        BigDecimal membershipBalance = new BigDecimal(modelRiepilogoGiornDB.getDailyMembership()).multiply(new BigDecimal(20.00));
        riepilogo.setSoldiTesseramenti(membershipBalance);

        String[] s1 = saldoGiorLabel.getText().split(" ");
        String[] s2 = prelievoLabel.getText().split(" ");

        BigDecimal totDay = new BigDecimal(s1[0]).subtract(membershipBalance);
        if (totDay.intValue() < 0)
            riepilogo.setEntrateGiornata(new BigDecimal(0));
        else
            riepilogo.setEntrateGiornata(totDay);

        riepilogo.setPrelievi(new BigDecimal(s2[0]));

        if (row == 0)
        {
            /*Insert*/
            modelRiepilogoGiornDB.insertDayBalance(riepilogo);
        }
        else
        {
            /*Update*/
            modelRiepilogoGiornDB.updateDayBalance(riepilogo);
        }

        confirm.displayInformation("Esporta saldo giornaliero eseguito!");

    }

    private void handlePrelievoJFXButton(ActionEvent actionEvent) {

        ControllerAlert alert = new ControllerAlert();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("");
        dialog.setTitle("Prelievo");
        dialog.setContentText("Cifra prelevata:");
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        Optional<String> s = dialog.showAndWait();
        BigDecimal withdrawal = new BigDecimal(0.0);

        if (s.isPresent()) {
            try {
                int i = Integer.parseInt(s.get());
                withdrawal = new BigDecimal(i);
            } catch (NumberFormatException e) {
                alert.displayAlert("Controlla la cifra inserita!");
            }
        }

        if(!withdrawal.equals(new BigDecimal(0.0)))
        {
            ModelPrelievo modelPrelievoDB = new ModelDBPrelievo();
            modelPrelievoDB.insertWithdrawal(withdrawal, managers.get(0));
            setSideScreen();
        }


    }

    private void handleRiepMensileJFXButton(ActionEvent actionEvent) {
        ArrayList<RiepilogoGiornaliero> dailySummaries = new ArrayList<>();
        ModelRiepilogoGiorn modelRiepilogoGiornDB = new ModelDBRiepilogoGiorn();
        dailySummaries.addAll(modelRiepilogoGiornDB.getDailySummaries());

        StageManager monthSummaries = new StageManager();
        monthSummaries.setStageMonthSummary((Stage) riepMensileJFXButton.getScene().getWindow(), managers, dailySummaries);
    }

    private void handleRiepGiornataJFXButton(ActionEvent actionEvent) {
        /*fetch all information about the day*/
        ArrayList<Entrata> records = new ArrayList<>();
        ModelEntrata modelEntrataDB = new ModelDBEntrata();
        records.addAll(modelEntrataDB.getAllRecord());
        for (Entrata e: records) {
            if(e.getTipopagamento() == null || e.getTipopagamento().equalsIgnoreCase(""))
                e.setTipopagamento("Abbonamento");
            if(e.getImporto() == null)
                e.setImporto(new BigDecimal(0.00));
            if (e.getTesseramento() == null)
                e.setTesseramento("NO");
            if(e.getScarpette() == null)
                e.setScarpette("NO");
        }


        StageManager daySummary = new StageManager();
        daySummary.setStageDaySummary((Stage) riepGiornataJFXButton.getScene().getWindow(), managers, records);
    }

    private void handleIngressoProvaJFXButton(ActionEvent actionEvent) {
        StageManager tryEnter = new StageManager();
        tryEnter.setStageTryEnter((Stage) ingressoProvaJFXButton.getScene().getWindow(), managers);
    }

    private void handleTesseramentoJFXButton(ActionEvent actionEvent) {
    }



}

