package regsystem;
class Course {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }
}


package regsystem;
import java.util.ArrayList;
class Student {
    private int studentID;
    private String name;
    private ArrayList<String> registeredCourses;

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getRegisteredCourses() {
        return registeredCourses;
    }

    public void registerForCourse(String courseCode) {
        registeredCourses.add(courseCode);
    }

    public void dropCourse(String courseCode) {
        registeredCourses.remove(courseCode);
    }
}

package regsystem;
import java.util.ArrayList;
import java.util.Scanner;
public class CourseRegistrationSystem {
private static ArrayList<Course> courses = new ArrayList<>();
private static ArrayList<Student> students = new ArrayList<>();

public static void main(String[] args) {
    initializeCourses();
    initializeStudents();

    Scanner scanner = new Scanner(System.in);

    while (true) {
        System.out.println("\nOptions:");
        System.out.println("1. View available courses");
        System.out.println("2. Register for a course");
        System.out.println("3. Drop a course");
        System.out.println("4. Exit");

        System.out.print("Enter your choice (1-4): ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                displayAvailableCourses();
                break;
            case 2:
                registerForCourse(scanner);
                break;
            case 3:
                dropCourse(scanner);
                break;
            case 4:
                System.out.println("Exiting the Course Registration System. Goodbye!");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 4.");
        }
    }
}

private static void initializeCourses() {
    courses.add(new Course("CSCI101", "Introduction to Computer Science", "Basic concepts of programming", 30, "Mon/Wed 10:00 AM"));
    courses.add(new Course("MATH201", "Calculus I", "Limits, derivatives, and integrals", 25, "Tue/Thu 1:30 PM"));
    // Add more courses as needed
}

private static void initializeStudents() {
    students.add(new Student(1001, "John Doe"));
    students.add(new Student(1002, "Jane Smith"));
    // Add more students as needed
}

private static void displayAvailableCourses() {
    System.out.println("\nAvailable Courses:");
    for (Course course : courses) {
        int remainingCapacity = course.getCapacity() - getCourseRegistrationCount(course.getCourseCode());
        System.out.println(course.getCourseCode() + " - " + course.getTitle() +
                " (Capacity: " + remainingCapacity + "/" + course.getCapacity() + ")");
    }
}

private static void registerForCourse(Scanner scanner) {
    System.out.print("Enter your student ID: ");

    // Input validation for student ID
    while (!scanner.hasNextInt()) {
        System.out.print("Invalid input. Please enter a valid integer for student ID: ");
        scanner.next(); // Consume invalid input
    }

    int studentID = scanner.nextInt();
    Student student = getStudentByID(studentID);

    if (student != null) {
        System.out.print("Enter the course code you want to register for: ");
        String courseCode = scanner.next().toUpperCase();  // Convert to uppercase for case-insensitivity
        Course course = getCourseByCode(courseCode);

        if (course != null) {
            int remainingCapacity = course.getCapacity() - getCourseRegistrationCount(courseCode);

            if (remainingCapacity > 0) {
                student.registerForCourse(courseCode);
                System.out.println("Registration successful for " + course.getTitle());
            } else {
                System.out.println("Course is full. Registration failed.");
            }
        } else {
            System.out.println("Course not found. Registration failed.");
        }
    } else {
        System.out.println("Student not found. Registration failed.");
    }
}

private static void dropCourse(Scanner scanner) {
    System.out.print("Enter your student ID: ");
    int studentID = scanner.nextInt();
    Student student = getStudentByID(studentID);

    if (student != null) {
        System.out.print("Enter the course code you want to drop: ");
        String courseCode = scanner.next();

        if (student.getRegisteredCourses().contains(courseCode)) {
            student.dropCourse(courseCode);
            System.out.println("Dropped the course successfully.");
        } else {
            System.out.println("You are not registered for this course.");
        }
    } else {
        System.out.println("Student not found. Dropping course failed.");
    }
}

private static int getCourseRegistrationCount(String courseCode) {
    int count = 0;
    for (Student student : students) {
        if (student.getRegisteredCourses().contains(courseCode)) {
            count++;
        }
    }
    return count;
}

private static Student getStudentByID(int studentID) {
    for (Student student : students) {
        if (student.getStudentID() == studentID) {
            return student;
        }
    }
    return null;
}

private static Course getCourseByCode(String courseCode) {
    for (Course course : courses) {
        if (course.getCourseCode().equals(courseCode)) {
            return course;
        }
    }
    return null;
}
}
