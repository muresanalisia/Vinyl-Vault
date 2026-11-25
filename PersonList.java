import java.util.ArrayList;

public class PersonList {
  private ArrayList<Person> persons;

  public PersonList() {
    this.persons = new ArrayList<>();
  }
  
  public void addPerson(Person person) {
    if (person == null)
      throw new IllegalArgumentException("Person cannot be null");
    if (persons.contains(person))
      throw new IllegalArgumentException("This person already exists");
    persons.add(person);
  }

  public void removePerson(Person person) {
    persons.remove(person);
  }

  public Person getPerson(int index) {
    return persons.get(index);
  }

  public Person getPersonByName(String name) {
    for (Person person : persons) {
      if (person.getName().equals(name)) {
        return person;
      }
    }
    return null;
  }

  public Person find(Person person) {
    int index = persons.indexOf(person);
    if (index == -1 ){
      return null;
    }
    return person.get(index);
  }

  public Person getPersonByEmail(String email) {
    for (Person person : persons) {
      if (person.getEmail().equalsIgnoreCase(email)) {
        return person;
      }
    }
    return null;
  }

  public ArrayList<Person> getAllPersons() {
    return new ArrayList<>(persons);
  }

  public int size() {
    return persons.size();
  }
}
