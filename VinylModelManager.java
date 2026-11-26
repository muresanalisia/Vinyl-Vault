import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class VinylModelManager {
    private String fileName;
    private VinylVault vinylVault;

    public VinylModelManager(String fileName) {
        this.fileName = fileName;

        VinylVault loaded = null;

        try{
            Object object = MyFileHandler.readFromJson(fileName);

            if(object instanceof VinylVault){
                loaded = (VinylVault) object;
            }
        }

        catch (FileNotFoundException e){
            //First start, no files yet
        }

        catch(IOException | ClassNotFoundException e){
            System.out.println("Could not find the file: " + e.getMessage());
        }

        if(loaded != null){
            this.vinylVault = loaded;
            relinkTrades();
        }

        else{
            this.vinylVault = new VinylVault();
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void saveVinylVault(VinylVault vault){
        try{
            MyFileHandler.writeToJson(fileName, vault);
        }
        catch(IOException e){
            throw new IllegalStateException("Could not save file: "+e.getMessage());
        }
    }

    //Records
    public void addRecord(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(record.isArchived()){
            throw new IllegalStateException("Archived records cannot be added.");
        }

        if(vinylVault.getAllRecords().findSameVinyl(record) != null){
            throw new IllegalStateException("This record is already in the collection.");
        }

        vinylVault.getAllRecords().addRecord(record);
        saveVinylVault(vinylVault);
    }

    public void removeRecord(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(getActiveTradeForRecord(record) != null){
            throw new IllegalStateException("Return the record before removing it.");
        }

        if(vinylVault.getAllRecords().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the collection.");
        }

        vinylVault.getAllRecords().removeRecord(record);
        saveVinylVault(vinylVault);
    }

    public void archiveRecord(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(getActiveTradeForRecord(record) != null){
            throw new IllegalStateException("Return the record before archiving it.");
        }

        if(vinylVault.getAllRecords().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the collection.");
        }

        record.archive();
        saveVinylVault(vinylVault);
    }

    public void unarchiveRecord(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(vinylVault.getAllRecords().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the collection.");
        }

        if(!record.isArchived()){
            throw new IllegalStateException("This record is not archived.");
        }

        record.unarchive();
        saveVinylVault(vinylVault);
    }

    public ArrayList<Record> searchRecords(String field,String text, boolean includeArchive){
        return vinylVault.getAllRecords().search(field, text, includeArchive);
    }

    //Wishlist
    public void addToWishlist(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(record.isArchived()) {
            throw new IllegalStateException("Archived records cannot be added.");
        }

        if(vinylVault.getAllWishlist().findSameVinyl(record) != null){
            throw new IllegalStateException("This record is already in the wishlist.");
        }

        vinylVault.getAllWishlist().addRecord(record);
        saveVinylVault(vinylVault);
    }

    public void removeFromWishlist(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(vinylVault.getAllWishlist().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the wishlist.");
        }

        vinylVault.getAllWishlist().removeRecord(record);
        saveVinylVault(vinylVault);
    }

    public void moveToCollection(Record record){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(record.isArchived()){
            throw new IllegalStateException("Archived records cannot be moved to the collection.");
        }

        if(vinylVault.getAllWishlist().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the wishlist.");
        }

        if(vinylVault.getAllRecords().findSameVinyl(record) != null){
            throw new IllegalStateException("Record is already in the collection.");
        }

        vinylVault.getAllWishlist().removeRecord(record);
        vinylVault.getAllRecords().addRecord(record);

        saveVinylVault(vinylVault);
    }

    public String getRecordStatus(Record record) {
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        Trade activeTrade = getActiveTradeForRecord(record);

         if (activeTrade == null)
            return "In collection";

        if (activeTrade.isLent())
            return "Lent out";

        if (activeTrade.isBorrowed())
            return "Borrowed";

        return "In collection";
    }

    //Loans

    public ArrayList<Trade> getAllTrades() {
        return vinylVault.getAllTrades().getAllTrades();
    }

    public ArrayList<Trade> getActiveLentTrades() {
        return vinylVault.getAllTrades().getActiveLentTrades();
    }

    public ArrayList<Trade> getActiveBorrowedTrades() {
        return vinylVault.getAllTrades().getActiveBorrowedTrades();
    }

    public ArrayList<Trade> getTradeHistory() {
        return vinylVault.getAllTrades().getHistory();
    }

    public void removeTrade(Trade trade) {
        if(trade == null){
            throw new IllegalArgumentException("Trade is required.");
        }

        if(!vinylVault.getAllTrades().getAllTrades().contains(trade)){
            throw new IllegalStateException("Trade is not in the system.");
        }

        vinylVault.getAllTrades().removeTrade(trade);
        saveVinylVault(vinylVault);
    }

    public Trade getActiveTradeForRecord(Record record) {
        return vinylVault.getAllTrades().getActiveTradeForRecord(record);
    }

    public boolean isRecordBorrowed(Record record)
{
    return vinylVault.getAllTrades().isRecordBorrowed(record);
}

    public boolean hasActiveTradeWithPerson(Person person)
    {
        return vinylVault.getAllTrades().hasActiveTradeWithPerson(person);
    }

    private void checkTrade(Record record, Person person, DateInterval interval){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(person == null){
            throw new IllegalArgumentException("Person is required.");
        }

        if(interval == null){
            throw new IllegalArgumentException("Dates are required.");
        }

        if(vinylVault.getAllPersons().find(person) == null){
            throw new IllegalStateException("Person is not in the system.");
        }

        if(interval.getDueDate().isBefore(Date.today())){
            throw new IllegalArgumentException("Due date cannot be in the past");
        }

        if(interval.getStartDate().isBefore(Date.today())){
            throw new IllegalArgumentException("Start date cannot be in the past");
        }

        if(record.isArchived()){
            throw new IllegalStateException("Archived records cannot be traded.");
        }

        if(getActiveTradeForRecord(record) != null){
            throw new IllegalStateException("This record already has an active trade.");
        }
    }

    public void lendRecord(Record record, Person person, DateInterval interval, String notes){
        checkTrade(record, person, interval);

        if(vinylVault.getAllRecords().findSameVinyl(record) == null){
            throw new IllegalStateException("Record is not in the collection.");
        }

        Trade trade = new Trade("Lent", record, person, interval, notes);

        vinylVault.getAllTrades().addTrade(trade);
        saveVinylVault(vinylVault);
    }

    public void borrowRecord(Record record, Person person, DateInterval interval, String notes){
        checkTrade(record, person, interval);

        Trade trade = new Trade("Borrowed", record, person, interval, notes);
        vinylVault.getAllTrades().addTrade(trade);

        saveVinylVault(vinylVault);
    }

    public void returnRecord(Trade trade, Date returnDate){
        if(trade == null){
            throw new IllegalArgumentException("Trade is required.");
        }

        if(!vinylVault.getAllTrades().getAllTrades().contains(trade)){
            throw new IllegalStateException("Trade is not in the system.");
        }

        trade.returnRecord(returnDate);
        saveVinylVault(vinylVault);
    }

    public ArrayList<Trade> getOverdueTrades(){
        Date today = Date.today();
        return vinylVault.getAllTrades().getOverdueTrades(today);
    }

    //Groupings
    public void createGrouping(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Grouping name is required.");
        }

        Grouping grouping = new Grouping(name);
        vinylVault.getAllGroupings().addGrouping(grouping);
        saveVinylVault(vinylVault);
    }

    public void removeRecordFromGrouping(Record record, Grouping grouping){
        if(record == null){
            throw new IllegalArgumentException("Record is required.");
        }

        if(grouping == null){
            throw new IllegalArgumentException("Grouping is required.");
        }

        grouping.removeRecord(record);
        saveVinylVault(vinylVault);
    }

    //Persons
    public void addPerson(Person person){
        if(person == null){
            throw new IllegalArgumentException("Person is required.");
        }

        vinylVault.getAllPersons().addPerson(person);
        saveVinylVault(vinylVault);
    }

    public void removePerson(Person person){
       if(person == null) {
           throw new IllegalArgumentException("Person is required.");
       }

       if(vinylVault.getAllPersons().find(person) == null){
           throw new IllegalStateException("Person is not in the system.");
       }

        if(hasActiveTradeWithPerson(person)){
            throw new IllegalStateException("Return all records before removing this person.");
        }
        vinylVault.getAllPersons().removePerson(person);
        saveVinylVault(vinylVault);
    }

    //Statistics
    public Statistics getStatistics(){
        return new Statistics();
    }

    private Record findRecordByBarcode(String barcode)
    {
    for (Record record : vinylVault.getAllRecords().getAllRecords())
    {
        if (record.getBarcode().equals(barcode))
        {
            return record;
        }
    }

    return null;
    }

    private Person findPersonByEmail(String email)
    {
    for (Person person : vinylVault.getAllPersons().getAllPersons())
    {
        if (person.getEmail().equals(email))
        {
            return person;
        }
    }
    return null;
    }

    private void relinkTrades()
    {
    for (Trade trade : vinylVault.getAllTrades().getAllTrades())
    {
        Record record = findRecordByBarcode(
            trade.getRecordBarcode());

        Person person = findPersonByEmail(
            trade.getPersonEmail());
        if (record == null || person == null)
        {
            throw new IllegalStateException(
                "Could not restore trade relationships.");
        }
        trade.relink(record, person);
    }
    }
}
