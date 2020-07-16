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
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.time.temporal.ChronoUnit;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;

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
    private JFXButton vecchio10IngressiJFXButton;

    @FXML
    private JFXButton vecchioMensileJFXButton;

    @FXML
    private JFXButton vecchioTrimestraleJFXButton;

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

    private String scarpette = "NO";

    private int entrateResidue = 0;

    private String scadenzaAbbonamento = "";

    private int giorniResidui = 0;


    @FXML
    private void initialize() {
        //populateClientInformation(person);
        ingressoSingoloJFXButton.setOnAction(this::handleSingleEntrace);
        dieciIngrJFXButton.setOnAction(this::handleTenEntrace);
        mensileJFXButton.setOnAction(this::handleMonthEntrace);
        trimeJFXButton.setOnAction(this::handle3MonthEntrace);
        corsoJFXButton.setOnAction(this::handleClassEntrace);
        vecchio10IngressiJFXButton.setOnAction(this::handleOldTenEntranceSubmission);
        vecchioMensileJFXButton.setOnAction(this::handleOldMonthEntranceSubmission);
        vecchioTrimestraleJFXButton.setOnAction(this::handleOld3MonthEntranceSubmission);
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

    public void setPerson(Person person) {
        this.person = person;

        nomeLabel.setText(person.getName());
        cognomeLabel.setText(person.getSurname());
        CFLabel.setText(person.getCf());
        mailLabel.setText(person.getMail());

        ModelCertificatoMedico modelCertificatoMedicoDB = new ModelDBCertificatoMedico();
        scadCertificatoLabel.setText(modelCertificatoMedicoDB.getExpiryDateMedicalCertificate(person.getMedicalCertificate()));

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] birthday = person.getBirthday().split("-");
        if (year - Integer.parseInt(birthday[2]) < 18)
            U18Label.setText("SI");
        else
            U18Label.setText("NO");
    }

    public void setSideScreen() {
        ControllerSideScreen controllerSideScreen = new ControllerSideScreen();
        controllerSideScreen.fillLabel(managers, user1Label, user2Label, dateLabel, saldoAperturaLabel, saldoGiorLabel, totCassaLabel, PersEntrateLabel, prelievoLabel);
    }


    private void handleTenEntrace(ActionEvent actionEvent) {
        setDisableButtons();
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento 10 ingressi", U18Label.getText()).intValueExact() + " €");
        ingresso = "Abbonamento 10 ingressi";
    }

    private void handleMonthEntrace(ActionEvent actionEvent) {
        setDisableButtons();
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento mensile", U18Label.getText()).intValueExact() + " €");
        ingresso = "Abbonamento mensile";
    }

    private void handle3MonthEntrace(ActionEvent actionEvent) {
        setDisableButtons();
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Abbonamento trimestrale", U18Label.getText()).intValueExact() + " €");


        ingresso = "Abbonamento trimestrale";
    }

    private void handleClassEntrace(ActionEvent actionEvent) {
        setDisableButtons();
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Tessera corso", U18Label.getText()).intValueExact() + " €");
        ingresso = "Tessera corso";
    }

    private void handleOldTenEntranceSubmission(ActionEvent actionEvent) {
        setDisableButtons();
        totDaPagareLabel.setText(0 + " €");
        ingresso = "Vecchio 10 ingressi";

        ControllerAlert alert = new ControllerAlert();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("");
        dialog.setTitle("Entrate residue");
        dialog.setContentText("Entrate rimaste(dopo l'ingresso odierno):");
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        Optional<String> s = dialog.showAndWait();
        if (s.isPresent()) {
            try {
                int i = Integer.parseInt(s.get());
                entrateResidue = i;
            } catch (NumberFormatException e) {
                alert.displayAlert("Controlla la cifra inserita!");
            }
        }
    }

    private void handleOldMonthEntranceSubmission(ActionEvent actionEvent) {
        setDisableButtons();
        totDaPagareLabel.setText(0 + " €");
        ingresso = "Vecchio mensile";

        ControllerAlert alert = new ControllerAlert();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("");
        dialog.setTitle("Scandenza abbonamento");
        dialog.setContentText("Data scadenza abbonamento:");
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        Optional<String> s = dialog.showAndWait();
        if (s.isPresent()) {
            try {
                scadenzaAbbonamento = s.get();
            } catch (NumberFormatException e) {
                alert.displayAlert("Controlla la data!");
            }
        }

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] split = scadenzaAbbonamento.split("/");
        if(split.length < 3)
            alert.displayAlert("Controlla la data inserita!\n");
        else {
            if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                alert.displayAlert("-Giorno nascita non valido!\n");
            if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                alert.displayAlert("-Mese nascita non valido!\n");
            if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) > year ||(Integer.parseInt(split[2]) < 1900)))
                alert.displayAlert("-Anno nascita non valido (YYYY, 4 cifre necessarie)!\n");


            String dateBeforeString = "2020-03-09";
            String dateAfterString = split[2]+"-"+split[1]+"-"+split[0];

            //Parsing the date
            LocalDate dateBefore = LocalDate.parse(dateBeforeString);
            LocalDate dateAfter = LocalDate.parse(dateAfterString);

            //calculating number of days in between
            giorniResidui = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);

            if (giorniResidui > 0)
                alert.displayInformation(String.format("Abbonamento valido per il risarcimento!\nGiorni alla scadenza: %d",giorniResidui));
        }
    }

    private void handleOld3MonthEntranceSubmission(ActionEvent actionEvent) {
        setDisableButtons();
        totDaPagareLabel.setText(0 + " €");
        ingresso = "Vecchio trimestrale";

        ControllerAlert alert = new ControllerAlert();
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("");
        dialog.setTitle("Scadenza abbonamento");
        dialog.setContentText("Data scadenza abbonamento:");
        ((Stage) dialog.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        Optional<String> s = dialog.showAndWait();
        if (s.isPresent()) {
            try {
                scadenzaAbbonamento = s.get();
            } catch (NumberFormatException e) {
                alert.displayAlert("Controlla la data!");
            }
        }

        LocalDate now = LocalDate.of(2020,3, 9);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] split = scadenzaAbbonamento.split("/");
        if(split.length < 3)
            alert.displayAlert("Controlla la data inserita!\n");
        else {
            if(!isNumerical(split[0]) || split[0].length() > 2 || (Integer.parseInt(split[0]) > 31 ||(Integer.parseInt(split[0]) < 1)))
                alert.displayAlert("-Giorno nascita non valido!\n");
            if(!isNumerical(split[1]) || split[1].length() > 2 || (Integer.parseInt(split[1]) < 1 ||(Integer.parseInt(split[1]) > 12)))
                alert.displayAlert("-Mese nascita non valido!\n");
            if(!isNumerical(split[2]) || split[2].length() != 4 || (Integer.parseInt(split[2]) > year ||(Integer.parseInt(split[2]) < 1900)))
                alert.displayAlert("-Anno nascita non valido (YYYY, 4 cifre necessarie)!\n");

            String dateBeforeString = "2020-03-09";
            String dateAfterString = split[2]+"-"+split[1]+"-"+split[0];

            //Parsing the date
            LocalDate dateBefore = LocalDate.parse(dateBeforeString);
            LocalDate dateAfter = LocalDate.parse(dateAfterString);

            //calculating number of days in between
            giorniResidui = (int) ChronoUnit.DAYS.between(dateBefore, dateAfter);

            if (giorniResidui > 0)
                alert.displayInformation(String.format("Abbonamento valido per il risarcimento!\nGiorni alla scadenza: %d",giorniResidui));
        }
    }

    private void handleShoes(ActionEvent actionEvent) {
        if (!totDaPagareLabel.getText().isEmpty()) {
            String[] s = totDaPagareLabel.getText().split(" ");
            int partial = Integer.parseInt(s[0]);
            partial += 2;
            totDaPagareLabel.setText(partial + " €");
        } else {
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
        vecchioTrimestraleJFXButton.setDisable(true);
        vecchio10IngressiJFXButton.setDisable(true);
        vecchioMensileJFXButton.setDisable(true);

        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        totDaPagareLabel.setText(modelPagamentoDB.getPriceEntrance("Ingresso singolo", U18Label.getText()).intValueExact() + " €");

        String[] s = dateLabel.getText().split(" ");
        if (s[0].equalsIgnoreCase("sabato") || s[0].equalsIgnoreCase("domenica")) {

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
        vecchioTrimestraleJFXButton.setDisable(false);
        vecchio10IngressiJFXButton.setDisable(false);
        vecchioMensileJFXButton.setDisable(false);
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
        if (totDaPagareLabel.getText().isEmpty()) {
            alert.displayAlert("seleziona prima un tipo di ingresso!\n(Se solo tesseramento scegli ingresso singolo)");
            tesseramentoJFXCheckBox.setSelected(false);
        } else {
            String[] partial = totDaPagareLabel.getText().split(" ");
            if (tesseramentoJFXCheckBox.isSelected()) {
                if (Integer.parseInt(partial[0]) > 10)
                    totDaPagareLabel.setText(Integer.parseInt(partial[0]) + 20 + " €");
                else if (U18Label.getText().equalsIgnoreCase("si") && Integer.parseInt(partial[0]) - 2 == 5)
                    totDaPagareLabel.setText(22 + " €");
                else if (U18Label.getText().equalsIgnoreCase("no") && Integer.parseInt(partial[0]) - 2 == 7)
                    totDaPagareLabel.setText(22 + " €");
                else
                    totDaPagareLabel.setText(20 + " €");
            } else {
                totDaPagareLabel.setText(Integer.parseInt(partial[0]) - 20 + " €");
            }
        }
    }

    private void handleContantiJFXCheckBox(ActionEvent actionEvent) {
        if (contantiJFXCheckBox.isSelected()) {
            cartaJFXCheckBox.setSelected(false);
        }
    }

    private void handleCartaJFXCheckBox(ActionEvent actionEvent) {
        if (cartaJFXCheckBox.isSelected()) {
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
        if (contantiJFXCheckBox.isSelected())
            payment.setPaymentType("Contante");
        else
            payment.setPaymentType("Carta");

        payment.setEnterType(ingresso);
        if (tesseramentoJFXCheckBox.isSelected()) {
            payment.setMembership("SI");
            payment.setPriceMembership(new BigDecimal(20.00));
        } else
            payment.setMembership("NO");

        if (scarpette.equalsIgnoreCase("si")) {
            payment.setShoes("SI");
            payment.setPriceShoes(new BigDecimal(2.00));
        } else
            payment.setShoes("NO");

        /*Add record into DB for DayEntrance or PeriodEntrance*/
        ModelDayEntrance modelDayEntranceDB = new ModelDBDayEntrance();
        ModelPeriodEntrance modelPeriodEntranceDB = new ModelDBPeriodEntrance();
        if ("Abbonamento 10 ingressi".equalsIgnoreCase(ingresso))
            modelDayEntranceDB.insertNewDayEntranceSubmission("Abbonamento 10 ingressi", person.getCf(), 9);
        else if ("Tessera corso".equalsIgnoreCase(ingresso))
            modelDayEntranceDB.insertNewDayEntranceSubmission("Abbonamento 10 ingressi", person.getCf(), 4);
        else if ("Abbonamento mensile".equalsIgnoreCase(ingresso))
            modelPeriodEntranceDB.insertNewMonthPeriodEntranceSubmission(person.getCf());
        else if ("Abbonamento trimestrale".equalsIgnoreCase(ingresso))
            modelPeriodEntranceDB.insertNew3MonthPeriodEntranceSubmission(person.getCf());
        else if("Vecchio 10 Ingressi".equalsIgnoreCase(ingresso))
            modelDayEntranceDB.insertNewDayEntranceSubmission("Abbonamento 10 ingressi", person.getCf(), entrateResidue);
        else if("Vecchio Mensile".equalsIgnoreCase(ingresso))
            modelPeriodEntranceDB.insertOldMonthSubmission(person.getCf(), giorniResidui);
        else if("Vecchio Trimestrale".equalsIgnoreCase(ingresso))
            modelPeriodEntranceDB.insertOld3MonthSubmission(person.getCf(), giorniResidui);


        /*Add payment into DB*/
        ModelPagamento modelPagamentoDB = new ModelDBPagamento();
        modelPagamentoDB.insertNewPayment(payment);

        confirm.displayInformation("Pagamento regitrato!");
        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) homePageImageView.getScene().getWindow(), managers);

    }
    private void setDisableButtons(){
        ingressoSingoloJFXButton.setDisable(true);
        mensileJFXButton.setDisable(true);
        trimeJFXButton.setDisable(true);
        dieciIngrJFXButton.setDisable(true);
        vecchioTrimestraleJFXButton.setDisable(true);
        vecchio10IngressiJFXButton.setDisable(true);
        vecchioMensileJFXButton.setDisable(true);
        corsoJFXButton.setDisable(true);
    }

    private boolean isNumerical(String s) {
        return s.matches("[+]?([0-9]*[.])?[0-9]+");
    }
}
