package Controller;


import Data.Manager;
import Data.Person;
import Model.ModelCertificatoMedico;
import Model.ModelDBCertificatoMedico;
import Model.ModelDBPagamento;
import Model.ModelPagamento;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

public class ControllerEntrance {

    @FXML
    private ImageView homePageImageView;

    @FXML
    private JFXButton dieciIngrJFXButton;

    @FXML
    private JFXButton ingressoSingoloJFXButton;

    @FXML
    private JFXButton mensileJFXButton;

    @FXML
    private JFXButton trimeJFXButton;

    @FXML
    private JFXButton corsoJFXButton;

    @FXML
    private Label totDaPagareLabel;

    @FXML
    private JFXCheckBox contantiJFXCheckBox;

    @FXML
    private JFXCheckBox cartaJFXCheckBox;

    @FXML
    private JFXButton pagamentoJFXButton;

    @FXML
    private Label nomeLabel;

    @FXML
    private Label cognomeLabel;

    @FXML
    private Label CFLabel;

    @FXML
    private Label mailLabel;

    @FXML
    private Label scadCertificatoLabel;

    @FXML
    private Label U18Label;

    @FXML
    private JFXButton scarpetteJFXButton;

    @FXML
    private JFXButton resetJFXButton;

    @FXML
    private JFXCheckBox tesseramentoJFXCheckBox;

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

    private ArrayList<Manager> managers;

    private Person person;


    @FXML
    private void initialize()
    {
        //populateClientInformation(person);
        ingressoSingoloJFXButton.setOnAction(this::handleSingleEntrace);
        dieciIngrJFXButton.setOnAction(this::handleTenEntrace);
        mensileJFXButton.setOnAction(this::handleMonthEntrace);
        trimeJFXButton.setOnAction(this::handle3MonthEntrace);
        corsoJFXButton.setOnAction(this::handleClassEntrace);
        scarpetteJFXButton.setOnAction(this::handleShoes);
        resetJFXButton.setOnAction(this::handleResetButton);
        homePageImageView.setOnMouseClicked(this::handleHomePageImageView);
        tesseramentoJFXCheckBox.setOnAction(this::handleMembershipJFXCheckBox);

    }


    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setPerson(Person person){
        this.person = person;

        nomeLabel.setText(person.getName());
        cognomeLabel.setText(person.getSurname());
        CFLabel.setText(person.getCf());
        mailLabel.setText(person.getMail());

        ModelCertificatoMedico modelCertificatoMedicoDB = new ModelDBCertificatoMedico();
        scadCertificatoLabel.setText(modelCertificatoMedicoDB.getExpiryDateMedicalCertificate(person.getMedicalCertificate()));

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] birthday = person.getBirthday().split("/");
        if(year - Integer.parseInt(birthday[2]) < 18)
            U18Label.setText("SI");
        else
            U18Label.setText("NO");
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }

    private void populateClientInformation(Person person) {
        nomeLabel.setText(person.getName());
        cognomeLabel.setText(person.getSurname());
        CFLabel.setText(person.getCf());
        mailLabel.setText(person.getMail());

        ModelCertificatoMedico modelCertificatoMedicoDB = new ModelDBCertificatoMedico();
        scadCertificatoLabel.setText(modelCertificatoMedicoDB.getExpiryDateMedicalCertificate(person.getMedicalCertificate()));

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] birthday = person.getBirthday().split("-");
        if(year - Integer.parseInt(birthday[0]) < 18)
            U18Label.setText("SI");
        else
            U18Label.setText("NO");
    }

    private void handleTenEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento 10 ingressi", U18Label.getText()).toString() + " €");

    }

    private void handleMonthEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento mensile", U18Label.getText()).toString() + " €");

    }

    private void handle3MonthEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento trimestrale", U18Label.getText()).toString() + " €");
    }

    private void handleClassEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Tessera corso", U18Label.getText()).toString() + " €");
    }

    private void handleShoes(ActionEvent actionEvent) {
        if(!totDaPagareLabel.getText().isEmpty())
        {
            String[] s = totDaPagareLabel.getText().split(" ");
            BigDecimal partial = new BigDecimal(Integer.parseInt(s[0]));
            BigDecimal add = partial.add(new BigDecimal(2.00));
            totDaPagareLabel.setText(add.toString() + " €");
        }
        else
        {
            totDaPagareLabel.setText(new BigDecimal(2.00).toString() + " €");
        }

    }

    private void handleSingleEntrace(ActionEvent actionEvent) {
        dieciIngrJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        BigDecimal partial = modelPagamentoDB.getPriceEntrance("Ingresso singolo", U18Label.getText());
        String[] s = dateLabel.getText().split(" ");
        if(s[0].equalsIgnoreCase("sabato") || s[0].equalsIgnoreCase("domenica"))
        {
            BigDecimal add = partial.add(new BigDecimal(1.00));
            totDaPagareLabel.setText(add.toString() + " €");
        }
        else
            totDaPagareLabel.setText(partial.toString() + " €");
    }

    private void handleResetButton(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(false);
        dieciIngrJFXButton.setDisable(false);
        mensileJFXButton.setDisable(false);
        trimeJFXButton.setDisable(false);
        corsoJFXButton.setDisable(false);
        scarpetteJFXButton.setDisable(false);
        totDaPagareLabel.setText("");

    }

    private void handleHomePageImageView(MouseEvent mouseEvent) {
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);
    }

    private void handleMembershipJFXCheckBox(ActionEvent actionEvent) {
        ControllerAlert alert = new ControllerAlert();
        if (totDaPagareLabel.getText().isEmpty())
        {
            alert.displayAlert("seleziona prima un tipo di ingresso!\n(Se solo tesseramento scegli ingresso singolo)");
        }
        else
        {
            String[] partial = totDaPagareLabel.getText().split(" ");
            if (tesseramentoJFXCheckBox.isSelected())
            {
                if(Integer.parseInt(partial[0]) > 10)
                    totDaPagareLabel.setText(String.valueOf(Integer.parseInt(partial[0]) + 20) + " €");
                else if(Integer.parseInt(partial[0]) - 2 == 5 || Integer.parseInt(partial[0]) - 2 == 7)
                    totDaPagareLabel.setText(String.valueOf(22) + " €");
            }
        }

        
    }



}
