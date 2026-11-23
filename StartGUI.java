import javafx.stage.Stage;

public class StartGUI {
    private VinylModelManager modelManager;
    private ViewHandler viewHandler;

    public StartGUI(VinylModelManager modelManager){
        this.modelManager = modelManager;
        viewHandler = new ViewHandler(modelManager);
    }

    public void start(Stage window){
        viewHandler.start(window);
    }
}
