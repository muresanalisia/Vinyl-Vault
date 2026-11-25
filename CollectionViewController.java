import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class CollectionViewController implements ViewController
{
  private static final int PREVIEW_SIZE = 6;

  private Region root;
  private ViewHandler viewHandler;
  private VinylModelManager modelManager;

  @FXML private TextField searchField;
  @FXML private ComboBox<String> fieldBox;
  @FXML private CheckBox includeArchiveBox;
  @FXML private Label messageLabel;
  @FXML private FlowPane collectionCards;
  @FXML private FlowPane groupingCards;
  @FXML private Label genreTile;
  @FXML private Label decadeTile;
  @FXML private Label artistTile;
  @FXML private Label lentOutTile;

  public void init(ViewHandler viewHandler, VinylModelManager modelManager, Region root)
  {
    this.viewHandler = viewHandler;
    this.modelManager = modelManager;
    this.root = root;

    if (fieldBox != null && fieldBox.getItems().isEmpty())
      fieldBox.getItems().addAll("All", "Title", "Artist", "Year", "Version", "Genre", "Barcode");

    reset();
  }

  public void reset()
  {
    if (collectionCards == null)
      return;

    searchField.clear();
    fieldBox.setValue("All");
    includeArchiveBox.setSelected(false);

    showCollection();
    showGroupings();
    showStatistics();
  }

  @FXML private void onSearch()
  {
    String text = searchField.getText();
    if (text == null || text.trim().isEmpty())
    {
      showCollection();
      return;
    }

    ArrayList<Record> results = modelManager.searchRecords(
        fieldBox.getValue(), text.trim(), includeArchiveBox.isSelected());
    showRecords(results, false);
    messageLabel.setText(results.isEmpty() ? "No matching records found." : "");
  }

  private void showCollection()
  {
    ArrayList<Record> all = modelManager.getActiveRecords();
    showRecords(all, true);
    messageLabel.setText(all.isEmpty() ? "No records yet" : "");
  }

  private void showRecords(ArrayList<Record> records, boolean preview)
  {
    collectionCards.getChildren().clear();

    int shown = records.size();
    if (preview && shown > PREVIEW_SIZE)
      shown = PREVIEW_SIZE;

    for (int i = 0; i < shown; i++)
      collectionCards.getChildren().add(createCard(records.get(i)));
  }

  // A card shows title, artists and "Lent out" / "Borrowed" when the record is on loan
  private VBox createCard(Record record)
  {
    VBox card = new VBox(4);
    card.setPrefWidth(160);
    card.setStyle("-fx-border-color: gray; -fx-padding: 8; -fx-cursor: hand;");

    Label title = new Label(record.getTitle());
    title.setStyle("-fx-font-weight: bold;");
    card.getChildren().add(title);
    card.getChildren().add(new Label(record.getArtistsText()));

    Trade trade = modelManager.getActiveTradeFor(record);
    if (trade != null)
      card.getChildren().add(new Label(trade.isLent() ? "Lent out" : "Borrowed"));
    else if (record.isArchived())
      card.getChildren().add(new Label("Archived"));

    card.setOnMouseClicked(event -> viewHandler.openRecordView(record));
    return card;
  }

  private void showGroupings()
  {
    groupingCards.getChildren().clear();

    ArrayList<Grouping> groupings = modelManager.getGroupings();
    int shown = groupings.size();
    if (shown > PREVIEW_SIZE)
      shown = PREVIEW_SIZE;

    for (int i = 0; i < shown; i++)
    {
      VBox card = new VBox(new Label(groupings.get(i).getName()));
      card.setPrefWidth(160);
      card.setStyle("-fx-border-color: gray; -fx-padding: 8;");
      groupingCards.getChildren().add(card);
    }
  }

  // Statistics tiles show counts, for example "86 Pop records"
  private void showStatistics()
  {
    genreTile.setText(countText(modelManager.getMostCommonGenres()) + " records");
    decadeTile.setText(countText(modelManager.getMostCommonDecades()) + " records");
    artistTile.setText(countText(modelManager.getMostCommonArtists()) + " records");

    ArrayList<StatResult> lent = modelManager.getMostLentOutRecords();
    if (lent.isEmpty())
      lentOutTile.setText("No data yet");
    else
      lentOutTile.setText(names(lent) + " lent out " + lent.get(0).getCount() + " times");
  }

  private String countText(ArrayList<StatResult> results)
  {
    if (results.isEmpty())
      return "No data yet";
    return results.get(0).getCount() + " " + names(results);
  }

  private String names(ArrayList<StatResult> results)
  {
    String text = "";
    for (int i = 0; i < results.size(); i++)
    {
      if (i > 0)
        text += " / ";
      text += results.get(i).getName();
    }
    return text;
  }

  @FXML private void onAddRecord()
  {
    viewHandler.openView("addRecord");
  }

  @FXML private void onAddGrouping()
  {
    viewHandler.openView("addGrouping");
  }

  // See all: one shared list view owns all See-all pages (collection, groupings,
  // borrowed, lent out, wishlist, archive, archived groupings).
  @FXML private void onSeeAllCollection()
  {
    viewHandler.openView("seeAllCollection");
  }

  @FXML private void onSeeAllGroupings()
  {
    viewHandler.openView("seeAllGroupings");
  }

  public Region getRoot()
  {
    return root;
  }
}
