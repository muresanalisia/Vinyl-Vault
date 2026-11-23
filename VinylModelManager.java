import java.util.ArrayList;

public class VinylModelManager {
    private String fileName;
    private VinylVault vinylVault;

    public VinylModelManager(String fileName) {
        this.fileName = fileName;

        Object object = MyFileHandler.readfromBinaryFile(fileName);

        if(object instanceof VinylVault){
            vinylVault = (VinylVault) object;
        }
        else{
            vinylVault = new VinylVault();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void saveVinylVault(VinylVault vault){
        MyFileHandler.writeFromBinaryFile(fileName, vault);
    }

    //Records
    public void addRecord(Record record){
        vinylVault.getAllRecords().addRecord(record);
        saveVinylVault(vinylVault);
    }

    public void archiveRecord(Record record){
        record.archive();
        saveVinylVault(vinylVault);
    }

    public void unarchiveRecord(Record record){
        record.unarchive();
        saveVinylVault(vinylVault);
    }

    public ArrayList<Record> searchRecords(String text){
        return vinylVault.getAllRecords().search(text);
    }

    public ArrayList<Record> getRecordsByGenre(String genre){
        return vinylVault.getAllRecords().getRecordsByGenre(genre);
    }

    //Wishlist
    public void addToWishlist(Record record){
        vinylVault.getAllWishlist().addRecord(record);
        saveVinylVault(vinylVault);
    }

    public void removeFromWishlist(Record record){
        vinylVault.getAllWishlist().removeRecord(record);
        saveVinylVault(vinylVault);
    }

    public void moveToCollection(Record record){
        vinylVault.getAllWishlist().removeRecord(record);
        vinylVault.getAllRecords().addRecord(record);

        saveVinylVault(vinylVault);
    }

    //Loans

    public void lendRecord(Record record, Person person, DateInterval interval, double deposit){
        Trade trade = new Trade("Lent out", record, person, interval, deposit);
        vinylVault.getAllTrades().addTrade(trade);
        saveVinylVault(vinylVault);
    }

    public void borrowRecord(Record record, Person person, DateInterval interval, double deposit){
        Trade trade = new Trade("Borrowed", record, person, interval, deposit);
        vinylVault.getAllTrades().addTrade(trade);
        saveVinylVault(vinylVault);
    }

    public void returnRecord(Trade trade, Date returnDate){
        trade.returnRecord(returnDate);
        saveVinylVault(vinylVault);
    }

    public ArrayList<Trade> getOverdueTrades(){
        Date today = Date.today();
        return vinylVault.getAllTrades().getOverdueTrades(today);
    }

    //Groupings
    public void createGroupings(String name){
        Grouping grouping = new Grouping(name);
        vinylVault.getAllGroupings().addGrouping(grouping);
        saveVinylVault(vinylVault);
    }

    public void removeRecordFromGrouping(Record record, Grouping grouping){
        grouping.archive();
        saveVinylVault(vinylVault);
    }

    //Persons
    public void addPerson(Person person){
        vinylVault.getAllPersons().addPerson(person);
        saveVinylVault(vinylVault);
    }

    public void removePerson(Person person){
        vinylVault.getAllPersons().removePerson(person);
        saveVinylVault(vinylVault);
    }

    //Statistics
    public Statistics getStatistics(){
        return new Statistics();
    }
}





