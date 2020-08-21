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

    private Person person;

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
            person = createPerson();
            CFJFXTextField.setText(new CodiceFiscale(person).getCode());
            person.setCf(CFJFXTextField.getText());
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
            CFJFXTextField.setText(person.getName()+person.getSurname()+birthday[2]);
        }

        if(!stranieroCheckBox.isSelected())
            genCFJFXButton.setDisable(false);
    }

    private void hadleRegistraSocio(ActionEvent actionEvent) throws MessagingException, FileNotFoundException, InvalidKeySpecException, NoSuchAlgorithmException {
        ControllerAlert alert = new ControllerAlert();
        ControllerPersonInformation checkFields = new ControllerPersonInformation();
        String error = checkFields.fieldsAreValid(nomeJFXTextField, cognomeJFXTextField, dataNascitaJFXTextField, luogoNascitaJFXTextField, sessoComboBox.getValue());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String[] rilascio = rilascioCertJFXTextField.getText().split("/");
        String[] scadenza = scadenzaCertJFXTextField.getText().split("/");

        /*check if mail is valid*/
        if(!isMailValid(mailJFXTextField.getText()))
            error += "-La mail inserita non è nel formato corretto!\n";

        if(rilascioCertJFXTextField.getText().trim().isEmpty())
        {
            error += "-La data di rilascio certificato deve essere inserita!\n";
        }
        else
        {

            if(rilascio.length < 3)
            {
                error += "-La data di rilascio deve avere formato DD/MM/YYYY!\n";
            }
            else
            {
                if(!isNumerical(rilascio[0]) || rilascio[0].length() > 2 || (Integer.parseInt(rilascio[0]) > 31 ||(Integer.parseInt(rilascio[0]) < 1)))
                    error += "-Giorno di rilascio certificato non valido!\n";
                if(!isNumerical(rilascio[1]) || rilascio[1].length() > 2 || (Integer.parseInt(rilascio[1]) < 1 ||(Integer.parseInt(rilascio[1]) > 12)))
                    error += "-Mese di rilascio certificato non valido!\n";
                if(!isNumerical(rilascio[2]) || rilascio[2].length() != 4 || (Integer.parseInt(rilascio[2]) > year || (Integer.parseInt(rilascio[2]) < 1900)))
                    error += "-Anno di rilascio certificato non valido (YYYY, 4 cifre necessarie)!\n";
            }
        }

        if(scadenzaCertJFXTextField.getText().trim().isEmpty())
        {
            error += "-La data di scadenza certificato deve essere inserita!\n";
        }
        else
        {
            if(scadenza.length < 3)
            {
                error += "-La data di scadenza deve avere formato DD/MM/YYYY!\n";
            }
            else
            {
                if(!isNumerical(scadenza[0]) || scadenza[0].length() > 2 || (Integer.parseInt(scadenza[0]) > 31 ||(Integer.parseInt(scadenza[0]) < 1)))
                    error += "-Giorno scadenza certificato non valido!\n";
                if(!isNumerical(scadenza[1]) || scadenza[1].length() > 2 || (Integer.parseInt(scadenza[1]) < 1 ||(Integer.parseInt(scadenza[1]) > 12)))
                    error += "-Mese scadenza certificato non valido!\n";
                if(!isNumerical(scadenza[2]) || scadenza[2].length() != 4 || (Integer.parseInt(scadenza[2]) < year ||(Integer.parseInt(scadenza[2]) < 1900)))
                    error += "-Anno scadenza certificato non valido (YYYY, 4 cifre necessarie)!\n";
            }
        }

        if(Integer.parseInt(rilascio[2]) >= Integer.parseInt(scadenza[2]))
            error += "-Data di rilascio o scadenza errata!Controlla gli anni\n";

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

        person.setMail(mailJFXTextField.getText());
        person.setPsw(finalPsw);
        person.setClientType("R");

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

            if(oldClient.getDataMembership() == null)
            {
                alert.displayInformation("Il cliente non è già tesserato per quest'anno,aggiorno le sue informazioni!\n");

                /*add new medical certificate into DB*/
                MedicalCertificate medicalCertificate = new MedicalCertificate();
                medicalCertificate.setDataRilascio(rilascioCertJFXTextField.getText());
                medicalCertificate.setDataScadenza(scadenzaCertJFXTextField.getText());
                ModelCertificatoMedico modelCertificatoMedico = new ModelDBCertificatoMedico();
                modelCertificatoMedico.insertNewMedicalCertificate(medicalCertificate);

                /*get last medical certificate id and create a new Client*/
                int medicalCertificateId = modelCertificatoMedico.getLastMedicalCertificateInsert();

                person.setMedicalCertificate(String.valueOf(medicalCertificateId));

                //TODO: enable send email
                Mail.sendMail(mailJFXTextField.getText(), nomeJFXTextField.getText(), cognomeJFXTextField.getText(), pswTempJFXTextField.getText());
                if(oldClient.getClientType().equalsIgnoreCase("P"))
                    person.setClientType("RP");
                modelClienteDB.updateClientInformation(person);
                alert.displayInformation("Le informazioni del cliente sono state aggiornate con successo!\n");
            }
            else
            {
                String[] membership = oldClient.getDataMembership().split("-");
                int year = Calendar.getInstance().get(Calendar.YEAR);

                /*if client is member for the new year-->error */
                if(Integer.parseInt(membership[1]) > 6 && Integer.parseInt(membership[0]) >= year)
                {
                    alert.displayAlert("Il cliente è già tesserato per quest'anno!");
                    StageManager mainPage = new StageManager();
                    mainPage.setStageMainPage((Stage) registraSocioJFXButton.getScene().getWindow(), managers);
                }
            }
        }
        /*Entrance page*/
        StageManager entrancePage = new StageManager();
        person.setBirthday(person.getBirthday().replaceAll("/", "-"));
        entrancePage.setStageEntrace((Stage) registraSocioJFXButton.getScene().getWindow(), managers, person);
    }

    private Person createPerson() {
        Person p = new Person();
        String s = cognomeJFXTextField.getText().trim();
        String[] s1 = s.split(" ");
        String surname;
        if(s1.length < 2)
            surname = s.substring(0, 1).toUpperCase() + s.substring(1);
        else
            surname = s1[0].substring(0,1).toUpperCase() + s1[0].substring(1) + s1[1].substring(0,1).toUpperCase() + s1[1].substring(1);
        p.setSurname(surname);

        String n = nomeJFXTextField.getText().trim();
        String[] n1 = n.split(" ");
        String name;
        if(n1.length < 2)
            name = n.substring(0, 1).toUpperCase() + n.substring(1);
        else
            name = n1[0].substring(0,1).toUpperCase() + n1[0].substring(1) + n1[1].substring(0,1).toUpperCase() + n1[1].substring(1);
        p.setName(name);

        p.setSex(sessoComboBox.getValue());
        String b = luogoNascitaJFXTextField.getText();
        String birthPlace = b.substring(0, 1).toUpperCase() + b.substring(1);
        p.setBornCity(birthPlace);
        p.setBirthday(dataNascitaJFXTextField.getText());
        String[] birthday = dataNascitaJFXTextField.getText().split("/");
        p.setDay(birthday[0]);
        p.setMonth(birthday[1]);
        p.setYear(birthday[2]);

        return p;
    }
}
