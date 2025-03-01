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
import java.util.List;

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

        if ("2".equals(function)) {
            editAttendance();
        }

        if ("3".equals(function)) {
            checkRecords();
        }
    }

    private void recordAttendance() {
        String inputNickname = InputView.readNickname();
        Crew crew = new Crew(new Nickname(inputNickname));
        attendanceBook.validateCrew(crew);

        String inputAttendTime = InputView.readAttendTimeForRecord();
        Attendance attendance = new Attendance(systemDate, LocalTime.parse(inputAttendTime));

        attendanceBook.add(crew, attendance);
        OutputView.printRecordAttendanceResult(attendance);
    }

    private void editAttendance() {
        String inputNickname = InputView.readNicknameForEdit();
        Crew crew = new Crew(new Nickname(inputNickname));
        attendanceBook.validateCrew(crew);

        int inputDay = InputView.readAttendDay();
        LocalDate attendDate = LocalDate.of(systemDate.getYear(), systemDate.getMonthValue(), inputDay);
        LocalTime inputTime = InputView.readAttendTimeForEdit();

        Attendance newAttendance = new Attendance(attendDate, inputTime);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, attendDate);
        attendanceBook.update(crew, oldAttendance, newAttendance);

        OutputView.printEditAttendanceResult(oldAttendance, newAttendance);
    }

    private void checkRecords() {
        String inputNickname = InputView.readNickname();
        Crew crew = new Crew(new Nickname(inputNickname));
        attendanceBook.validateCrew(crew);

        List<Attendance> attendances = attendanceBook.getRecordOfCrew(systemDate, crew);
        OutputView.printAttendanceRecordsUntilYesterday(inputNickname, systemDate, attendances);
    }
}
