import java.util.ArrayList;

public class Wishlist {
    private ArrayList<Record> records;

    public Wishlist(){
        records = new ArrayList<Record>();
    }

    public void addRecord(Record record){
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

    public String toString(){
        return records.toString();
    }
}
