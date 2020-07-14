package Controller;


import Data.Manager;
import Data.Pagamento;
import Data.Person;
import Model.*;
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
import java.sql.Timestamp;
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

    private String ingresso;

    private String scarpette;


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
        contantiJFXCheckBox.setOnAction(this::handleContantiJFXCheckBox);
        cartaJFXCheckBox.setOnAction(this::handleCartaJFXCheckBox);
        pagamentoJFXButton.setOnAction(this::handlePagamentoJFXButton);

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


    private void handleTenEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento 10 ingressi", U18Label.getText()).intValueExact() + " €");
        dieciIngrJFXButton.setDisable(true);

        ingresso = "Abbonamento 10 ingressi";

    }

    private void handleMonthEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento mensile", U18Label.getText()).intValueExact() + " €");

        ingresso = "Abbonamento mensile";

    }

    private void handle3MonthEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento trimestrale", U18Label.getText()).intValueExact() + " €");
        trimeJFXButton.setDisable(true);

        ingresso = "Abbonamento trimestrale";
    }

    private void handleClassEntrace(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Tessera corso", U18Label.getText()).intValueExact() + " €");

        corsoJFXButton.setDisable(true);

        ingresso = "Tessera corso";
    }

    private void handleShoes(ActionEvent actionEvent) {
        if(!totDaPagareLabel.getText().isEmpty())
        {
            String[] s = totDaPagareLabel.getText().split(" ");
            int partial = Integer.parseInt(s[0]);
            partial += 2;
            totDaPagareLabel.setText(partial + " €");
        }
        else
        {
            totDaPagareLabel.setText(2 + " €");
        }
        scarpetteJFXButton.setDisable(true);
        scarpette = "SI";
    }

    private void handleSingleEntrace(ActionEvent actionEvent) {
        dieciIngrJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Ingresso singolo", U18Label.getText()).intValueExact() + " €");

        String[] s = dateLabel.getText().split(" ");
        if(s[0].equalsIgnoreCase("sabato") || s[0].equalsIgnoreCase("domenica"))
        {

            String[] tot = totDaPagareLabel.getText().split(" ");
            int partial = Integer.parseInt(tot[0]);
            partial += 2;
            totDaPagareLabel.setText(partial + " €");
        }
        ingressoSingoloJFXButton.setDisable(true);
        ingresso = "Ingresso singolo";

    }

    private void handleResetButton(ActionEvent actionEvent) {
        ingressoSingoloJFXButton.setDisable(false);
        dieciIngrJFXButton.setDisable(false);
        mensileJFXButton.setDisable(false);
        trimeJFXButton.setDisable(false);
        corsoJFXButton.setDisable(false);
        scarpetteJFXButton.setDisable(false);
        totDaPagareLabel.setText("");
        tesseramentoJFXCheckBox.setSelected(false);
        ingresso = null;
        scarpette = null;

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
            tesseramentoJFXCheckBox.setSelected(false);
        }
        else
        {
            String[] partial = totDaPagareLabel.getText().split(" ");
            if (tesseramentoJFXCheckBox.isSelected())
            {
                if(Integer.parseInt(partial[0]) > 10)
                    totDaPagareLabel.setText(Integer.parseInt(partial[0]) + 20 + " €");
                else if(U18Label.getText().equalsIgnoreCase("si") && Integer.parseInt(partial[0]) - 2 == 5)
                    totDaPagareLabel.setText(22 + " €");
                else if(U18Label.getText().equalsIgnoreCase("no") && Integer.parseInt(partial[0]) - 2 == 7)
                    totDaPagareLabel.setText(22 + " €");
                else
                    totDaPagareLabel.setText(20 + " €");
            }
            else
            {
                totDaPagareLabel.setText(Integer.parseInt(partial[0]) - 20 + " €");
            }
        }
    }

    private void handleContantiJFXCheckBox(ActionEvent actionEvent) {
        if(contantiJFXCheckBox.isSelected())
        {
            cartaJFXCheckBox.setSelected(false);
        }
    }

    private void handleCartaJFXCheckBox(ActionEvent actionEvent) {
        if( cartaJFXCheckBox.isSelected())
        {
            contantiJFXCheckBox.setSelected(false);
        }
    }

    private void handlePagamentoJFXButton(ActionEvent actionEvent) {

        ControllerAlert confirm = new ControllerAlert();
        /*now insert new record for payment into DB*/
        Pagamento payment = new Pagamento();
        payment.setCfPerson(person.getCf());
        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
        payment.setPaymentInstant(currentTimestamp);

        String[] s = totDaPagareLabel.getText().split(" ");
        payment.setAmount(new BigDecimal(Integer.parseInt(s[0])));
        if(contantiJFXCheckBox.isSelected())
            payment.setPaymentType("Contante");
        else
            payment.setPaymentType("Carta");

        payment.setEnterType(ingresso);
        if (tesseramentoJFXCheckBox.isSelected())
        {
            payment.setMembership("SI");
            payment.setPriceMembership(new BigDecimal(20.00));
        }
        else
            payment.setMembership("NO");

        if(scarpette.equalsIgnoreCase("si"))
        {
            payment.setShoes("SI");
            payment.setPriceShoes(new BigDecimal(2.00));
        }
        else
            payment.setShoes("NO");


        /*Add payment into DB*/
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        modelPagamentoDB.insertNewPayment(payment);

        confirm.displayInformation("Pagamento regitrato!");
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);

    }
}
