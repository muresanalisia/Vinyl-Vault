import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class HomeViewController implements ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  @FXML private Label recordCountLabel;
  @FXML private Label overdueLabel;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

  public void reset()
  {
    if (recordCountLabel == null)
      return;

    int records = modelManager.getActiveRecords().size();
    recordCountLabel.setText(records == 0 ? "No records yet" : records + " records in your collection");

    int overdue = modelManager.getOverdueTrades().size();
    overdueLabel.setText(overdue == 0 ? "No overdue trades" : overdue + " overdue trade(s)");
  }

  @FXML private void onCollection()
  {
    viewHandler.openView("collection");
  }

  @FXML private void onWishlist()
  {
    viewHandler.openView("wishlist");
  }

  @FXML private void onArchive()
  {
    viewHandler.openView("archive");
  }

  @FXML private void onAddRecord()
  {
    viewHandler.openView("addRecord");
  }

  public Region getRoot()
  {
    return root;
  }
}
