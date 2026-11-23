import javafx.scene.layout.Region;

public class RecordViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
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
