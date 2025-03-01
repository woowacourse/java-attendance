package attendance.controller;

import attendance.Initializer;
import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crew;
import attendance.domain.Nickname;
import attendance.view.InputView;
import attendance.view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceController {

    private final AttendanceBook attendanceBook;
    private final LocalDate systemDate;

    public AttendanceController(Initializer initializer) {
        this.attendanceBook = initializer.initAttendanceBook();
        this.systemDate = initializer.initSystemDate();
    }

    public void run() {
        while (true) {
            String function = InputView.readFunction(systemDate);
            if ("Q".equals(function)) {
                break;
            }

            execute(function);
        }
    }

    private void execute(String function) {
        if ("1".equals(function)) {
            recordAttendance();
        }
    }

    private void recordAttendance() {
        String inputNickname = InputView.readNickname();
        Crew crew = new Crew(new Nickname(inputNickname));
        attendanceBook.validateCrew(crew);

        String inputAttendTime = InputView.readAttendTime();
        Attendance attendance = new Attendance(systemDate, LocalTime.parse(inputAttendTime));

        attendanceBook.add(crew, attendance);
        OutputView.printAttendanceResult(attendance);
    }
}
