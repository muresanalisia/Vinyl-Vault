import java.util.ArrayList;

public class RecordList
{
  private ArrayList<Record> recordList;
  public RecordList()
  {
    this.recordList = null;
  }

  public void addRecord(Record record)
  {
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

  public ArrayList<Record> search(String field, String text, boolean archived)
  {
    ArrayList<Record> results = new ArrayList<>();
    String searchText = text.toLowerCase().trim();

    for(Record record : recordList)
    {
      if(record.isArchived() == archived)
      {
        String toSearch;

        switch(field)
        {
          case "Title":
            toSearch = record.getTitle();
            break;
          case "Artist":
            toSearch = record.getArtist();
            break;
          case "Year":
            toSearch = "" + record.getYear();
            break;
          case "Version":
            toSearch = record.getVersion();
            break;
          case "Genre":
            toSearch = record.getGenre();
          case "Barcode":
            toSearch = record.getBarcode();
            break;
          default:
            toSearch = record.getTitle() + " "
                + record.getArtist() + " "
                + record.getYear() + " "
                + record.getVersion() + " "
                + record.getGenre() + " "
                + record.getBarcode();
        }
        if(toSearch.toLowerCase().contains(searchText))
          results.add(record);
      }
    }
    return results;
  }
}
