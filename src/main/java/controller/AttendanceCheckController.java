package controller;

import model.AttendanceDate;
import model.AttendanceStatus;
import model.AttendanceStatusEvaluator;
import model.AttendanceTime;
import model.Students;
import util.AttendanceDateAttendanceTimeFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceCheckController {
    public static void attendanceCheck(Students students, AttendanceDate today) {
        try {
            if (today.isHoliday()) {
                throw new IllegalArgumentException(AttendanceDateAttendanceTimeFormatter.createNonSchoolDayMessage(today));
            }
            String studentName = InputView.getStudentNameUntilValidateToAttendance(students);
            AttendanceTime attendanceTime = InputView.getUserAttendanceTimeUntilValidateToAttendance();
            students.findStudentByName(studentName).isAlreadyExistAttendanceDate(today);
            students.findStudentByName(studentName).addAttendanceDateTime(today, attendanceTime);
            AttendanceStatus studentAttendanceStatus = AttendanceStatusEvaluator.calculateAttendanceStatus(today, attendanceTime);
            OutputView.printAttendanceResult(today, attendanceTime, studentAttendanceStatus);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
