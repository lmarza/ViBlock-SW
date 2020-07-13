package Controller;

import Data.Manager;
import Data.Pagamento;
import Data.Person;
import Model.ModelCliente;
import Model.ModelDBCliente;
import Model.ModelDBPagamento;
import Model.ModelPagamento;
import Utils.CodiceFiscale;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ControllerTryEnter {

    @FXML
    private ImageView homePageImageView;

    @FXML
    private JFXButton ingressoProvaJFXButton;

    @FXML
    private Label totDaPagareLabel;

    @FXML
    private JFXCheckBox contantiJFXCheckBox;

    @FXML
    private JFXCheckBox cartaJFXCheckBox;

    @FXML
    private JFXButton pagamentoJFXButton;

    @FXML
    private JFXTextField nomeJFXTextField;

    @FXML
    private JFXTextField cognomeJFXTextField;

    @FXML
    private JFXTextField dataNascitaJFXTextField;

    @FXML
    private JFXTextField luogoNascitaJFXTextField;

    @FXML
    private ComboBox<String> sessoComboBox;
    private ObservableList<String> sex = FXCollections.observableArrayList();

    @FXML
    private JFXTextField CFJFXTextField;

    @FXML
    private JFXButton genCFJFXButton;

    @FXML
    private CheckBox stranieroCheckBox;

    @FXML
    private JFXCheckBox scarpetteJFXCheckBox;

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

    @FXML
    private void initialize()
    {
        populateComboBox();
        sessoComboBox.setItems(sex);
        homePageImageView.setOnMouseClicked(this::handleHomePageImageView);
        genCFJFXButton.setOnAction(actionEvent -> {
            try {
                handlegenCFJFXButton(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        ingressoProvaJFXButton.setOnAction(this::handleIngressoProvaJFXButton);
        scarpetteJFXCheckBox.setOnAction(this::handleScarpetteJFXCheckBox);
        contantiJFXCheckBox.setOnAction(this::handleContantiJFXCheckBox);
        cartaJFXCheckBox.setOnAction(this::handleCartaJFXCheckBox);
        pagamentoJFXButton.setOnAction(this::handlePagamentoJFXButton);
        stranieroCheckBox.setOnAction(this::handleStranieroCheckBox);
    }


    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }

    private void handleHomePageImageView(MouseEvent mouseEvent) {
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);
    }

    private void handlegenCFJFXButton(ActionEvent actionEvent) throws IOException {

        ControllerAlert alert = new ControllerAlert();
        ControllerPersonInformation controllerPersonInformation = new ControllerPersonInformation();
        String error = controllerPersonInformation.fieldsAreValid(nomeJFXTextField,cognomeJFXTextField, dataNascitaJFXTextField, luogoNascitaJFXTextField, sessoComboBox.getValue());

        if(error.isEmpty())
        {
            Person person = new Person();
            person.setSurname(cognomeJFXTextField.getText());
            person.setName(nomeJFXTextField.getText());
            person.setSex(sessoComboBox.getValue());
            person.setBornCity(luogoNascitaJFXTextField.getText().toUpperCase());
            String[] birthday = dataNascitaJFXTextField.getText().split("/");
            person.setDay(birthday[0]);
            person.setMonth(birthday[1]);
            person.setYear(birthday[2]);
            CFJFXTextField.setText(new CodiceFiscale(person).getCode());
        }
        else
        {
            alert.displayAlert(error);
        }
    }


    private void populateComboBox() {
        sex.addAll("M", "F");
    }

    private void handleIngressoProvaJFXButton(ActionEvent actionEvent) {
        totDaPagareLabel.setText("10 €");
        ControllerAlert alert = new ControllerAlert();
        if(!nomeJFXTextField.getText().isEmpty() && !cognomeJFXTextField.getText().isEmpty() && !dataNascitaJFXTextField.getText().isEmpty() &&
                sessoComboBox.getValue() != null && !CFJFXTextField.getText().isEmpty())
            pagamentoJFXButton.setDisable(false);
        else
        {
            alert.displayAlert("Riempire tutti i campi prima di procedere!");
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

    private void handleScarpetteJFXCheckBox(ActionEvent actionEvent) {
        if(scarpetteJFXCheckBox.isSelected())
            totDaPagareLabel.setText("12 €");

        if(!scarpetteJFXCheckBox.isSelected())
            totDaPagareLabel.setText("10 €");
    }

    private void handleStranieroCheckBox(ActionEvent actionEvent) {
        ControllerAlert alert = new ControllerAlert();
        if(stranieroCheckBox.isSelected())
        {
            genCFJFXButton.setDisable(true);
            alert.displayInformation("Al posto del codice fiscale inserire: NomeCognomeAnnoNascita con l'anno di nascita in questo formato YYYY");
        }

        if(!stranieroCheckBox.isSelected())
            genCFJFXButton.setDisable(false);
    }

    private void handlePagamentoJFXButton(ActionEvent actionEvent) {
        ControllerAlert confirm = new ControllerAlert();

        //TODO CHECK IF CLIENT ALREADY EXISTS

        /*first create new client and insert into DB*/
        Person person = new Person();
        person.setSurname(cognomeJFXTextField.getText());
        person.setName(nomeJFXTextField.getText());
        person.setSex(sessoComboBox.getValue());
        person.setBornCity(luogoNascitaJFXTextField.getText());
        person.setBirthday(dataNascitaJFXTextField.getText());
        person.setSex(sessoComboBox.getValue());
        person.setCf(CFJFXTextField.getText());
        person.setClientType("P");
        Date d = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        String finaleDate = dateFormat.format(d);
        person.setDataTry(finaleDate);


        ModelCliente modelClientDB = new ModelDBCliente();
        modelClientDB.insertNewClient(person);

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

        payment.setEnterType("Ingresso prova");
        payment.setMembership("NO");
        if(scarpetteJFXCheckBox.isSelected())
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
