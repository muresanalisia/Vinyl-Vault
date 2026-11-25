public class Trade
{
  private String type;
  private String notes;
  private Record record;
  private Person person;
  private DateInterval dateInterval;
  private Date returnDate;

  public Trade(String type, Record record, Person person,
      DateInterval dateInterval, String notes)
  {
    if (type == null ||
        (!type.equalsIgnoreCase("Lent")
            && !type.equalsIgnoreCase("Borrowed")))
    {
      throw new IllegalArgumentException(
          "Type must be either 'Lent' or 'Borrowed'.");
    }

    if (record == null)
    {
      throw new IllegalArgumentException("Record is required.");
    }

    if (person == null)
    {
      throw new IllegalArgumentException("Person is required.");
    }

    if (dateInterval == null)
    {
      throw new IllegalArgumentException("Dates are required.");
    }
    
    this.type = type;
    this.record = record;
    this.person = person;
    this.dateInterval = dateInterval;
    this.notes = notes;
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

  public String getNotes()
  {
    return notes;
  }

  public Date getReturnDate()
  {
    return returnDate;
  }

  public boolean isLent()
  {
    return type != null && type.equalsIgnoreCase("Lent");
  }

  public boolean isBorrowed()
  {
    return type != null && type.equalsIgnoreCase("Borrowed");
  }

  public void returnRecord(Date returnDate)
  {
    if (isReturned())
    {
      throw new IllegalStateException(
          "This trade has already been returned.");
    }

    if (returnDate == null)
    {
      throw new IllegalArgumentException(
          "Return date is required.");
    }

    if (!returnDate.after(dateInterval.getStartDate()))
    {
      throw new IllegalArgumentException(
          "Return date must be after the start date.");
    }
    
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
        + " | Notes: " + notes
        + " | Return date: " + returnDate;
  }
}
