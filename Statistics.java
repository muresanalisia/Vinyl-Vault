import java.util.ArrayList;

public class Statistics
{
  public ArrayList<Record> getMostLentOutRecords(TradeList trades)
  {
    ArrayList<Record> records = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (trades == null || trades.size() == 0)
      return new ArrayList<>();

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (trade == null || !trade.isLent())
        continue;

      Record record = trade.getRecord();
      int index = records.indexOf(record);
      if (index != -1)
        counts.set(index, counts.get(index) + 1);
      else
      {
        records.add(record);
        counts.add(1);
      }
    }

    int maxCount = 0;
    for (int i = 0; i < counts.size(); i++)
    {
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);
    }

    ArrayList<Record> mostLent = new ArrayList<>();
    for (int i = 0; i < records.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        mostLent.add(records.get(i));
    }

    return mostLent;
  }

  public String[] getMostCommonGenres(RecordList records)
  {
    if (records == null)
      return new String[0];

    RecordList activeRecords = records.getActiveRecords();
    if (activeRecords == null || activeRecords.size() == 0)
      return new String[0];

    ArrayList<String> genres = new ArrayList<>();
    for (int i = 0; i < activeRecords.size(); i++)
    {
      Record record = activeRecords.getRecord(i);
      if (record != null && record.getGenre() != null && !record.getGenre().isEmpty())
      {
        genres.add(record.getGenre());
      }
    }

    return mostCommon(genres).toArray(new String[0]);
  }

  public String[] getMostCommonDecades(RecordList records)
  {
    if (records == null)
      return new String[0];

    RecordList activeRecords = records.getActiveRecords();
    if (activeRecords == null || activeRecords.size() == 0)
      return new String[0];

    ArrayList<String> decades = new ArrayList<>();
    for (int i = 0; i < activeRecords.size(); i++)
    {
      Record record = activeRecords.getRecord(i);
      if (record != null && record.getDecade() != null && !record.getDecade().isEmpty())
      {
        decades.add(record.getDecade());
      }
    }

    return mostCommon(decades).toArray(new String[0]);
  }

  public String[] getMostCommonArtists(RecordList records)
  {
    if (records == null)
      return new String[0];

    RecordList activeRecords = records.getActiveRecords();
    if (activeRecords == null || activeRecords.size() == 0)
      return new String[0];

    ArrayList<String> artists = new ArrayList<>();
    for (int i = 0; i < activeRecords.size(); i++)
    {
      Record record = activeRecords.getRecord(i);
      if (record != null && record.getArtist() != null && !record.getArtist().isEmpty())
      {
        artists.add(record.getArtist());
      }
    }

    return mostCommon(artists).toArray(new String[0]);
  }

  // Private helper that handles the counting and finding max occurrences
  private ArrayList<String> mostCommon(ArrayList<String> items)
  {
    ArrayList<String> uniqueItems = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (items == null || items.isEmpty())
      return new ArrayList<>();

    for (int i = 0; i < items.size(); i++)
    {
      String item = items.get(i);
      if (item == null || item.isEmpty())
        continue;

      int index = uniqueItems.indexOf(item);
      if (index != -1)
        counts.set(index, counts.get(index) + 1);
      else
      {
        uniqueItems.add(item);
        counts.add(1);
      }
    }

    int maxCount = 0;
    for (int i = 0; i < counts.size(); i++)
    {
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);
    }

    ArrayList<String> result = new ArrayList<>();
    for (int i = 0; i < uniqueItems.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        result.add(uniqueItems.get(i));
    }

    return result;
  }

  public double getReliability(Person person, TradeList trades)
  {
    if (person == null || trades == null || trades.size() == 0)
      return 100.0;

    int totalLentAndReturned = 0;
    int totalReturnedOnTime = 0;

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (trade != null
          && person.equals(trade.getPerson())
          && trade.isLent()
          && trade.isReturned())
      {
        totalLentAndReturned++;
        if (!trade.isReturnedLate())
          totalReturnedOnTime++;
      }
    }

    if (totalLentAndReturned == 0)
      return 100.0;

    return ((double) totalReturnedOnTime / totalLentAndReturned) * 100.0;
  }
}
