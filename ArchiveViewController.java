import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class ArchiveViewController implements ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  // My archive (archived records)
  @FXML private TableView<Record> recordTable;
  @FXML private TableColumn<Record, String> titleColumn;
  @FXML private TableColumn<Record, String> artistColumn;
  @FXML private TableColumn<Record, Integer> yearColumn;
  @FXML private TableColumn<Record, String> versionColumn;
  @FXML private TableColumn<Record, String> barcodeColumn;
  @FXML private TextField recordSearchField;
  @FXML private ComboBox<String> fieldBox;

  // My archived groupings
  @FXML private TableView<Grouping> groupingTable;
  @FXML private TableColumn<Grouping, String> groupingNameColumn;
  @FXML private TextField groupingSearchField;

  @FXML private Label messageLabel;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;

    if (recordTable != null)
    {
      titleColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("title"));
      artistColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("artistsText"));
      yearColumn.setCellValueFactory(new PropertyValueFactory<Record, Integer>("year"));
      versionColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("version"));
      barcodeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("barcode"));
      groupingNameColumn.setCellValueFactory(new PropertyValueFactory<Grouping, String>("name"));

      if (fieldBox.getItems().isEmpty())
        fieldBox.getItems().addAll("All", "Title", "Artist", "Year", "Version", "Genre", "Barcode");
    }

    reset();
  }

  public void reset()
  {
    if (recordTable == null)
      return;

    recordSearchField.clear();
    groupingSearchField.clear();
    fieldBox.setValue("All");
    messageLabel.setText("");
    showRecords(modelManager.getArchivedRecords());
    showGroupings(modelManager.getArchivedGroupings());
  }

  private void showRecords(ArrayList<Record> records)
  {
    recordTable.setItems(FXCollections.observableArrayList(records));
  }

  private void showGroupings(ArrayList<Grouping> groupings)
  {
    groupingTable.setItems(FXCollections.observableArrayList(groupings));
  }

  @FXML private void onSearchRecords()
  {
    messageLabel.setText("");
    String text = recordSearchField.getText();
    if (text.trim().isEmpty())
    {
      showRecords(modelManager.getArchivedRecords());
      return;
    }

    ArrayList<Record> results = modelManager.searchArchivedRecords(fieldBox.getValue(), text.trim());
    showRecords(results);
    if (results.isEmpty())
      messageLabel.setText("No matching archived records found.");
  }

  @FXML private void onSearchGroupings()
  {
    messageLabel.setText("");
    // Partial, ignores case, empty shows everything (GroupingList handles that)
    ArrayList<Grouping> results = modelManager.searchArchivedGroupings(groupingSearchField.getText());
    showGroupings(results);
    if (results.isEmpty())
      messageLabel.setText("No matching archived groupings found.");
  }

  @FXML private void onUnarchiveRecord()
  {
    messageLabel.setText("");
    Record selected = recordTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      messageLabel.setText("Select a record to unarchive.");
      return;
    }

    try
    {
      modelManager.unarchiveRecord(selected);
    }
    catch (Exception e)
    {
      messageLabel.setText(e.getMessage());
      return;
    }
    onSearchRecords();
  }

  @FXML private void onUnarchiveGrouping()
  {
    messageLabel.setText("");
    Grouping selected = groupingTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      messageLabel.setText("Select a grouping to unarchive.");
      return;
    }

    try
    {
      modelManager.unarchiveGrouping(selected);
    }
    catch (Exception e)
    {
      messageLabel.setText(e.getMessage());
      return;
    }
    onSearchGroupings();
  }

  @FXML private void onBack()
  {
    viewHandler.openView("home");
  }

  public Region getRoot()
  {
    return root;
  }
}
