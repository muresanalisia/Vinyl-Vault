import java.time.LocalDate;

public class Date
{
  private int day, month, year;

  public Date(int day, int month, int year)
  {
    this.day = day;
    this.month = month;
    this.year = year;
  }

  public int getDay()
  {
    return day;
  }

  public int getMonth()
  {
    return month;
  }

  public int getYear()
  {
    return year;
  }

  public boolean isBefore(Date date2)
  {
    if(date2.year < year)
      return true;
    else if(date2.month < month)
      return true;
    else
      return date2.day < day;
  }

  public int daysUntil(Date date)
  {
    return date.totalDays() - this.totalDays();
  }

  public Date copy()
  {
    return new Date(this.day, this.month, this.year);
  }

  public static Date today()
  {
    LocalDate currentDate = LocalDate.now();
    int currentDay = currentDate.getDayOfMonth();
    int currentMonth = currentDate.getMonthValue();
    int currentYear = currentDate.getYear();
    return new Date(currentDay, currentMonth, currentYear);
  }

  public boolean equals(Object obj)
  {
    if(obj == null || getClass() != obj.getClass())
      return false;
    Date other = (Date)obj;
    return day == other.day && month == other.month && year == other.year;
  }

  public String toString()
  {
    return day + "." + month + "." + year;
  }

  public boolean isLeapYear()
  {
    if (year %4 != 0)
      return false;
    else if (year % 100 == 0)
    {
      return year % 400 == 0;
    }
    else
      return true;
  }

  public int daysInMonth(int m, int year)
  {
    if(month == 2)
      if(isLeapYear())
        return 29;
      else return 28;
    else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 11)
      return 31;
    else
    {
      return 30;
    }
  }

  public int totalDays()
  {
    int total = this.day;
    for (int y = 1; y < this.year; y++) {
      if(isLeapYear())
        total += 366;
      else total += 365;
    }
    for (int m = 1; m < this.month; m++) {
      total += daysInMonth(m, this.year);
    }
    return total;
  }


}
