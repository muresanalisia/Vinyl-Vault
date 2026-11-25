import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class RecordViewController
{
  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;
  private Record record;

  @FXML private Label titleLabel;
  @FXML private Label artistLabel;
  @FXML private Label genreLabel;
  @FXML private Label decadeLabel;
  @FXML private Label statusLabel;
  @FXML private Label loanLabel;
  @FXML private Label coverPlaceholder;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;
    reset();
  }

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
    artistLabel.setText(record.getArtist());
    genreLabel.setText(record.getGenre());
    decadeLabel.setText(record.getDecade());
    statusLabel.setText(record.isArchived() ? "Archived" : "Active");

    Trade trade = modelManager.getActiveTradeFor(record);
    if (trade == null)
      loanLabel.setText("");
    else
      loanLabel.setText((trade.isLent() ? "Lent to: " : "Borrowed from: ")
          + trade.getPerson().getName());
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

  public Region getRoot()
  {
    return root;
  }
}
