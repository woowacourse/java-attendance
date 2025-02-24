package domain;

import java.time.LocalDate;
import java.util.List;
import util.FileManager;

public class AttendanceSystemFactory {
    private static final String ATTENDANCE_HISTORY_FILE_NAME = "attendances.csv";

    public AttendanceSystem createAttendanceSystem(final LocalDate today) {
        final List<String> attendanceLines = FileManager.readFileLines(ATTENDANCE_HISTORY_FILE_NAME);
        attendanceLines.remove(0);
        return AttendanceSystem.of(attendanceLines, today);
    }
}
