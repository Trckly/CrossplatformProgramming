import java.util.Objects;

public class Student {
    private String firstName;
    private String lastName;
    private String dormitory;
    private int roomNumber;
    private double fee;
    private int age;
    private boolean isDiscounted;

    public Student(String firstName, String lastName, String dormitory, int roomNumber, double fee, int age, boolean isDiscounted) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dormitory = dormitory;
        this.roomNumber = roomNumber;
        this.fee = fee;
        this.age = age;
        this.isDiscounted = isDiscounted;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDormitory() {
        return dormitory;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public double getFee() {
        return fee;
    }

    public int getAge() {
        return age;
    }

    public boolean isDiscounted() {
        return isDiscounted;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", dormitory: " + dormitory + ", room: " + roomNumber + ", fee: " + fee + ", age: " + age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return roomNumber == student.roomNumber && Double.compare(student.fee, fee) == 0 &&
                age == student.age && isDiscounted == student.isDiscounted && Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName) && Objects.equals(dormitory, student.dormitory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, dormitory, roomNumber, fee, age, isDiscounted);
    }
}
