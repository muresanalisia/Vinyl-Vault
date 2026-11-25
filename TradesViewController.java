import javafx.fxml.FXML;
import javafx.scene.layout.Region;

public class TradesViewController {

    @FXML
    private Region root;

    private ViewHandler viewHandler;
    private VinylModelManager modelManager;

    public TradesViewController() {

    }

    public void init(ViewHandler viewHandler,
                     VinylModelManager modelManager,
                     Region root) {

        this.viewHandler = viewHandler;
        this.modelManager = modelManager;
        this.root = root;

        // Initialize the view
    }
}
