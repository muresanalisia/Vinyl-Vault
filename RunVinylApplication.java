import javafx.application.Application;
import javafx.stage.Stage;

public class RunVinylApplication extends Application {

    @Override
    public void start(Stage stage) {
        VinylModelManager manager = new VinylModelManager("vinylvault.bin");
        StartGUI gui = new StartGUI(manager);
        gui.start(stage);
}

    public static void main(String[] args) {
        launch(args);
    }
}
