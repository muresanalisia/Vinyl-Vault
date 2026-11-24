import java.util.ArrayList;

public class PersonList {
  private ArrayList<Person> persons;

  public PersonList() {
    this.persons = new ArrayList<>();
  }

  public void addPerson(Person person) {
    if (person != null) {
      persons.add(person);
    }
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

  public ArrayList<Person> getAllPersons() { // return a copy or the og?
    return new ArrayList<>(persons);
  }

  public int size() {
    return persons.size();
  }
}
