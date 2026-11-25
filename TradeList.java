import java.util.ArrayList;

public class TradeList
{
  private ArrayList<Trade> tradeList;

  public TradeList()
  {
    this.tradeList = new ArrayList<>();
  }

  public void addTrade(Trade trade)
  {
    tradeList.add(trade);
  }

  public void removeTrade(Trade trade)
  {
    tradeList.remove(trade);
  }

  public Trade getTrade(int index)
  {
    return tradeList.get(index);
  }

  public ArrayList<Trade> getAllTrades()
  {
    return tradeList;
  }

  public ArrayList<Trade> getActiveLentTrades()
  {
    ArrayList<Trade> activeTrades = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.isLent() && !trade.isReturned())
        activeTrades.add(trade);
    }

    return activeTrades;
  }

  public ArrayList<Trade> getActiveBorrowedTrades()
  {
    ArrayList<Trade> activeTrades = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.isBorrowed() && !trade.isReturned())
        activeTrades.add(trade);
    }

    return activeTrades;
  }

  public ArrayList<Trade> getHistory()
  {
    ArrayList<Trade> history = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.isReturned())
        history.add(trade);
    }

    return history;
  }

  public ArrayList<Trade> getOverdueTrades(Date today)
  {
    ArrayList<Trade> overdueTrades = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.isOverdue(today))
        overdueTrades.add(trade);
    }

    return overdueTrades;
  }

  public ArrayList<Trade> getTradesByPerson(Person person)
  {
    ArrayList<Trade> results = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.getPerson().equals(person))
        results.add(trade);
    }

    return results;
  }

  public ArrayList<Trade> getTradesByRecord(Record record)
  {
    ArrayList<Trade> results = new ArrayList<>();

    for (Trade trade : tradeList)
    {
      if (trade.getRecord().equals(record))
        results.add(trade);
    }

    return results;
  }

  public boolean isRecordLentOut(Record record)
  {
    for (Trade trade : tradeList)
    {
      if (trade.getRecord().equals(record)
          && trade.isLent()
          && !trade.isReturned())
        return true;
    }

    return false;
  }

  public int size()
  {
    return tradeList.size();
  }

  
    public ArrayList<Trade> search(String field, String text)
    {
    ArrayList<Trade> results = new ArrayList<>();
      if (text == null || text.trim().isEmpty())
      {
        return new ArrayList<>(tradeList);
      }
      String searchText = text.toLowerCase().trim();

    for(Trade trade : tradeList)
    {
        String toSearch;

        switch(field)
        {
        case "Type":
            toSearch = trade.getType();
            break;

        case "Title":
            toSearch = trade.getRecord().getTitle();
            break;

        case "Artist":
            for (String artist : trade.getRecord().getArtist())
            {
              if (artist.toLowerCase().contains(searchText))
              {
                results.add(trade);
                break;
              }
            }
            continue;

        case "Barcode":
            toSearch = trade.getRecord().getBarcode();
            break;

        case "Version":
            toSearch = trade.getRecord().getVersion();
            break;

         case "Notes":
                toSearch = trade.getNotes();
                break;

        case "Person":
            toSearch = trade.getPerson().getName();
            break;

        case "Email":
            toSearch = trade.getPerson().getEmail();
            break;

        case "Phone":
            toSearch = trade.getPerson().getPhone();
            break;

        case "Start date":
            toSearch = String.valueOf(trade.getDateInterval().getStartDate());
            break;

        case "Due date":
            toSearch = String.valueOf(trade.getDateInterval().getDueDate());
            break;

        case "Return date":
            toSearch = String.valueOf(trade.getReturnDate());
            break;

        default:
            toSearch = trade.getType() + " "
                + trade.getRecord().getTitle() + " "
                + trade.getRecord().getArtist() + " "
                + trade.getRecord().getBarcode() + " "
                + trade.getRecord().getVersion() + " "
                + trade.getNotes() + " "
                + trade.getPerson().getName() + " "
                + trade.getPerson().getEmail() + " "
                + trade.getPerson().getPhone() + " "
                + trade.getDateInterval().getStartDate() + " "
                + trade.getDateInterval().getDueDate() + " "
                + trade.getReturnDate();
        }

        if(toSearch != null &&
           toSearch.toLowerCase().contains(searchText))
        {
            results.add(trade);
        }
    }

    return results;
    }

}
