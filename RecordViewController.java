import javafx.scene.layout.Region;

public class RecordViewController implements ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

  public void reset()
  {
    // Reset
  }

  public Region getRoot()
  {
    return root;
  }
}
