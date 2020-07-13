package Controller;

import Data.Manager;
import Data.Person;
import Utils.CodiceFiscale;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ControllerMembership {

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
    private Label PersEntrateLabel;

    @FXML
    private Label prelievoLabel;

    @FXML
    private ImageView homePageImageView;

    @FXML
    private JFXTextField nomeJFXTextField;

    @FXML
    private JFXTextField cognomeJFXTextField;

    @FXML
    private JFXTextField mailJFXTextField;

    @FXML
    private JFXTextField dataNascitaJFXTextField;

    @FXML
    private JFXTextField luogoNascitaJFXTextField;

    @FXML
    private ComboBox<String> sessoComboBox;
    private ObservableList<String> sex = FXCollections.observableArrayList();

    @FXML
    private JFXTextField scadenzaCertJFXTextField;

    @FXML
    private JFXTextField CFJFXTextField;

    @FXML
    private JFXTextField pswTempJFXTextField;

    @FXML
    private JFXButton genCFJFXButton;

    @FXML
    private JFXButton genPswJFXButton;

    @FXML
    private CheckBox stranieroCheckBox;

    @FXML
    private Label dateLabel;

    @FXML
    private JFXButton registraSocioJFXButton;

    private ArrayList<Manager> managers;

    private final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", Pattern.CASE_INSENSITIVE);

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
        genPswJFXButton.setOnAction(this::handlegenPswFXButton);
        stranieroCheckBox.setOnAction(this::handleStranieroCheckBox);
        registraSocioJFXButton.setOnAction(actionEvent -> {
            try {
                hadleRegistraSocio(actionEvent);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

    }



    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void setSideScreen()
    {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }

    private void populateComboBox() {
        sex.addAll("M", "F");
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

    private void handlegenPswFXButton(ActionEvent actionEvent) {
        String password = new Random().ints(10, 33, 122).mapToObj(i -> String.valueOf((char)i)).collect(Collectors.joining());
        pswTempJFXTextField.setText(password);
        pswTempJFXTextField.setDisable(true);
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

    private void hadleRegistraSocio(ActionEvent actionEvent) throws ParseException {
        ControllerAlert alert = new ControllerAlert();
        ControllerPersonInformation checkFields = new ControllerPersonInformation();
        String error = checkFields.fieldsAreValid(nomeJFXTextField, cognomeJFXTextField, dataNascitaJFXTextField, luogoNascitaJFXTextField, sessoComboBox.getValue());
        int year = Calendar.getInstance().get(Calendar.YEAR);

        /*check if mail is valid*/
        if(!isMailValid(mailJFXTextField.getText()))
            error += "-La mail inserita non è nel formato corretto!\n";

        if(scadenzaCertJFXTextField.getText().trim().isEmpty())
        {
            error += "-La data di nascita deve essere inserita!\n";
        }
        else
        {
            String[] split = scadenzaCertJFXTextField.getText().split("/");
            if(split.length < 3)
            {
                error += "-La data di nascita deve avere formato DD/MM/YYYY!\n";
            }
            else
            {
                if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                    error += "-Giorno nascita non valido!\n";
                if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                    error += "-Mese nascita non valido!\n";
                if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) > year ||(Integer.parseInt(split[2]) < 1900)))
                    error += "-Anno nascita non valido (YYYY, 4 cifre necessarie)!\n";

                /*check if medicate certificate is valid*/
                Date date = new SimpleDateFormat("dd/mm/yyyy").parse(scadenzaCertJFXTextField.getText());
                Date currentDate = new SimpleDateFormat("dd/mm/yyyy").parse(String.valueOf(LocalDate.now()));

                if (date.before(currentDate))
                    error += "-Data scadenza certificato non valida!\n";
            }

        }

        if(error.isEmpty()){
            /*check if client is already a member*/


        }
        else
        {
            alert.displayAlert(error);
        }
    }

    public boolean isMailValid(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private boolean isNumerical(String s) {
        return s.matches("[+]?([0-9]*[.])?[0-9]+");
    }
}
