package controller;

import model.AttendanceDate;
import model.AttendanceStatus;
import model.AttendanceStatusEvaluator;
import model.AttendanceTime;
import model.Student;
import model.Students;
import util.AttendanceDateAttendanceTimeFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceModifyController {
    public static void attendanceModify(Students students) {
        String studentName = InputView.getStudentNameUntilValidateToModify(students);

        Student student = students.findStudentByName(studentName);
        AttendanceDate attendanceDate = InputView.getUserAttendanceDateUntilValidate(student);

        AttendanceTime attendanceTimeBeforeModify = student.findAttendanceTimeByAttendanceDate(attendanceDate);

        AttendanceStatus attendanceStatusBeforeModify = AttendanceStatusEvaluator.calculateAttendanceStatus(attendanceDate, attendanceTimeBeforeModify);

        AttendanceTime attendanceTimeToModify = InputView.getUserAttendanceTimeUntilValidateToModify();
        students.modifyAttendanceDateTime(studentName, attendanceDate, attendanceTimeToModify);

        AttendanceStatus attendanceStatusAfterModify = AttendanceStatusEvaluator.calculateAttendanceStatus(attendanceDate, attendanceTimeToModify);

        OutputView.printModifyComplete(attendanceDate, attendanceTimeBeforeModify, attendanceStatusBeforeModify,
                AttendanceDateAttendanceTimeFormatter.createModifyCompleteMessage(attendanceTimeToModify, attendanceStatusAfterModify));
    }
}
