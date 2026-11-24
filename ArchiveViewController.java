import javafx.scene.layout.Region;
import model.VinylModelManager;

public class ArchiveViewController {
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root) {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

  public void reset() {
    //reset
  }

  public Region getRoot() {
    return root;
  }
}

