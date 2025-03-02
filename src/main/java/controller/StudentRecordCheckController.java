package controller;

import model.Student;
import model.Students;
import view.InputView;
import view.OutputView;

public class StudentRecordCheckController {
    public static void studentRecordCheck(Students students) {
        String studentName = InputView.getStudentNameUntilValidateToAttendance(students);
        Student wantToCheckStudent = students.findStudentByName(studentName);
        OutputView.printRecordCheck(wantToCheckStudent.getStudentAttendanceHistory().getAttendanceHistory(), studentName);
        OutputView.printResult(wantToCheckStudent.calculateStudentAttendanceResult());
    }
}
