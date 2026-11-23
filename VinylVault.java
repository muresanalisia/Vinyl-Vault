package model;

public class VinylVault
{
  private PersonList allPersons;
  private TradeList allTrades;
  private RecordList allRecords;
  private Wishlist allWishlist;
  private GroupingList allGroupings;

  public VinylVault()
  {
    this.allPersons = new PersonList();
    this.allTrades = new TradeList();
    this.allRecords = new RecordList();
    this.allWishlist = new Wishlist();
    this.allGroupings = new GroupingList();
  }

  public RecordList getAllRecords()
  {
    return allRecords;
  }

  public Wishlist getAllWishlists()
  {
    return allWishlist;
  }

  public TradeList getAllTrades()
  {
    return allTrades;
  }

  public GroupingList getAllGroupings()
  {
    return allGroupings;
  }

  public PersonList getAllPersons()
  {
    return allPersons;
  }
}
