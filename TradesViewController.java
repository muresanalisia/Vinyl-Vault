import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import java.time.LocalDate;

public class TradesViewController {
    @FXML
    private Region root;

    @FXML
    private ListView<Trade> allTradesList;

    @FXML
    private ListView<Trade> lentTradesList;

    @FXML
    private ListView<Trade> borrowedTradesList;

    @FXML
    private ListView<Trade> overdueTradesList;

    @FXML
    private ListView<Trade> historyList;

    @FXML
    private DatePicker returnDatePicker;

    private ViewHandler viewHandler;
    private VinylModelManager modelManager;

    public void init(ViewHandler viewHandler,
                     VinylModelManager modelManager) {

        this.viewHandler = viewHandler;
        this.modelManager = modelManager;

        updateLists();
    }

    private void updateLists() {
        allTradesList.getItems().setAll(
                modelManager.getAllTrades()
        );

        lentTradesList.getItems().setAll(
                modelManager.getActiveLentTrades()
        );

        borrowedTradesList.getItems().setAll(
                modelManager.getActiveBorrowedTrades()
        );

        overdueTradesList.getItems().setAll(
                modelManager.getOverdueTrades()
        );

        historyList.getItems().setAll(
                modelManager.getTradeHistory()
        );
    }

    @FXML
    private void returnRecord() {
        Trade selectedTrade = allTradesList
                .getSelectionModel()
                .getSelectedItem();

        if (selectedTrade == null) {
            showMessage("Please select a trade first.");
            return;
        }

        if (selectedTrade.isReturned()) {
            showMessage("This record has already been returned.");
            return;
        }

        LocalDate selectedDate = returnDatePicker.getValue();

        if (selectedDate == null) {
            showMessage("Please select a return date.");
            return;
        }

        Date returnDate = new Date(
                selectedDate.getDayOfMonth(),
                selectedDate.getMonthValue(),
                selectedDate.getYear()
        );

        modelManager.returnRecord(selectedTrade, returnDate);

        updateLists();
        returnDatePicker.setValue(null);
    }

    private void showMessage(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Trades");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Region getRoot() {
        return root;
    }
}
