package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import model.Student;
import model.StudentAttendanceHistory;
import model.Students;
import util.FileInput;

public class StudentLoaderController {
    public static Students readFileAndMakeStudents() {
        List<Student> students = new ArrayList<>();
        Map<String, StudentAttendanceHistory> studentInformationInFile = FileInput.readFileAndMakeStudentInformation();

        for (String studentName : studentInformationInFile.keySet()) {
            students.add(new Student(studentName, studentInformationInFile.get(studentName)));
        }

        return new Students(students);
    }
}
