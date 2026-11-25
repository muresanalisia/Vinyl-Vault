import java.util.ArrayList;

public class Statistics
{
  // Ionela-6: only lent trades count
  public ArrayList<Record> getMostLentOutRecords(TradeList trades)
  {
    if (trades == null || trades.size() == 0)
      return new ArrayList<>();

    ArrayList<Record> records = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (!trade.isLent())
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
    for (int count : counts)
      if (count > maxCount)
        maxCount = count;

    ArrayList<Record> mostLent = new ArrayList<>();
    for (int i = 0; i < records.size(); i++)
      if (counts.get(i) == maxCount && maxCount > 0)
        mostLent.add(records.get(i));

    return mostLent;
  }

  // Ionela-7: only active (non-archived) records
  public String[] getMostCommonGenres(RecordList records)
  {
    ArrayList<String> values = new ArrayList<>();
    if (records != null)
      for (Record r : records.getActiveRecords())
        values.add(r.getGenre());
    return mostCommon(values);
  }

  public String[] getMostCommonDecades(RecordList records)
  {
    ArrayList<String> values = new ArrayList<>();
    if (records != null)
      for (Record r : records.getActiveRecords())
        values.add(r.getDecade());
    return mostCommon(values);
  }

  public String[] getMostCommonArtists(RecordList records)
  {
    ArrayList<String> values = new ArrayList<>();
    if (records != null)
      for (Record r : records.getActiveRecords())
        values.add(r.getArtist());
    return mostCommon(values);
  }

  // Ionela-8: the one shared counting helper
  private String[] mostCommon(ArrayList<String> values)
  {
    ArrayList<String> distinct = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    for (String value : values)
    {
      if (value == null || value.isEmpty())
        continue;
      int index = distinct.indexOf(value);
      if (index != -1)
        counts.set(index, counts.get(index) + 1);
      else
      {
        distinct.add(value);
        counts.add(1);
      }
    }

    int maxCount = 0;
    for (int count : counts)
      if (count > maxCount)
        maxCount = count;

    ArrayList<String> result = new ArrayList<>();
    for (int i = 0; i < distinct.size(); i++)
      if (counts.get(i) == maxCount && maxCount > 0)
        result.add(distinct.get(i));

    return result.toArray(new String[0]);
  }

  // Ionela-3/4/5: no Date or isOverDue needed any more
  public double getReliability(Person person, TradeList trades)
  {
    if (person == null || trades == null || trades.size() == 0)
      return 100.0;

    int totalReturned = 0;
    int totalOnTime = 0;

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (trade.getPerson().equals(person) && trade.isLent() && trade.isReturned())
      {
        totalReturned++;
        if (!trade.isReturnedLate())
          totalOnTime++;
      }
    }

    if (totalReturned == 0)
      return 100.0;

    return ((double) totalOnTime / totalReturned) * 100.0;
  }
}
