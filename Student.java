package util;

import model.Student;
import java.io.*;
import java.util.*;

public class FileUtil {

    // Read students from a file (CSV lines)
    public static List<Student> readStudents(String path) {
        List<Student> list = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return list;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Student s = Student.fromCSV(line);
                if (s != null) list.add(s);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return list;
    }

    // Write students to file (overwrite)
    public static void writeStudents(String path, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (Student s : students) {
                bw.write(s.toCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Demonstrate reading randomly using RandomAccessFile: read a specific line number (1-based)
    public static String readLineRandom(String path, int lineNumber) {
        File f = new File(path);
        if (!f.exists()) return null;
        try (RandomAccessFile raf = new RandomAccessFile(f, "r")) {
            int current = 1;
            String line;
            raf.seek(0);
            while ((line = raf.readLine()) != null) {
                if (current == lineNumber) return line;
                current++;
            }
        } catch (IOException e) {
            System.out.println("Random read error: " + e.getMessage());
        }
        return null;
    }

    // Show file attributes using File
    public static void printFileAttributes(String path) {
        File f = new File(path);
        System.out.println("File: " + path);
        System.out.println("Exists: " + f.exists());
        if (f.exists()) {
            System.out.println("Size (bytes): " + f.length());
            System.out.println("Readable: " + f.canRead());
            System.out.println("Writable: " + f.canWrite());
            System.out.println("Absolute Path: " + f.getAbsolutePath());
        }
    }
}
