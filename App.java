package main;

import service.StudentManager;
import model.Student;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String DATA_FILE = "students.txt";
    private static StudentManager manager = new StudentManager();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Load existing records
        manager.loadFromFile(DATA_FILE);
        manager.printFileAttributes(DATA_FILE);
        String randomLine = manager.readRandomLine(DATA_FILE, 2); // example
        if (randomLine != null) {
            System.out.println("Random line 2: " + randomLine);
        }

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudent(); break;
                case "2": manager.viewAll(); break;
                case "3": searchByName(); break;
                case "4": deleteByName(); break;
                case "5": sortByMarks(); break;
                case "6": saveAndExit(); running = false; break;
                default: System.out.println("Invalid choice");
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== Capstone Student Menu =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search by Name");
        System.out.println("4. Delete by Name");
        System.out.println("5. Sort by Marks");
        System.out.println("6. Save and Exit");
        System.out.print("Enter choice: ");
    }

    private static void addStudent() {
        try {
            System.out.print("Enter Roll No: ");
            int roll = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Enter Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter Email: ");
            String email = sc.nextLine().trim();
            System.out.print("Enter Course: ");
            String course = sc.nextLine().trim();
            System.out.print("Enter Marks: ");
            double marks = Double.parseDouble(sc.nextLine().trim());

            Student s = new Student(roll, name, email, course, marks);
            manager.addStudent(s);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number input. Try again.");
        }
    }

    private static void searchByName() {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().trim();
        List<Student> res = manager.searchByName(name);
        if (res.isEmpty()) System.out.println("No records found.");
        else {
            for (Student s : res) {
                System.out.println("-----");
                System.out.println(s);
            }
        }
    }

    private static void deleteByName() {
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine().trim();
        boolean removed = manager.deleteByName(name);
        if (removed) System.out.println("Records removed.");
        else System.out.println("No matching records.");
    }

    private static void sortByMarks() {
        manager.sortByMarksDescending();
        System.out.println("Sorted Student List by Marks:");
        manager.viewAll();
    }

    private static void saveAndExit() {
        manager.saveToFile(DATA_FILE);
        System.out.println("Exiting. Goodbye.");
    }
}
