import java.util.ArrayList;

public class Grouping {
  private String name;
  private boolean archived;
  private ArrayList<Record> records;
  
  public Grouping() {
    this.records = new ArrayList<>();
  }

  public Grouping(String name) {
    this.name = checkName(name);
    this.archived = false;
    this.records = new ArrayList<>();
  }


  private static String checkName (String name){
    if (name == null || name.trim().isEmpty())
      throw new IllegalArgumentException("Grouping name is required");
    return name.trim();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = checkName(name);
  }

  public void addRecord(Record record) {
    if (record == null)
      throw new IllegalArgumentException("Record is missing");
    if (!records.contains(record)) {
      records.add(record);
    }
  }

  public void removeRecord(Record record) {
    records.remove(record);
  }

  public ArrayList<Record> getAllRecords() {
    return new ArrayList<>(records);
  }

  public int size() {
    return records.size();
  }

  public boolean isArchived() {
    return archived;
  }

  public void archive() {
    this.archived = true;
  }

  public void unarchive() {
    this.archived = false;
  }

  public String toString() {
    if (archived) {
      return name + " Archived";
    } else {
      return name;
    }
  }
}
