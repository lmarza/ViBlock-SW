package Controller;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ControllerAlert
{
    public void displayAlert(String s)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Controlla le credenziali d'accesso");
        alert.setHeaderText(null);
        alert.setContentText(s);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        alert.showAndWait();
    }

    public Alert displayConfirmation(String s)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma");
        alert.setHeaderText("");
        alert.setContentText(s);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        alert.showAndWait();

        return alert;
    }

    public void displayInformation(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText("");
        alert.setContentText(s);
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("images/Vi Block.png"));

        alert.showAndWait();
    }
}