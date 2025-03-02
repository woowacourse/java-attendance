package controller;

import domain.AttendanceBook;
import domain.CrewsAttendanceBook;
import java.util.Map;
import util.AttendanceFileReader;
import view.InputView;

public class MainController {
    public void run() {
        String ATTENDANCE_FILE_PATH = "src/test/resources/attendances_test.csv";

        Map<String, AttendanceBook> initialAttendances = AttendanceFileReader.read(ATTENDANCE_FILE_PATH);
        CrewsAttendanceBook crewsAttendanceBook = new CrewsAttendanceBook(initialAttendances);

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
    }
}
