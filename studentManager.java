package service;

import model.Student;
import util.FileUtil;
import java.util.*;

public class StudentManager {

    private List<Student> students;
    private Map<Integer, Student> studentMap; 

    public StudentManager() {
        students = new ArrayList<>();
        studentMap = new HashMap<>();
    }

    public void loadFromFile(String path) {
        students = FileUtil.readStudents(path);
        studentMap.clear();
        for (Student s : students) studentMap.put(s.getRollNo(), s);
        System.out.println("Loaded students from file (" + students.size() + " records).");
    }

    public void saveToFile(String path) {
        FileUtil.writeStudents(path, students);
        System.out.println("Saved " + students.size() + " students to file.");
    }

    public void addStudent(Student s) {
        if (studentMap.containsKey(s.getRollNo())) {
            System.out.println("Duplicate roll number. Student not added.");
            return;
        }
        students.add(s);
        studentMap.put(s.getRollNo(), s);
        System.out.println("Student added.");
    }

    public void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No students.");
            return;
        }
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            Student s = it.next();
            System.out.println("---------------------");
            System.out.println(s);
        }
    }

    public List<Student> searchByName(String name) {
        List<Student> res = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().equalsIgnoreCase(name)) res.add(s);
        }
        return res;
    }

    public boolean deleteByName(String name) {
        Iterator<Student> it = students.iterator();
        boolean removed = false;
        while (it.hasNext()) {
            Student s = it.next();
            if (s.getName().equalsIgnoreCase(name)) {
                it.remove();
                studentMap.remove(s.getRollNo());
                removed = true;
            }
        }
        return removed;
    }

    public void sortByMarksDescending() {
        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return Double.compare(s2.getMarks(), s1.getMarks());
            }
        });
    }

    public void sortByName() {
        Collections.sort(students, Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));
    }

    public void printFileAttributes(String path) {
        FileUtil.printFileAttributes(path);
    }

    public String readRandomLine(String path, int lineNo) {
        return FileUtil.readLineRandom(path, lineNo);
    }
}
