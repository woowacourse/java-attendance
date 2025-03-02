package controller;

import java.util.Map;
import model.AttendanceStatus;
import model.Student;
import model.Students;
import view.OutputView;

public class DismissalSubjectCheckController {
    public static void dismissalSubjectCheck(Students students) {
        OutputView.displayAtRiskStudent();
        for (Student student : students.getStudents()) {
            Map<AttendanceStatus, Integer> attendanceCount = student.calculateStudentAttendanceResult();
            OutputView.printDismissalSubject(attendanceCount, student.getName());
        }
    }
}
