
import java.io.*;
import java.util.*;

class Student {
    String rollNo;
    String name;
    int marks;

    Student(String rollNo, String name, int marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
    }
}

public class StudentRankingSystem {

    static final String FILE_NAME = "students.txt";

    // Main method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        // Load existing records from file
        loadStudents(students);

        int choice = 0;

        do {
            System.out.println("\n================================");
            System.out.println("       STUDENT RANKING SYSTEM");
            System.out.println("================================");
            System.out.println("1. Add Student");
            System.out.println("2. Display Rank List");
            System.out.println("3. Search Student");
            System.out.println("4. Show Topper");
            System.out.println("5. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid menu choice!");
                continue;
            }

            switch (choice) {

                case 1:
                    addStudent(sc, students);
                    break;

                case 2:
                    displayRankList(students);
                    break;

                case 3:
                    searchStudent(sc, students);
                    break;

                case 4:
                    showTopper(students);
                    break;

                case 5:
                    saveStudents(students);
                    System.out.println("Data saved successfully.");
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 5);

        sc.close();
    }

    // Method 1: Add Student
    static void addStudent(Scanner sc,
                           ArrayList<Student> students) {

        System.out.print("Enter Roll Number: ");
        String roll = sc.nextLine().trim();

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();

        // Validate input
        if (roll.isEmpty() || name.isEmpty()) {
            System.out.println("Roll number and name cannot be empty.");
            return;
        }

        // Check duplicate roll number
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).rollNo.equalsIgnoreCase(roll)) {
                System.out.println("Roll number already exists!");
                return;
            }
        }

        System.out.print("Enter Marks (0-100): ");

        int marks;

        try {
            marks = Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid marks. Enter a valid number.");
            return;
        }

        // Marks validation
        if (marks < 0 || marks > 100) {
            System.out.println(
                "Invalid marks. Marks must be between 0 and 100."
            );
            return;
        }

        Student s = new Student(roll, name, marks);

        students.add(s);

        // Save newly added record
        saveStudents(students);

        System.out.println("Student added. Roll: "
                + roll + " | " + name + " | " + marks);
    }

    // Method 2: Display Rank List
    static void displayRankList(ArrayList<Student> students) {

        if (students.size() == 0) {
            System.out.println("No student records found.");
            return;
        }

        // Convert ArrayList into array
        Student[] arr = new Student[students.size()];

        for (int i = 0; i < students.size(); i++) {
            arr[i] = students.get(i);
        }

        // Apply Merge Sort
        mergeSort(arr, 0, arr.length - 1);

        System.out.println("\n========== STUDENT RANK LIST ==========");

        System.out.printf("%-8s %-12s %-20s %-8s%n",
                "Rank", "Roll No", "Name", "Marks");

        System.out.println("---------------------------------------");

        for (int i = 0; i < arr.length; i++) {

            System.out.printf("%-8d %-12s %-20s %-8d%n",
                    i + 1,
                    arr[i].rollNo,
                    arr[i].name,
                    arr[i].marks);
        }

        System.out.println("=======================================");
    }

    // Method 3: Merge Sort
    static void mergeSort(Student[] arr, int left, int right) {

        if (left < right) {

            int mid = left + (right - left) / 2;

            mergeSort(arr, left, mid);

            mergeSort(arr, mid + 1, right);

            merge(arr, left, mid, right);
        }
    }

    // Merge two sorted parts
    static void merge(Student[] arr,
                      int left, int mid, int right) {

        int n1 = mid - left + 1;
        int n2 = right - mid;

        Student[] L = new Student[n1];
        Student[] R = new Student[n2];

        for (int i = 0; i < n1; i++) {
            L[i] = arr[left + i];
        }

        for (int j = 0; j < n2; j++) {
            R[j] = arr[mid + 1 + j];
        }

        int i = 0;
        int j = 0;
        int k = left;

        // Descending order of marks
        while (i < n1 && j < n2) {

            if (L[i].marks >= R[j].marks) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }

            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Method 4: Search Student
    static void searchStudent(Scanner sc,
                              ArrayList<Student> students) {

        if (students.size() == 0) {
            System.out.println("No student records found.");
            return;
        }

        System.out.print("Enter Roll Number or Student Name: ");

        String key = sc.nextLine().trim();

        boolean found = false;

        // Linear Search
        for (int i = 0; i < students.size(); i++) {

            Student s = students.get(i);

            if (s.rollNo.equalsIgnoreCase(key) ||
                s.name.equalsIgnoreCase(key)) {

                System.out.println("\nStudent Found!");
                System.out.println("Roll No: " + s.rollNo);
                System.out.println("Name: " + s.name);
                System.out.println("Marks: " + s.marks);

                found = true;
            }
        }

        if (!found) {
            System.out.println("Student not found!");
        }
    }

    // Method 5: Show Topper
    static void showTopper(ArrayList<Student> students) {

        if (students.size() == 0) {
            System.out.println("No student records found.");
            return;
        }

        Student topper = students.get(0);

        for (int i = 1; i < students.size(); i++) {

            if (students.get(i).marks > topper.marks) {
                topper = students.get(i);
            }
        }

        System.out.println("\n========== CLASS TOPPER ==========");
        System.out.println("Roll No: " + topper.rollNo);
        System.out.println("Name: " + topper.name);
        System.out.println("Marks: " + topper.marks);
        System.out.println("==================================");
    }

    // Method 6: Load Students from File
    static void loadStudents(ArrayList<Student> students) {

        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No existing file. Starting new records.");
            saveStudents(students);
            return;
        }

        try (BufferedReader br =
                new BufferedReader(new FileReader(file))) {

            String firstLine = br.readLine();

            if (firstLine == null) {
                System.out.println("Student file is empty.");
                return;
            }

            int count = Integer.parseInt(firstLine.trim());

            for (int i = 0; i < count; i++) {

                String line = br.readLine();

                if (line == null) {
                    break;
                }

                String[] data = line.split("\\|", -1);

                if (data.length != 3) {
                    continue;
                }

                String roll = data[0].trim();
                String name = data[1].trim();

                try {
                    int marks = Integer.parseInt(data[2].trim());

                    if (!roll.isEmpty() &&
                        !name.isEmpty() &&
                        marks >= 0 && marks <= 100) {

                        students.add(
                            new Student(roll, name, marks)
                        );
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Skipping invalid record.");
                }
            }

            System.out.println(students.size()
                    + " student record(s) loaded.");

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading student file: "
                    + e.getMessage());
        }
    }

    // Method 7: Save Students to File
    static void saveStudents(ArrayList<Student> students) {

        try (PrintWriter pw =
                new PrintWriter(new FileWriter(FILE_NAME))) {

            // First line stores total number of students
            pw.println(students.size());

            // Store student records
            for (int i = 0; i < students.size(); i++) {

                Student s = students.get(i);

                pw.println(s.rollNo + "|" +
                           s.name + "|" +
                           s.marks);
            }

        } catch (IOException e) {
            System.out.println("Error saving student records: "
                    + e.getMessage());
        }
    }
}
