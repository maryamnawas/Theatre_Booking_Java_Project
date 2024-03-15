public class Person {
    private String name, surname, email;

    // Constructor that takes in the name, surname, and email as input variables
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
    //Getters for the instance variable
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getEmail() {
        return email;
    }
}

