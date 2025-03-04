package controller;

import model.AttendanceDate;
import model.AttendanceStatus;
import model.AttendanceStatusEvaluator;
import model.AttendanceTime;
import model.Students;
import view.InputView;
import view.OutputView;

public class AttendanceCheckController {
    public static void attendanceCheck(Students students, AttendanceDate today) {
        try {
            today.validateHoliday();
            String studentName = InputView.getStudentNameUntilValidateToAttendance(students);
            AttendanceTime attendanceTime = InputView.getUserAttendanceTimeUntilValidateToAttendance();
            students.validateAlreadyExistAttendanceDate(studentName, today);
            students.addAttendanceDateTime(studentName, today, attendanceTime);
            AttendanceStatus studentAttendanceStatus = AttendanceStatusEvaluator.calculateAttendanceStatus(today, attendanceTime);
            OutputView.printAttendanceResult(today, attendanceTime, studentAttendanceStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
