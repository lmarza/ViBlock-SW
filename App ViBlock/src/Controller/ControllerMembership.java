package Controller;

import Data.Manager;
import Data.MedicalCertificate;
import Data.Person;
import Model.ModelCertificatoMedico;
import Model.ModelCliente;
import Model.ModelDBCertificatoMedico;
import Model.ModelDBCliente;
import Utils.CodiceFiscale;
import Utils.EncryptPassword;
import Utils.Mail;
import Utils.StageManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
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
    private JFXTextField rilascioCertJFXTextField;

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
            } catch (MessagingException | FileNotFoundException | InvalidKeySpecException | NoSuchAlgorithmException e) {
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
        genPswJFXButton.setDisable(true);
    }

    private void handleStranieroCheckBox(ActionEvent actionEvent) {
        if(stranieroCheckBox.isSelected())
        {
            genCFJFXButton.setDisable(true);
            String[] birthday = dataNascitaJFXTextField.getText().split("/");
            CFJFXTextField.setText(nomeJFXTextField.getText()+cognomeJFXTextField.getText()+birthday[2]);
        }

        if(!stranieroCheckBox.isSelected())
            genCFJFXButton.setDisable(false);
    }

    private void hadleRegistraSocio(ActionEvent actionEvent) throws MessagingException, FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        ControllerAlert alert = new ControllerAlert();
        ControllerPersonInformation checkFields = new ControllerPersonInformation();
        String error = checkFields.fieldsAreValid(nomeJFXTextField, cognomeJFXTextField, dataNascitaJFXTextField, luogoNascitaJFXTextField, sessoComboBox.getValue());
        int year = Calendar.getInstance().get(Calendar.YEAR);

        /*check if mail is valid*/
        if(!isMailValid(mailJFXTextField.getText()))
            error += "-La mail inserita non è nel formato corretto!\n";

        if(rilascioCertJFXTextField.getText().trim().isEmpty())
        {
            error += "-La data di rilascio certificato deve essere inserita!\n";
        }
        else
        {
            String[] split = rilascioCertJFXTextField.getText().split("/");
            if(split.length < 3)
            {
                error += "-La data di rilascio deve avere formato DD/MM/YYYY!\n";
            }
            else
            {
                if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                    error += "-Giorno di rilascio certificato non valido!\n";
                if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                    error += "-Mese di rilascio certificato non valido!\n";
                if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) > year ||(Integer.parseInt(split[2]) < 1900)))
                    error += "-Anno di rilascio certificato non valido (YYYY, 4 cifre necessarie)!\n";
            }
        }

        if(scadenzaCertJFXTextField.getText().trim().isEmpty())
        {
            error += "-La data di scadenza certificato deve essere inserita!\n";
        }
        else
        {
            String[] split = scadenzaCertJFXTextField.getText().split("/");
            if(split.length < 3)
            {
                error += "-La data di scadenza deve avere formato DD/MM/YYYY!\n";
            }
            else
            {
                if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                    error += "-Giorno scadenza certificato non valido!\n";
                if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                    error += "-Mese scadenza certificato non valido!\n";
                if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) < year ||(Integer.parseInt(split[2]) < 1900)))
                    error += "-Anno scadenza certificato non valido (YYYY, 4 cifre necessarie)!\n";
            }
        }

        if(error.isEmpty())
        {
            /*check if client is already a member*/
            ModelCliente modelClienteDB = new ModelDBCliente();
            int exists = modelClienteDB.checkIfClientAlreadyExists(CFJFXTextField.getText());
            insertOrUpdateClient(exists, modelClienteDB);
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

    private void insertOrUpdateClient(int exists, ModelCliente modelClienteDB) throws FileNotFoundException, MessagingException, InvalidKeySpecException, NoSuchAlgorithmException {
        /*first send mail with temp psw to user's mail*/
        ControllerAlert alert = new ControllerAlert();

        /*encoding psw*/
        EncryptPassword encryptPassword = new EncryptPassword();
        String finalPsw = encryptPassword.generateHashPsw(pswTempJFXTextField.getText());

        Person person = new Person();
        person.setName(nomeJFXTextField.getText());
        person.setSurname(cognomeJFXTextField.getText());
        person.setSex(sessoComboBox.getValue());
        person.setBirthday(dataNascitaJFXTextField.getText());
        person.setMail(mailJFXTextField.getText());
        person.setPsw(finalPsw);
        person.setClientType("R");
        person.setBornCity(luogoNascitaJFXTextField.getText());
        person.setCf(CFJFXTextField.getText());

        Date d = new Date();
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.ITALY);
        String finaleDate = dateFormat.format(d);
        person.setDataMembership(finaleDate);

        if(exists == 0)
        {
            /*add new medical certificate into DB*/
            MedicalCertificate medicalCertificate = new MedicalCertificate();
            medicalCertificate.setDataRilascio(rilascioCertJFXTextField.getText());
            medicalCertificate.setDataScadenza(scadenzaCertJFXTextField.getText());
            ModelCertificatoMedico modelCertificatoMedico = new ModelDBCertificatoMedico();
            modelCertificatoMedico.insertNewMedicalCertificate(medicalCertificate);

            /*get last medical certificate id and create a new Client*/
            int medicalCertificateId = modelCertificatoMedico.getLastMedicalCertificateInsert();

            person.setMedicalCertificate(String.valueOf(medicalCertificateId));

            //Mail.sendMail(mailJFXTextField.getText(), nomeJFXTextField.getText(), cognomeJFXTextField.getText(), pswTempJFXTextField.getText());
            modelClienteDB.insertNewClient(person);
            alert.displayInformation("Il cliente è stato registrato con successo!\n");
        }
        else
        {
            alert.displayInformation("Il cliente è già presente nel DB... verifico che non sia già iscritto per quest'anno!\n");
            Person oldClient = modelClienteDB.getClient(person.getCf());

            String[] membership = oldClient.getDataMembership().split("-");
            int year = Calendar.getInstance().get(Calendar.YEAR);

            /*if client is member for the new year-->error */
            if(Integer.parseInt(membership[1]) > 6 && Integer.parseInt(membership[0]) >= year)
            {
                alert.displayAlert("Il cliente è già tesserato per quest'anno!");
                StageManager mainPage = new StageManager();
                mainPage.setStageMainPage((Stage) registraSocioJFXButton.getScene().getWindow(), managers);
            }
            else
            {
                alert.displayInformation("Aggiorno le sue informazioni!\n");

                /*add new medical certificate into DB*/
                MedicalCertificate medicalCertificate = new MedicalCertificate();
                medicalCertificate.setDataRilascio(rilascioCertJFXTextField.getText());
                medicalCertificate.setDataScadenza(scadenzaCertJFXTextField.getText());
                ModelCertificatoMedico modelCertificatoMedico = new ModelDBCertificatoMedico();
                modelCertificatoMedico.insertNewMedicalCertificate(medicalCertificate);

                /*get last medical certificate id and create a new Client*/
                int medicalCertificateId = modelCertificatoMedico.getLastMedicalCertificateInsert();

                person.setMedicalCertificate(String.valueOf(medicalCertificateId));

                Mail.sendMail(mailJFXTextField.getText(), nomeJFXTextField.getText(), cognomeJFXTextField.getText(), pswTempJFXTextField.getText());
                if(oldClient.getClientType().equalsIgnoreCase("P"))
                    person.setClientType("RP");
                modelClienteDB.updateClientInformation(person);
                alert.displayInformation("Le informazioni del cliente sono state aggiornate con successo!\n");
            }
        }
        /*Entrance page*/
        StageManager entrancePage = new StageManager();
        entrancePage.setStageEntrace((Stage) registraSocioJFXButton.getScene().getWindow(), managers, person);
    }
}
