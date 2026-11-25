import java.util.ArrayList;

public class RecordList
{
  private ArrayList<Record> recordList;
  public RecordList()
  {
    this.recordList = new ArrayList<>();
  }

  public void addRecord(Record record)
  {
    if (record == null)
      throw new IllegalArgumentException("Record is missing");
    recordList.add(record);
  }

  public void removeRecord(Record record)
  {
    recordList.remove(record);
  }

  public Record getRecord(int index)
  {
    return recordList.get(index);
  }

  public ArrayList<Record> getAllRecords()
  {
    return recordList;
  }

  public ArrayList<Record> getActiveRecords()
  {
    ArrayList<Record> activeRecords = new ArrayList<>();
    for (Record record : recordList)
    {
      if (!record.isArchived())
        activeRecords.add(record);
    }
    return activeRecords;
  }

  public ArrayList<Record> getArchivedRecords()
  {
    ArrayList<Record> archivedRecords = new ArrayList<>();
    for (Record record : recordList)
    {
      if (record.isArchived())
        archivedRecords.add(record);
    }
    return archivedRecords;
  }

  // Uses Record.isSameVinyl or null if not found. This is used to avoid duplicates in the collection.
  public Record findSameVinyl(Record other)
  {
    for (Record record : recordList)
    {
      if (record.isSameVinyl(other))
        return record;
    }
    return null;
  }

  public int size()
  {
    return recordList.size();
  }

  public String toString()
  {
    String s = "";
    for(Record record : recordList)
    {
      s += record.toString() + '\n';
    }
    return s;
  }

  //  An empty text matches every record.
  // field: "Title", "Artist", "Year", "Version", "Genre", "Barcode" - anything else searches all of them.
  public ArrayList<Record> search(String field, String text, boolean archived)
  {
    ArrayList<Record> results = new ArrayList<>();
    String searchText = text == null ? "" : text.toLowerCase().trim();

    for(Record record : recordList)
    {
      if(record.isArchived() == archived && matches(record, field, searchText))
        results.add(record);
    }
    return results;
  }

  // Also used by Wishlist.search
  public static boolean matches(Record record, String field, String searchText)
  {
    String toSearch;

    if (field == null)
      field = "";

    switch(field)
    {
      case "Title":
        toSearch = record.getTitle();
        break;
      case "Artist":
        toSearch = record.getArtistsText();
        break;
      case "Year":
        toSearch = record.getYear() == 0 ? "" : "" + record.getYear();
        break;
      case "Version":
        toSearch = record.getVersion();
        break;
      case "Genre":
        toSearch = record.getGenresText();
        break;
      case "Barcode":
        toSearch = record.getBarcode();
        break;
      default:
        toSearch = record.getTitle() + " "
            + record.getArtistsText() + " "
            + (record.getYear() == 0 ? "" : record.getYear()) + " "
            + record.getVersion() + " "
            + record.getGenresText() + " "
            + record.getBarcode();
    }
    return toSearch.toLowerCase().contains(searchText);
  }
}
