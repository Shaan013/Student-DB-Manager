// 
// Package: DataBase
// Author: [Your Name]
//
// Description:
// This Java program manages student data through a console-based interface.
// It allows users to add, search, display, and delete student records.
// The data is stored in a text file "Student_data.txt" in a formatted structure.
//
// Classes and Methods:
// 1. `StudentDBM` Class:
//    - This class provides methods to manage student data, specifically handling file operations
//      for adding, reading, searching, and deleting records in "Student_data.txt".
//    - Methods:
//      - `addStudent()`: Prompts the user to enter student details and appends them to the file.
//      - `readerFile()`: Reads and displays all student records formatted in columns.
//      - `searchStudent()`: Searches for a student by enrollment number and displays their details.
//      - `DeleteLine()`: Deletes a specific student record by enrollment number.
//      - `replece()`: Helper method to rename a temporary file back to "Student_data.txt".
//  
// 2. `StudentManagment` Class (Main class):
//    - This class contains the main method to run the program.
//    - It provides a menu-driven console interface allowing the user to choose options:
//        - Add Student, Display All Students, Search by Enrollment Number, Delete Student, or Exit.
//
// Usage Notes:
// - Run the program, then follow the prompts to manage student records.
// - The records are stored in "Student_data.txt", and each record is stored in a single line
//   separated by "$" symbols for easy parsing.
// - Temporary file handling ensures that the delete function works correctly without data loss.
//
// Note:
// The program uses `BufferedReader` and `BufferedWriter` for efficient file handling, and
// a temporary file for safe deletion of records to prevent accidental data loss during delete operations.

import java.io.*;

import java.util.Scanner;

class StudentDBM {
    final String FileName = "Student_data.txt";

    void addStudent() {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(FileName, true));
            Scanner sc = new Scanner(System.in);

            System.out.print("\n enter your enrollment number :-");
            String En = sc.nextLine();

            System.out.print("\n enter your name :-");
            String name = sc.nextLine();

            System.out.print(" \n enter your address :-");
            String ads = sc.nextLine();

            System.out.print("\n enter your mobile number:-");
            String Mo = sc.nextLine();

            wr.write(En + "$" + name + "$" + ads + "$" + Mo);
            wr.newLine();
            wr.close();
            // readerFile();
        } catch (IOException e) {
            System.out.println("! error !");
        }

    }

    void readerFile() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(FileName));
            String a;

            System.out.printf(" %-10S ", "number");
            System.out.printf(" %-30S ", "name");
            System.out.printf(" %-20S ", "address");
            System.out.printf(" %-10S ", "mobile");
            System.out.println();
            for (int i = 0; i < 30; i++)
                System.out.print(" - ");
            System.out.println();
            while ((a = r.readLine()) != null) {
                String[] part = a.split("\\$");

                System.out.printf(" %-10S ", part[0]);
                System.out.printf(" %-30S ", part[1]);
                System.out.printf(" %-20S ", part[2]);
                System.out.printf(" %-10S ", part[3]);

                System.out.println();
            }
        } catch (IOException e) {

            System.out.println("! error !");
        }
    }

    void searchStudent() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(FileName));
            Scanner sc = new Scanner(System.in);
            String a;
            boolean result = false;
            System.out.print(" enter en number :-");
            String search = sc.nextLine();

            while ((a = r.readLine()) != null) {
                String[] part = a.split("\\$");
                if (part[0].equals(search)) {

                    System.out.printf(" %-20S %S %S", "enrollment number", ":-", part[0]);
                    System.out.println();
                    System.out.printf(" %-20S %S %S", "full name", ":-", part[1]);
                    System.out.println();
                    System.out.printf(" %-20S %S %S", "address of student", ":-", part[2]);
                    System.out.println();
                    System.out.printf(" %-20S %S %S", "mobile number ", ":-", part[3]);

                    result = true;
                    break;
                }
            }
            if (result == false)
                System.out.println(" record not found!");
        } catch (IOException e) {

            System.out.println("! error !");
        }
    }

    void DeleteLine() {
        try {
            boolean result = false;

            {
                Scanner sc = new Scanner(System.in);
                BufferedReader read = new BufferedReader(new FileReader(FileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter("tem.txt"));
                String line;

                System.out.print("\n enter en number of student that you want to delete:-");
                String search = sc.nextLine();

                while ((line = read.readLine()) != null) {
                    String[] part = line.split("\\$");
                    if (!(part[0].equals(search))) {
                        writer.write(line);
                        writer.newLine();
                        //2 System.out.println(line);
                    }
                    result = (part[0].equals(search)) ? true : false;
                }
                read.close();
                writer.close();
            }

            File temFile = new File("tem.txt");
            {
                File oldFile = new File(FileName);

                if (oldFile.delete())
                    System.out.println("it is worked.");
                // oldFile.close();
                // replece();
            }
            File dem = new File(FileName);
            temFile.renameTo(dem);

            if (result == false)
                System.out.println("record not found.");
        } catch (IOException e) {
            System.out.println("error");
            e.getStackTrace();
        }
    }

    void replece() {
        File temFile = new File("tem.txt");
        File dem = new File(FileName);
        temFile.renameTo(dem);
    }
}

public class StudentManagment {
    public static void main(String[] args) {
        StudentDBM a = new StudentDBM();
        Scanner sc = new Scanner(System.in);
        int choise;
        do {
            System.out.print(
                    "\n 1. Addstudent \n 2. Show All Student Data .\n 3.search student (from enrollment number).\n 4. Dekete Student Data. \n 0.Exit.\n Enter your choise:-");
            choise = sc.nextInt();
            switch (choise) {
                case 1:
                    a.addStudent();
                    break;

                case 2:
                    a.readerFile();
                    break;

                case 3:
                    a.searchStudent();
                    break;

                case 4:
                    a.DeleteLine();
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Enter velid choise.");
                    break;
            }

        } while (choise != 0);
    }
}
