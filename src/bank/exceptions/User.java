package bank.exceptions;

public class User {
    private String firstName;
    private String lastName;
    private int age;

    public User(String name, String name1, int age) {

    }

    public void Person(String first, String last, int age){
        this.firstName = first;
        this.lastName = last;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }
}