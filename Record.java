import java.util.ArrayList;

public class Record {
  private String title, version, genre, barcode;
  private int year;
  private boolean archived;
  private ArrayList<String> artists;

  public Record(String title, ArrayList<String> artists,  int year, String version, String genre, String barcode) {
    this.title = title;
    this.artists = new ArrayList<>(artists);
    this.year = year;
    this.version = version;
    this.genre = genre;
    this.archived = false;
    this.barcode = barcode;
  }

  public String getTitle() {
    return title;
  }
  public void setTitle(String title) {
    this.title = title;
  }

  public ArrayList<String> getArtists() {
    return artists;
  }

  public void setArtists(ArrayList<String> artists) {
    this.artists = new ArrayList<>(artists);
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getBarcode()
  {
    return this.barcode;
  }

  public void setBarcode(String barcode)
  {
    this.barcode = barcode;
  }

  public String getDecade() {
    return (year / 10) * 10 + "s";
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
    return year == other.year && archived == other.archived && this.title.equals(other.title) && this.artists.equals(other.artists)
        && this.barcode.equals(other.barcode) && this.version.equals(other.version) && this.genre.equals(other.genre);
  }

  public String toString() {
    return
        "Title ='" + title + '\n' +
        "Artist(s) ='" + artists + '\n' +
        "Year =" + year + '\n'+
        "Version ='" + version + '\n' +
        "Genre ='" + genre + '\n' +
        "Barcode = " + barcode;
  }
}
