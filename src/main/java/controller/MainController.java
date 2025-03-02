package controller;

import domain.AttendanceBook;
import domain.Crew;
import domain.CrewsAttendanceBook;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import util.AttendanceFileReader;
import view.InputView;

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
//                    attendanceUpdate();
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

        crewsAttendanceBook.checkIn(crew, LocalDate.of(2024, 12, 16), localTime);
    }
}
