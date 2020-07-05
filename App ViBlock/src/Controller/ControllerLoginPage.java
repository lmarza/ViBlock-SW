package Controller;

import Data.Manager;
import Model.ModelDBManagers;
import Model.ModelManager;
import Utils.StageManager;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ControllerLoginPage {

    @FXML
    private JFXTextField userJFXTextField;

    @FXML
    private JFXPasswordField pswJFXPasswordField;

    @FXML
    private JFXButton loginJFXButton;

    @FXML
    private JFXComboBox<Manager> multiUserComboBox;
    private ObservableList<Manager> managerObservableList = FXCollections.observableArrayList();

    @FXML
    private JFXCheckBox multiApertJFXCheckBox;

    private ArrayList<Manager> manager;

    @FXML
    private void initialize()
    {
        //populate combo box of managers
        populateManagersComboBox();
        multiUserComboBox.setItems(managerObservableList);
        multiApertJFXCheckBox.setOnAction(this::handleMultiApertJFXButton);
        loginJFXButton.setOnAction(this::handleLoginJFXButton);
        // handle to press enter for login
        pswJFXPasswordField.setOnKeyReleased(event ->
        {
            if (event.getCode() == KeyCode.ENTER)
                loginJFXButton.fire();
        });
    }

    private void handleMultiApertJFXButton(ActionEvent actionEvent) {

        if(multiApertJFXCheckBox.isSelected())
        {
            multiUserComboBox.setDisable(false);
            multiUserComboBox.setOpacity(1);
        }

        if(!multiApertJFXCheckBox.isSelected())
        {
            multiUserComboBox.setPromptText("Scegli Socio");
            multiUserComboBox.setDisable(true);
            multiUserComboBox.setOpacity(0.33);
        }
    }

    private void populateManagersComboBox() {
        ModelManager DBmanagers = new ModelDBManagers();
        managerObservableList.addAll((DBmanagers.getManagers()));
    }

    private void handleLoginJFXButton(ActionEvent actionEvent)
    {

        StageManager mainPage = new StageManager();
        mainPage.setStageMainPage((Stage) loginJFXButton.getScene().getWindow());
    }
}

