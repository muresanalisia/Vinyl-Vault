// One line of a statistic: a name and how many there are (e.g. "Pop" and 86)
public class StatResult
{
  private String name;
  private int count;

  public StatResult(String name, int count)
  {
    this.name = name;
    this.count = count;
  }

  public String getName()
  {
    return name;
  }

  public int getCount()
  {
    return count;
  }

  public String toString()
  {
    return name + " (" + count + ")";
  }
}
