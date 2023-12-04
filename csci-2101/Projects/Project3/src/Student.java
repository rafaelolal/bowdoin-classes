/** Represents the information of student in the directory */
public class Student {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String email;
    private int sUBox;

    public Student(String firstName, String lastName, String address,
            String phone, String email, int sUBox) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.sUBox = sUBox;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getsUBox() {
        return sUBox;
    }

    /**
     * Standard toString method that formats attributes as specified in the
     * instructions
     */
    public String toString() {
        return String.format("%s %s, %d, %s, %s",
                firstName, lastName, sUBox, phone, email);
    }
}
