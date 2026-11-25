import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class CollectionViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  @FXML private TextField searchField;
  @FXML private ListView<Record> recordListView;
  @FXML private Label messageLabel;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

  public void reset()
  {
    if (searchField != null)
      searchField.clear();
    showAll();
  }

  @FXML private void onSearch()
  {
    String text = searchField.getText();
    if (text == null || text.trim().isEmpty())
    {
      showAll();
      return;
    }

    ArrayList<Record> results = modelManager.searchRecords(text.trim());
    recordListView.getItems().setAll(results);
    messageLabel.setText(results.isEmpty() ? "No matching records found." : "");
  }

  private void showAll()
  {
    ArrayList<Record> all = modelManager.getActiveRecords();
    recordListView.getItems().setAll(all);
    messageLabel.setText(all.isEmpty() ? "No records yet" : "");
  }

  public Region getRoot()
  {
    return root;
  }
}
