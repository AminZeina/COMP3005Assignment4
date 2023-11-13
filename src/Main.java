import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);  // Create a Scanner object


    public static void main(String[] args) {
        // Create a DatabaseInteraction object to connect to and perform operations on the DB
        DatabaseInteraction dbConnection = new DatabaseInteraction();
        boolean running = true;
        String rawInput;

        // Main loop until the user wants to close the program
        while (running) {
            // Prompt user for an operation
            System.out.println("\nWhat operation would you like to perform? Supported Operations:");
            System.out.println("(g)et all students");
            System.out.println("(a)dd a new student");
            System.out.println("(u)pdate an existing student's email");
            System.out.println("(d)elete an existing student");
            System.out.println("(e)xit the application");
            System.out.print("\nEnter an operation: ");

            rawInput = scanner.nextLine();

            // Perform the operation that the user wishes to perform
            switch (rawInput.toLowerCase()) {
                case "g" -> getAllStudent(dbConnection);
                case "a" -> addStudent(dbConnection);
                case "u" -> updateStudentEmail(dbConnection);
                case "d" -> deleteStudent(dbConnection);
                case "e" -> {
                    try {
                        dbConnection.closeConnection();
                    } catch (SQLException e) {
                        System.out.println("Error closing connection:" + e);
                    }
                    System.out.println("Closing program...");
                    running = false;
                }
                // handle invalid input
                default -> System.out.println("Invalid input. Please enter a valid command from the list.");
            }
        }
    }

    // Get and prints all students by calling getAllStudent() in DatabaseInteraction
    private static void getAllStudent(DatabaseInteraction db) {
        System.out.println("\n---- Getting all students ----");
        try {
            ResultSet rs = db.getAllStudents();
            DatabaseInteraction.printResults(rs);
        } catch (SQLException e) {
            // Error occurred -> print error and return to main menu
            System.out.println("Error getting all students: " + e);
        }
        System.out.println("---- End of operation ----");
    }

    // Adds a student by calling addStudent() in DatabaseInteraction
    private static void addStudent(DatabaseInteraction db) {
        System.out.println("\n---- Adding a new student ----");
        System.out.print("Enter the student's first_name, last_name, email, and enrollment_date (comma-separated): ");
        String[] studentInfo = scanner.nextLine().split(",\\s*");
        // validate input, failing gracefully and returning to main menu if invalid
        if (studentInfo.length == 4) {
            try {
                    Date enr_date = Date.valueOf(studentInfo[3]);
                    try {
                        db.addStudent(studentInfo[0].trim(), studentInfo[1].trim(), studentInfo[2].trim(), enr_date);
                    } catch (SQLException e) {
                        // Error occurred -> print error and return to main menu
                        System.out.println("Error adding student: " + e);
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input. enrollment date must be a valid date in the form YYYY-MM-DD. See README for examples");
            }
        } else {
            System.out.println("Invalid input. You must enter all 4 student values, comma separated. See README for examples");
        }
        System.out.println("---- End of operation ----");
    }

    // Updates a student's email by calling updateStudentEmail() in DatabaseInteraction
    private static void updateStudentEmail(DatabaseInteraction db) {
        System.out.println("\n---- Updating a student's email ----");
        System.out.print("Enter the student's student_id and new email (comma-separated): ");
        String[] studentInfo = scanner.nextLine().split(",\\s*");

        // validate input, failing gracefully and returning to main menu if invalid
        if (studentInfo.length == 2) {
            try {
                Integer id = Integer.valueOf(studentInfo[0]);
                try {
                    db.updateStudentEmail(id, studentInfo[1].trim());
                } catch (SQLException e) {
                    // Error occurred -> print error and return to main menu
                    System.out.println("Error updating email: " + e);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input. student_id must be an integer. See README for examples");
            }
        } else {
            System.out.println("Invalid input. You must enter both a student id and new email, comma separated. See README for examples");
        }
        System.out.println("---- End of operation ----");
    }

    // Deletes a student's email by calling deleteStudent() in DatabaseInteraction
    private static void deleteStudent(DatabaseInteraction db) {
        System.out.println("\n---- Deleting a student ----");
        System.out.print("Enter the student's student_id: ");
        String student_id = scanner.nextLine();
        // validate input, failing gracefully and returning to main menu if invalid
        try {
            Integer id = Integer.valueOf(student_id);
            try {
                db.deleteStudent(id);
            } catch (SQLException e) {
                // Error occurred -> print error and return to main menu
                System.out.println("Error deleting student: " + e);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. student_id must be an integer. See README for examples");
        }
        System.out.println("---- End of operation ----");
    }

}
