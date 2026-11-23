package model;

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
      Record record = trade.getRecord();

      int index = records.indexOf(record);
      if(index != -1)
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
    for(int i = 0; i< records.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        mostLent.add(records.get(i));
    }

    return mostLent;

  }

  public String[] getMostCommonGenres(RecordList records)
  {
    ArrayList<String> genres = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (records == null || records.size() == 0)
      return  new String[0];

    for (int i = 0; i < records.size(); i++)
    {
      Record record = records.getRecord(i);
      String genre = record.getGenre();

      if (genre != null && !genre.isEmpty())
      {
        int index = genres.indexOf(genre);
        if (index != -1)
          counts.set(index, counts.get(index) + 1);
        else
        {
          genres.add(genre);
          counts.add(1);
        }
      }
    }

    int maxCount = 0;
    for(int i = 0; i < counts.size(); i++)
    {
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);
    }

    ArrayList<String> mostCommon = new ArrayList<>();
    for (int i = 0; i < genres.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        mostCommon.add(genres.get(i));
    }
    return mostCommon.toArray(new String[0]);
  }


  public String[] getMostCommonDecades(RecordList records)
  {
    ArrayList<String> decades = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (records == null || records.size() == 0)
      return  new String[0];

    for (int i = 0; i < records.size(); i++)
    {
      Record record = records.getRecord(i);
      String decade = record.getDecade();

      if (decade != null && !decade.isEmpty())
      {
        int index = decades.indexOf(decade);
        if (index != -1)
          counts.set(index, counts.get(index) + 1);
        else
        {
          decades.add(decade);
          counts.add(1);
        }
      }
    }

    int maxCount = 0;
    for(int i = 0; i < counts.size(); i++)
    {
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);
    }

    ArrayList<String> mostCommon = new ArrayList<>();
    for (int i = 0; i < decades.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        mostCommon.add(decades.get(i));
    }
    return mostCommon.toArray(new String[0]);

  }

  public String[] getMostCommonArtists(RecordList records)
  {
    ArrayList<String> artists = new ArrayList<>();
    ArrayList<Integer> counts = new ArrayList<>();

    if (records == null || records.size() == 0)
      return  new String[0];

    for (int i = 0; i < records.size(); i++)
    {
      Record record = records.getRecord(i);
      String artist = record.getArtist();

      if (artist != null && !artist.isEmpty())
      {
        int index = artists.indexOf(artist);
        if (index != -1)
          counts.set(index, counts.get(index) + 1);
        else
        {
          artists.add(artist);
          counts.add(1);
        }
      }
    }

    int maxCount = 0;
    for(int i = 0; i < counts.size(); i++)
    {
      if (counts.get(i) > maxCount)
        maxCount = counts.get(i);
    }

    ArrayList<String> mostCommon = new ArrayList<>();
    for (int i = 0; i < artists.size(); i++)
    {
      if (counts.get(i) == maxCount && maxCount > 0)
        mostCommon.add(artists.get(i));
    }
    return mostCommon.toArray(new String[0]);

  }

  public double getReliability(Person person, TradeList trades)
  {
    if (person == null || trades == null || trades.size() == 0)
      return 100.0;

    int totalBorrowedByPerson = 0;
    int totalReturnedOnTime = 0;

    for (int i = 0; i < trades.size(); i++)
    {
      Trade trade = trades.getTrade(i);
      if (trade.getPerson().equals(person) && trade.isBorrowed())
      {
        totalBorrowedByPerson++;
        if (!trade.isOverDue(new Date()))
          totalReturnedOnTime++;
      }
    }

    if (totalBorrowedByPerson == 0)
      return 100.0;

    return ((double) totalReturnedOnTime / totalBorrowedByPerson) * 100.0;

  }

}
