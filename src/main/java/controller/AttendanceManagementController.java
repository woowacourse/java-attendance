package controller;

import java.time.LocalDate;
import model.AttendanceDate;
import model.Students;
import view.InputView;

public class AttendanceManagementController {
    private final TodayDateGenerator todayDateGenerator;

    public AttendanceManagementController(final TodayDateGenerator todayDateGenerator) {
        this.todayDateGenerator = todayDateGenerator;
    }

    public void attendanceManagementStart() {
        Students students = StudentLoaderController.readFileAndMakeStudents();
        AttendanceDate attendanceStartDate = new AttendanceDate(LocalDate.of(2024, 12, 1));
        AttendanceDate today = todayDateGenerator.generate();
        students.updateMissingAttendanceRecords(attendanceStartDate, today);

        String userInput = "";

        while (!userInput.equals(MenuOption.QUIT.getOption())){
            userInput = InputView.getUserWantMenuUntilValidate();
            if (userInput.equals(MenuOption.ATTENDANCE_CHECK.getOption())) {
                AttendanceCheckController.attendanceCheck(students, today);
            }
            if (userInput.equals(MenuOption.ATTENDANCE_MODIFY.getOption())) {
            }
            if (userInput.equals(MenuOption.STUDENT_RECORD_CHECK.getOption())) {
            }
            if (userInput.equals(MenuOption.DISMISSAL_SUBJECT_CHECK.getOption())) {
            }
        }
    }
}
