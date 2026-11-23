
import java.util.Date;

public class Trade
{
  private String type;
  private double deposit;
  private Record record;
  private Person person;
  private DateInterval dateInterval;
  private Date returnDate;

  public Trade(String type, Record record, Person person,
      DateInterval dateInterval, double deposit)
  {
    this.type = type;
    this.record = record;
    this.person = person;
    this.dateInterval = dateInterval;
    this.deposit = deposit;
    this.returnDate = null;
  }

  public String getType()
  {
    return type;
  }

  public Record getRecord()
  {
    return record;
  }

  public Person getPerson()
  {
    return person;
  }

  public DateInterval getDateInterval()
  {
    return dateInterval;
  }

  public double getDeposit()
  {
    return deposit;
  }

  public Date getReturnDate()
  {
    return returnDate;
  }

  public boolean isLent()
  {
    return type.equalsIgnoreCase("Lent");
  }

  public boolean isBorrowed()
  {
    return type.equalsIgnoreCase("Borrowed");
  }

  public void returnRecord(Date returnDate)
  {
    this.returnDate = returnDate;
  }

  public boolean isReturned()
  {
    return returnDate != null;
  }

  public boolean isOverdue(Date today)
  {
    return !isReturned()
        && today.after(dateInterval.getDueDate());
  }

  public boolean isReturnedLate()
  {
    return isReturned()
        && returnDate.after(dateInterval.getDueDate());
  }

  public String toString()
  {
    return "Type: " + type
        + " | Record: " + record
        + " | Person: " + person
        + " | Date interval: " + dateInterval
        + " | Deposit: " + deposit
        + " | Return date: " + returnDate;
  }
}
