package attendance.repository;

import attendance.domain.AttendanceManager;
import attendance.exception.AttendanceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class AttendanceFileRepository {
    private final AttendanceManager attendanceManager;
    private final String attendanceFileSrc;
    private final String FILE_DOESNT_EXISTS = "존재하지 않은 파일입니다.";

    public AttendanceFileRepository(String attendanceFileSrc) {
        this.attendanceManager = new AttendanceManager();
        this.attendanceFileSrc = attendanceFileSrc;
    }

    public void saveFromAttendanceFile() {
        File file = new File(attendanceFileSrc);
        loadAttendances(file);
    }

    private void loadAttendances(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            loadAttendanceByLine(bufferedReader);
        } catch (IOException e) {
            throw new AttendanceException(FILE_DOESNT_EXISTS);
        }
    }

    private void loadAttendanceByLine(BufferedReader bufferedReader) throws IOException {
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            loadAttendance(line);
        }
    }

    private void loadAttendance(String line) {
        String[] attendanceLine = line.split(",");
        String nickname = attendanceLine[0];
        LocalDateTime datetime = parsingAttendanceDate(attendanceLine[1]);
        attendanceManager.addAttendance(nickname,datetime);
    }

    private LocalDateTime parsingAttendanceDate(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm");
        return LocalDateTime.parse(datetime, formatter);
    }

    public AttendanceManager getAttendanceManager() {
        return attendanceManager;
    }
}
