public class DateInterval
{
  private Date startDate, dueDate;
  public DateInterval(Date startDate, Date dueDate)
  {
    this.startDate = startDate;
    this.dueDate = dueDate;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public void setStartDate(Date startDate)
  {
    this.startDate = startDate;
  }

  public Date getDueDate()
  {
    return dueDate;
  }

  public void setDueDate(Date dueDate)
  {
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
    return "Start date: " + " | due date: " + dueDate;
  }
}
