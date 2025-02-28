package attendance;

import static attendance.view.InputView.readOption;

import attendance.domain.AttendanceManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        AttendanceManager attendanceManager = new AttendanceManager();
        initAttendances(attendanceManager);
        Options options = new Options(attendanceManager, now());
        while (true) {
            LocalDate today = now();
            String option = readOption(today);
            boolean isExitOption = options.isExitOption(option);
            if (isExitOption) {
                break;
            }
            try {
                options.run(option);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void initAttendances(final AttendanceManager attendanceManager) {
        try {
            BufferedReader bufferedReader = AttendancesFileReader.readFile();
            AttendanceFileParser.initAttendances(bufferedReader, attendanceManager);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private static LocalDate now() {
        return LocalDate.of(2024, 12, 16);
    }
}
