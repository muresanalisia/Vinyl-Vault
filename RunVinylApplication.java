import javafx.application.Application;
import javafx.stage.Stage;

public class RunVinylApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Start the application
        stage.setTitle("Vinyl Vault");

        // TODO: Load the main FXML view
        // FXMLLoader loader = new FXMLLoader(
        //     getClass().getResource("FXML.fxml"));
        // Scene scene = new Scene(loader.load());
        // stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}