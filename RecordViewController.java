import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class RecordViewController implements ViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;
  private Record record;

  @FXML private Label coverPlaceholder;
  @FXML private Label titleLabel;
  @FXML private Label artistLabel;
  @FXML private Label yearLabel;
  @FXML private Label versionLabel;
  @FXML private Label genreLabel;
  @FXML private Label barcodeLabel;
  @FXML private Label statusLabel;
  @FXML private VBox loanBox;
  @FXML private Label personLabel;
  @FXML private Label contactLabel;
  @FXML private Label dueDateLabel;
  @FXML private Button archiveButton;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

  // Called by the ViewHandler before this view is shown
  public void setRecord(Record record)
  {
    this.record = record;
    reset();
  }

  public void reset()
  {
    if (record == null || titleLabel == null)
      return;

    coverPlaceholder.setText("No cover");
    titleLabel.setText(record.getTitle());
    artistLabel.setText(record.getArtistsText());
    yearLabel.setText(record.getYear() == 0 ? "Unknown" : "" + record.getYear());
    versionLabel.setText(record.getVersion());
    genreLabel.setText(String.join(", ", record.getGenres()));
    barcodeLabel.setText(record.getBarcode());
    archiveButton.setDisable(record.isArchived());

    Trade trade = modelManager.getActiveTradeFor(record);
    if (trade == null)
    {
      statusLabel.setText(record.isArchived() ? "Archived" : "In collection");
      loanBox.setVisible(false);
      loanBox.setManaged(false);
    }
    else
    {
      statusLabel.setText(trade.isLent() ? "Lent out" : "Borrowed");
      personLabel.setText((trade.isLent() ? "Lent to: " : "Borrowed from: ")
          + trade.getPerson().getName());
      contactLabel.setText("Contact: " + trade.getPerson().getContact());
      dueDateLabel.setText("Due date: " + trade.getDateInterval().getEndDate());
      loanBox.setVisible(true);
      loanBox.setManaged(true);
    }
  }

  @FXML private void onEdit()
  {
    viewHandler.openView("edit");
  }

  @FXML private void onArchive()
  {
    modelManager.archiveRecord(record);
    reset();
  }

  @FXML private void onAddToGrouping()
  {
    viewHandler.openView("addToGrouping");
  }

  @FXML private void onTrade()
  {
    viewHandler.openView("trade");
  }

  public Region getRoot()
  {
    return root;
  }
}
