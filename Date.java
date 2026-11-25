import java.time.LocalDate;

public class Date
{
  private int day, month, year;

  public Date(int day, int month, int year)
  {
    if (year < 1 || year > 9999)
      throw new IllegalArgumentException("Invalid year: " + year);
    if (month < 1 || month > 12)
      throw new IllegalArgumentException("Invalid month: " + month);
    if (day < 1 || day > daysInMonth(month, year))
      throw new IllegalArgumentException("Invalid day: " + day);

    this.day = day;
    this.month = month;
    this.year = year;
  }

  public static Date parse(String text)
  {
    if (text == null)
      throw new IllegalArgumentException("Date is missing");
    String[] parts = text.trim().split("\\.");
    if (parts.length != 3)
      throw new IllegalArgumentException("Date must be of format DD.MM.YYYY");
    try
    {
      return new Date(Integer.parseInt(parts[0].trim()),
          Integer.parseInt(parts[1].trim()), Integer.parseInt(parts[2].trim()));
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException("Date must only contain numbers.");
    }
  }

  public Date OneYearAgoToday()
  {
    int newYear = year-1;
    int newDay = Math.min(day, daysInMonth(month, newYear));
    return new Date(newDay, month, newYear);
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

  public boolean isBefore(Date other)
  {
    if (year != other.year)
      return year < other.year;
    if (month != other.month)
      return month < other.month;
    return day < other.day;
  }

  public boolean after(Date other)
  {
    return other.isBefore(this);
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

  public static boolean isLeapYear(int year)
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

  public static int daysInMonth(int month, int year)
  {
    switch (month)
    {
      case 2:
        return isLeapYear(year) ? 29 : 28;
      case 4, 6, 9, 11:
        return 30;
      case 1, 3, 5, 7, 8, 10, 12:
        return 31;
      default:
        throw new IllegalArgumentException("Invalid month: " + month);
    }
  }

  public int totalDays()
  {
    int total = this.day;
    for (int y = 1; y < this.year; y++) {
      if(isLeapYear(y))
        total += 366;
      else total += 365;
    }
    for (int m = 1; m < this.month; m++) {
      total += daysInMonth(m, this.year);
    }
    return total;
  }
}
