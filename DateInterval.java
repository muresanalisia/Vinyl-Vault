public class DateInterval
{
  private Date startDate, dueDate;
  public DateInterval(Date startDate, Date dueDate)
  {
    check(startDate, dueDate);
    this.startDate = startDate;
    this.dueDate = dueDate;
  }

  private static void check(Date startDate, Date dueDate)
  {
    if (startDate == null || dueDate == null)
      throw new IllegalArgumentException("Start date and due date are required");
    if (dueDate.isBefore(startDate))
      throw new IllegalArgumentException("The due date cannot be before the start date");
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public void setStartDate(Date startDate)
  {
    check(startDate, dueDate);
    this.startDate = startDate;
  }

  public Date getDueDate()
  {
    return dueDate;
  }

  public void setDueDate(Date dueDate)
  {
    check(startDate, dueDate);
    this.dueDate = dueDate;
  }

  public boolean equals(Object obj)
  {
    if(obj == null || obj.getClass() != getClass())
      return false;
    DateInterval other = (DateInterval) obj;
    return(other.dueDate.equals(dueDate) && other.startDate.equals(startDate));
  }

  public String toString()
  {
    return "Start date: " + startDate + " | due date: " + dueDate;
  }
}
