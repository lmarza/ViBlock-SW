import Utils.DatabaseConnection;
import Utils.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
        // Since we use the singleton pattern, we close the database connection here
        DatabaseConnection db = DatabaseConnection.getInstance();

        if(db != null)
            db.DBCloseConnection();
    }

    @Override
    public void start(Stage primaryStage)
    {
        StageManager loginPageState = new StageManager();
        loginPageState.setStageHomepage(primaryStage);
    }
}
