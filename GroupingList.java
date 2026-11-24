import java.util.ArrayList;

public class GroupingList {
  private ArrayList<Grouping> groupings;

  public GroupingList() {
    this.groupings = new ArrayList<>();
  }

  public void addGrouping(Grouping grouping) {
    if (grouping != null) {
      groupings.add(grouping);
    }
  }

  public void removeGrouping(Grouping grouping) {
    groupings.remove(grouping);
  }

  public Grouping getGrouping(int index) {
    return groupings.get(index);
  }

  public Grouping getGroupingByName(String name) {
    for (Grouping grouping : groupings) {
      if (grouping.getName().equalsIgnoreCase(name)) {
        return grouping;
      }
    }
    return null;
  }

  public ArrayList<Grouping> getActiveGroupings() {
    ArrayList<Grouping> active = new ArrayList<>();
    for (Grouping grouping : groupings) {
      if (!grouping.isArchived()) {
        active.add(grouping);
      }
    }
    return active;
  }

  public ArrayList<Grouping> getArchivedGroupings() {
    ArrayList<Grouping> archived = new ArrayList<>();
    for (Grouping grouping : groupings) {
      if (grouping.isArchived()) {
        archived.add(grouping);
      }
    }
    return archived;
  }

  public int size() {
    return groupings.size();
  }
}

