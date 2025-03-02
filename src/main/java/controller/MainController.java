package controller;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceState;
import domain.Crew;
import domain.CrewsAttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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
//                    attendanceHistory();
                    break;
                case "4":
//                    absenceHistory();
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

        crewsAttendanceBook.checkIn(crew, DateTimeUtil.getTodayLocalDate(), localTime);

        AttendanceState state = AttendanceState.findStateBy(DateTimeUtil.getTodayLocalDate(), localTime);
        OutputView.printAttendanceCheck(DateTimeUtil.getTodayLocalDate(), time, state);
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

        crewsAttendanceBook.update(crew, LocalDate.of(2024, 12, date), localTime);

        AttendanceState beforeState = AttendanceState.findStateBy(beforeAttendance.getLocalDate(),
                beforeAttendance.getLocalTime());
        AttendanceState afterState = AttendanceState.findStateBy(beforeAttendance.getLocalDate(), localTime);
        OutputView.printAttendanceUpdate(beforeAttendance, beforeState, time, afterState);
    }
}
