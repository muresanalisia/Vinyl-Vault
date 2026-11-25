import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Record> records;

    public Wishlist(){
        records = new ArrayList<Record>();
    }

    public Record findSameVinyl(Record other){
        for(int i=0; i<records.size(); i++){
            if(records.get(i).isSameVinyl(other)){
                return records.get(i);
            }
        }
        return null;
    }

    public void addRecord(Record record){
        if(record == null)
        {
            throw new IllegalArgumentException("Record is missing");
        }
        if(findSameVinyl(record) != null){
            throw new IllegalStateException("This record is already in the wishlist.");
        }
        records.add(record);
    }

    public void removeRecord(Record record){
        records.remove(record);
    }

    public Record getRecord(int index){
        return records.get(index);
    }

    public ArrayList<Record> getAllRecords(){
        return records;
    }

    public int size(){
        return records.size();
    }

    public ArrayList<Record> search(String field, String text){
        ArrayList<Record> results = new ArrayList<>();
        String searchText = text == null ? "" : text.toLowerCase().trim();

        for(Record record : records){
            if(RecordList.matches(record, field, searchText)){
                results.add(record);
            }
        }

        return results;
    }

    public String toString(){
        return records.toString();
    }
}
