import java.util.ArrayList;

public class Grouping {
  private String name;
  private boolean archived;
  private ArrayList<Record> records;

  public Grouping(String name) {
    this.name = name;
    this.archived = false;
    this.records = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addRecord(Record record) {
    if (record != null) {
      records.add(record);
    }
  }

  public void removeRecord(Record record) {
    records.remove(record);
  }

  public ArrayList<Record> getAllRecords() {
    return records;
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

