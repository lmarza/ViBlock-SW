package Controller;

import Data.Manager;
import Data.Person;
import Utils.CodiceFiscale;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import java.util.ArrayList;

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

        //TODO check all fields before strarting calculate CF
        ControllerAlert alert = new ControllerAlert();
        Person person = new Person();
        person.setSurname(cognomeJFXTextField.getText());
        person.setName(nomeJFXTextField.getText());
        person.setSex(sessoComboBox.getValue());
        person.setBornCity(luogoNascitaJFXTextField.getText().toUpperCase());
        String[] birthday = dataNascitaJFXTextField.getText().split("/");
        if(birthday.length < 3)
            alert.displayAlert("Controlla la data di nascita!");
        else
        {
            person.setDay(birthday[0]);
            person.setMonth(birthday[1]);
            person.setYear(birthday[2]);
        }

        CFJFXTextField.setText(new CodiceFiscale(person).getCode());

    }

    private void populateComboBox() {
        sex.addAll("M", "F");
    }

    private void handleIngressoProvaJFXButton(ActionEvent actionEvent) {
        totDaPagareLabel.setText("10 €");
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
        if(stranieroCheckBox.isSelected())
            genCFJFXButton.setDisable(true);
        if(!stranieroCheckBox.isSelected())
            genCFJFXButton.setDisable(false);
    }

    private void handlePagamentoJFXButton(ActionEvent actionEvent) {

    }



}
