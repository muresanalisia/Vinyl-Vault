import java.util.ArrayList;

public class Record {
  private String title, version, barcode;
  private ArrayList<String> artists, genres;
  private int year;
  private boolean archived;

  // year 0, an empty version and an empty barcode mean "not given" (allowed for wishlist records).
  
  public Record(String title, ArrayList<String> artists, int year, String version, ArrayList<String> genres, String barcode) {
    this.title = checkTitle(title);
    this.artists = checkList(artists, "artist");
    this.year = checkYear(year);
    this.version = checkVersion(version);
    this.genres = checkList(genres, "genre");
    this.barcode = checkBarcode(barcode);
    this.archived = false;
  }

  private static String checkTitle(String title) {
    if (title == null || title.isBlank())
      throw new IllegalArgumentException("Title is required");
    return title.trim();
  }

  private static ArrayList<String> checkList(ArrayList<String> list, String what) {
    if (list == null || list.isEmpty())
      throw new IllegalArgumentException("At least one " + what + " is required");
    ArrayList<String> cleaned = new ArrayList<>();
    for (String item : list) {
      if (item == null || item.isBlank())
        throw new IllegalArgumentException( what + " cannot be empty");
      cleaned.add(item.trim());
    }
    return cleaned;
  }

  private static int checkYear(int year) {
    int currentYear = Date.today().getYear();
    if (year != 0 && (year < 1877 || year > currentYear))
      throw new IllegalArgumentException("Year must be between 1877 and " + currentYear);
    return year;
  }

  private static String checkVersion(String version) {
    return version == null ? "" : version.trim();
  }

  private static String checkBarcode(String barcode) {
    if (barcode == null || barcode.isBlank())
      return "";
    String cleaned = barcode.trim();
    if (!cleaned.matches("[0-9]{8,14}"))
      throw new IllegalArgumentException("Barcode format invalid");
    return cleaned;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = checkTitle(title);
  }

  public ArrayList<String> getArtists() {
    return new ArrayList<>(artists);
  }

  public void setArtists(ArrayList<String> artists) {
    this.artists = checkList(artists, "artist");
  }

  public String getArtistsText() {
    return String.join(", ", artists);
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = checkYear(year);
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = checkVersion(version);
  }

  public ArrayList<String> getGenres() {
    return new ArrayList<>(genres);
  }

  public void setGenres(ArrayList<String> genres) {
    this.genres = checkList(genres, "genre");
  }

  public String getGenresText() {
    return String.join(", ", genres);
  }

  public String getBarcode()
  {
    return this.barcode;
  }

  public void setBarcode(String barcode)
  {
    this.barcode = checkBarcode(barcode);
  }

  public String getDecade() {
    if (year == 0)
      return "Unknown";
    return (year / 10) * 10 + "s";
  }

  // A record in the collection needs a barcode, a year and a version too
  public boolean hasAllDetails() {
    return !barcode.isEmpty() && year != 0 && !version.isEmpty();
  }

  // Same vinyl = same barcode and version, or (without barcodes) same title, artists and version
  public boolean isSameVinyl(Record other) {
    if (other == null)
      return false;
    if (!version.equalsIgnoreCase(other.version))
      return false;
    if (!barcode.isEmpty() && !other.barcode.isEmpty())
      return barcode.equals(other.barcode);
    return title.equalsIgnoreCase(other.title)
        && getArtistsText().equalsIgnoreCase(other.getArtistsText());
  }

  public boolean isArchived() {
    return archived;
  }

  public void archive() {
    this.archived = true;
  }

  public void unarchive() {
    this.archived = false;
  }

  public boolean equals(Object object)
  {
    if(object == null || object.getClass() != this.getClass())
      return false;

    Record other = (Record) object;
    return year == other.year && archived == other.archived && this.title.equals(other.title)
        && this.artists.equals(other.artists) && this.barcode.equals(other.barcode)
        && this.version.equals(other.version) && this.genres.equals(other.genres);
  }

  public String toString() {
    return
        "Title = " + title + '\n' +
        "Artist(s) = " + getArtistsText() + '\n' +
        "Year = " + (year == 0 ? "-" : String.valueOf(year)) + '\n' +
        "Version = " + version + '\n' +
        "Genre(s) = " + getGenresText() + '\n' +
        "Barcode = " + barcode;
  }
}
