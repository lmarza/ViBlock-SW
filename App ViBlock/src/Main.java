import Utils.DatabaseConnection;
import Utils.StageManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage)
    {
       StageManager loginPageState = new StageManager();
       loginPageState.setStageHomepage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
        // Since we use the singleton pattern, we close the database connection here
        DatabaseConnection db = DatabaseConnection.getInstance();

        if(db != null)
            db.DBCloseConnection();
    }
}
