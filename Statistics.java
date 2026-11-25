import java.util.ArrayList;

public class Statistics
{
  // Records that were lent out the most during the last year (only counts loans that started
  // within the last year). Several results if there is a tie.
  public ArrayList<StatResult> getMostLentOutRecords(TradeList trades, Date today)
  {
    ArrayList<Record> records = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (trades == null || today == null)
      return new ArrayList<>();

    Date oneYearAgo = today.minusYears(1);

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (trade == null || !trade.isLent())
        continue;
      if (trade.getDateInterval().getStartDate().isBefore(oneYearAgo))
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

    ArrayList<StatResult> mostLent = new ArrayList<>();
    for (int i = 0; i < records.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
      {
        Record record = records.get(i);

        String name = record.getTitle() + " (" + record.getArtistsText();
        if (record.getYear() != 0)
          name += ", " + record.getYear();
        name += ")";

        mostLent.add(new StatResult(name, maxCount));
      }
    }

    return mostLent;
  }

  public ArrayList<StatResult> getMostCommonGenres(RecordList records)
  {
    ArrayList<String> genres = new ArrayList<>();
    if (records != null)
    {
      ArrayList<Record> active = records.getActiveRecords();
      for (int i = 0; i < active.size(); i++)
        genres.addAll(active.get(i).getGenres());
    }
    return mostCommon(genres);
  }

  public ArrayList<StatResult> getMostCommonDecades(RecordList records)
  {
    ArrayList<String> decades = new ArrayList<>();
    if (records != null)
    {
      ArrayList<Record> active = records.getActiveRecords();
      for (int i = 0; i < active.size(); i++)
      {
        if (active.get(i).getYear() != 0)
          decades.add(active.get(i).getDecade());
      }
    }
    return mostCommon(decades);
  }

  public ArrayList<StatResult> getMostCommonArtists(RecordList records)
  {
    ArrayList<String> artists = new ArrayList<>();
    if (records != null)
    {
      ArrayList<Record> active = records.getActiveRecords();
      for (int i = 0; i < active.size(); i++)
        artists.addAll(active.get(i).getArtists());
    }
    return mostCommon(artists);
  }

  // Private helper that handles the counting and finding max occurrences
  private ArrayList<StatResult> mostCommon(ArrayList<String> items)
  {
    ArrayList<String> uniqueItems = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    for (int i = 0; i < items.size(); i++)
    {
      String item = items.get(i);
      if (item != null && !item.isEmpty())
      {
        int index = uniqueItems.indexOf(item);
        if (index != -1)
          counts.set(index, counts.get(index) + 1);
        else
        {
          uniqueItems.add(item);
          counts.add(1);
        }
      }
    }
    
    int maxCount = 0;
    for (int i = 0; i < counts.size(); i++)
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);

    ArrayList<StatResult> result = new ArrayList<>();
    for (int i = 0; i < uniqueItems.size(); i++)
      if (counts.get(i) == maxCount)
        result.add(new StatResult(uniqueItems.get(i), maxCount));

    return result;
  }
}
