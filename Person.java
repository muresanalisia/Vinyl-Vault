public class Person
{
  private String name, email, phone;

  public Person (String name, String email, String phone)
  {
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public boolean equals(Object obj)
  {
    if(obj == null || obj.getClass() != getClass())
      return false;
    Person other = (Person) obj;
    return (other.name.equals(name) && other.email.equals(email)
        && other.phone.equals(phone));
  }

  public String toString()
  {
    return "Name = " + name + '\n' + "Email = " + email + '\n' + "Phone = " + phone;
  }
}
