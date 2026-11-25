import java.util.ArrayList;

public class GroupingList {
  private ArrayList<Grouping> groupings;

  public GroupingList() {
    this.groupings = new ArrayList<>();
  }

  public void addGrouping(Grouping grouping) {
    if (grouping == null)
      throw new IllegalArgumentException("Grouping is missing");
    if (getGroupingByName(grouping.getName()) != null)
      throw new IllegalArgumentException("A grouping called \"" + grouping.getName() + "\" already exists");
    groupings.add(grouping);
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

  // Search on the grouping name, ignores case. Empty text shows every grouping.
  // archived = true searches the archived groupings, false the active ones.
  public ArrayList<Grouping> search(String text, boolean archived) {
    ArrayList<Grouping> results = new ArrayList<>();
    String searchText;
    if (text == null) {
      searchText = "";
    } else {
      searchText = text.toLowerCase().trim();
    }
    for (Grouping grouping : groupings) {
      if (grouping.isArchived() == archived
          && grouping.getName().toLowerCase().contains(searchText)) {
        results.add(grouping);
      }
    }
    return results;
  }

  public int size() {
    return groupings.size();
  }
}
