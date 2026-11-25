import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;

import java.util.ArrayList;

public class WishlistViewController implements ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  @FXML private TableView<Record> wishlistTable;
  @FXML private TableColumn<Record, String> titleColumn;
  @FXML private TableColumn<Record, String> artistColumn;
  @FXML private TableColumn<Record, Integer> yearColumn;
  @FXML private TableColumn<Record, String> versionColumn;
  @FXML private TableColumn<Record, String> barcodeColumn;

  // Add form (no price)
  @FXML private TextField titleField;
  @FXML private TextField artistField;
  @FXML private TextField genreField;
  @FXML private TextField barcodeField;
  @FXML private TextField yearField;
  @FXML private TextField versionField;

  // Missing details needed to move a record to the collection
  @FXML private TextField moveBarcodeField;
  @FXML private TextField moveYearField;
  @FXML private TextField moveVersionField;

  // Search
  @FXML private TextField searchField;
  @FXML private ComboBox<String> fieldBox;

  @FXML private Label messageLabel;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;

    if (wishlistTable != null)
    {
      // The text is the name of the getter without "get": "title" -> getTitle()
      titleColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("title"));
      artistColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("artistsText"));
      yearColumn.setCellValueFactory(new PropertyValueFactory<Record, Integer>("year"));
      versionColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("version"));
      barcodeColumn.setCellValueFactory(new PropertyValueFactory<Record, String>("barcode"));

      if (fieldBox.getItems().isEmpty())
        fieldBox.getItems().addAll("All", "Title", "Artist", "Year", "Version", "Genre", "Barcode");
    }

    reset();
  }

  public void reset()
  {
    if (wishlistTable == null)
      return;

    searchField.clear();
    fieldBox.setValue("All");
    clearForm();
    messageLabel.setText("");
    showRecords(modelManager.getWishlist());
  }

  private void showRecords(ArrayList<Record> records)
  {
    wishlistTable.setItems(FXCollections.observableArrayList(records));
  }

  @FXML private void onSearch()
  {
    String text = searchField.getText();
    if (text.trim().isEmpty())
    {
      showRecords(modelManager.getWishlist());
      messageLabel.setText("");
      return;
    }

    ArrayList<Record> results = modelManager.searchWishlist(fieldBox.getValue(), text.trim());
    showRecords(results);
    if (results.isEmpty())
      messageLabel.setText("No matching records found.");
    else
      messageLabel.setText("");
  }

  @FXML private void onAdd()
  {
    messageLabel.setText("");

    // Year is optional, 0 means "not given"
    int year = 0;
    String yearText = yearField.getText().trim();
    if (!yearText.isEmpty())
    {
      try
      {
        year = Integer.parseInt(yearText);
      }
      catch (NumberFormatException e)
      {
        messageLabel.setText("Year must be a number.");
        return;
      }
    }

    try
    {
      // Record checks title, artist(s), genre(s), year and barcode itself
      ArrayList<String> artists = splitList(artistField.getText());
      ArrayList<String> genres = splitList(genreField.getText());
      Record record = new Record(titleField.getText(), artists, year,
          versionField.getText(), genres, barcodeField.getText());
      modelManager.addToWishlist(record);
    }
    catch (Exception e)
    {
      messageLabel.setText(e.getMessage());
      return;
    }

    clearForm();
    showRecords(modelManager.getWishlist());
  }

  @FXML private void onRemove()
  {
    messageLabel.setText("");
    Record selected = wishlistTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      messageLabel.setText("Select a record to remove.");
      return;
    }

    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
        "Remove \"" + selected.getTitle() + "\" from your wishlist?",
        ButtonType.YES, ButtonType.NO);
    confirm.setHeaderText(null);
    confirm.showAndWait();

    if (confirm.getResult() == ButtonType.YES)
    {
      modelManager.removeFromWishlist(selected);
      showRecords(modelManager.getWishlist());
    }
  }

  @FXML private void onMoveToCollection()
  {
    messageLabel.setText("");
    Record selected = wishlistTable.getSelectionModel().getSelectedItem();
    if (selected == null)
    {
      messageLabel.setText("Select a record to move.");
      return;
    }

    // Use whatever the user typed in the three "move" fields to fill in the gaps
    try
    {
      String barcode = moveBarcodeField.getText().trim();
      if (!barcode.isEmpty())
        selected.setBarcode(barcode);

      String yearText = moveYearField.getText().trim();
      if (!yearText.isEmpty())
        selected.setYear(Integer.parseInt(yearText));

      String version = moveVersionField.getText().trim();
      if (!version.isEmpty())
        selected.setVersion(version);
    }
    catch (NumberFormatException e)
    {
      messageLabel.setText("Year must be a number.");
      return;
    }
    catch (IllegalArgumentException e)
    {
      messageLabel.setText(e.getMessage());
      return;
    }

    // A record in the collection needs a barcode, a year and a version
    if (!selected.hasAllDetails())
    {
      messageLabel.setText("Barcode, year and version are needed. Fill in the missing ones next to the Move button.");
      return;
    }

    try
    {
      modelManager.moveToCollection(selected);
    }
    catch (Exception e)
    {
      messageLabel.setText(e.getMessage());
      return;
    }

    moveBarcodeField.clear();
    moveYearField.clear();
    moveVersionField.clear();
    showRecords(modelManager.getWishlist());
  }

  @FXML private void onBack()
  {
    viewHandler.openView("home");
  }

  // "Queen, David Bowie" -> ["Queen", "David Bowie"]
  private ArrayList<String> splitList(String text)
  {
    ArrayList<String> list = new ArrayList<String>();
    String[] parts = text.split(",");
    for (int i = 0; i < parts.length; i++)
    {
      if (!parts[i].trim().isEmpty())
        list.add(parts[i].trim());
    }
    return list;
  }

  private void clearForm()
  {
    titleField.clear();
    artistField.clear();
    genreField.clear();
    barcodeField.clear();
    yearField.clear();
    versionField.clear();
    moveBarcodeField.clear();
    moveYearField.clear();
    moveVersionField.clear();
  }

  public Region getRoot()
  {
    return root;
  }
}
