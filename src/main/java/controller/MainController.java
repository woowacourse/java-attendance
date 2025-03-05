package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceStateCount;
import domain.Crew;
import domain.CrewsAttendanceBook;
import domain.PenaltyBook;
import domain.PenaltyType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import util.AttendanceFileReader;
import util.DateTimeUtil;
import view.InputView;
import view.OutputView;

public class MainController {
    String ATTENDANCE_FILE_PATH = "src/test/resources/attendances_test.csv";

    Map<Crew, AttendanceBook> initialAttendances = AttendanceFileReader.read(ATTENDANCE_FILE_PATH);
    CrewsAttendanceBook crewsAttendanceBook = new CrewsAttendanceBook(initialAttendances);


    public void run() {
        String feature;
        do {
            feature = InputView.inputFeatureNumber();
            switch (feature) {
                case "1":
                    attendanceCheck();
                    break;
                case "2":
                    attendanceUpdate();
                    break;
                case "3":
                    attendanceHistory();
                    break;
                case "4":
                    absenceHistory();
                    break;
            }
        } while (!"Q".equals(feature));
    }

    private void attendanceCheck() {
        String name = InputView.inputNickname();
        Crew crew = crewsAttendanceBook.getCrewByName(name);

        String time = InputView.inputTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, formatter);

        Attendance attendance = crewsAttendanceBook.checkIn(crew, DateTimeUtil.getTodayLocalDate(), localTime);

        OutputView.printAttendanceCheck(attendance);
    }

    private void attendanceUpdate() {
        String name = InputView.inputUpdateNickname();
        Crew crew = crewsAttendanceBook.getCrewByName(name);

        int date = Integer.parseInt(InputView.inputDate());
        String time = InputView.inputTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, formatter);

        AttendanceBook attendanceBook = crewsAttendanceBook.getAttendanceBook(crew);
        Attendance beforeAttendance = attendanceBook.getBeforeAttendance(LocalDate.of(2024, 12, date));

        Attendance afterAttendance = crewsAttendanceBook.update(crew, LocalDate.of(2024, 12, date), localTime);

        OutputView.printAttendanceUpdate(beforeAttendance, afterAttendance);
    }

    private void attendanceHistory() {
        String name = InputView.inputNickname();
        Crew crew = crewsAttendanceBook.getCrewByName(name);

        AttendanceBook attendanceBook = crewsAttendanceBook.getAttendanceBook(crew);
        List<Attendance> attendanceBookHistory = attendanceBook.getAttendanceBookHistory();

        OutputView.printAttendanceRecordHistory(attendanceBookHistory);

        AttendanceStateCount attendanceStateCount = attendanceBook.calculateState();
        PenaltyType penaltyType = attendanceBook.calculatePenaltyType(attendanceStateCount);

        OutputView.printAttendancePenaltyHistory(attendanceStateCount, penaltyType);
    }

    private void absenceHistory() {
        Set<PenaltyBook> penaltyBooks = crewsAttendanceBook.calculatePenaltyBooks();

        OutputView.printAbsenceHistory(penaltyBooks);
    }
}
