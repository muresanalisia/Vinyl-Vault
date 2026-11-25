public class Person
{
  private String name, email, phone;

  public Person (String name, String email, String phone)
  {
    this.name = checkName(name);
    this.email = checkEmail(email);
    this.phone = checkPhone(phone);
  }

  private static String checkName(String name)
  {
    if (name == null || name.isBlank())
      throw new IllegalArgumentException("Name is required");
    return name.trim();
  }

  private static String checkEmail(String email)
  {
    if (email == null || !email.trim().matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+"))
      throw new IllegalArgumentException("Invalid email format.");
    return email.trim();
  }

  private static String checkPhone(String phone)
  {
    if (phone == null)
      throw new IllegalArgumentException("Phone number is required");
    String cleaned = phone.replaceAll("\\s+", "");
    if (!cleaned.matches("\\+?[0-9]{8,15}"))
      throw new IllegalArgumentException("Invalid phone number format.");
    return cleaned;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = checkName(name);
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = checkEmail(email);
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = checkPhone(phone);
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
