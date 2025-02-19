package attendance.repository;

import attendance.domain.AttendanceManager;
import attendance.exception.AttendanceArgumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileRepository {
    private final String attendanceFileSrc;
    private final String FILE_DOESNT_EXISTS = "존재하지 않은 파일입니다.";

    public AttendanceFileRepository(String attendanceFileSrc) {
        this.attendanceFileSrc = attendanceFileSrc;
    }

    public List<String> loadAttendanceLinesFromAttendanceFile() {
        File file = new File(getClass().getResource(attendanceFileSrc).getFile());
        return loadAttendanceLines(file);
    }

    private List<String> loadAttendanceLines(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return loadAttendancesByLine(bufferedReader);
        } catch (IOException e) {
            throw new AttendanceArgumentException(FILE_DOESNT_EXISTS);
        }
    }

    private List<String> loadAttendancesByLine(BufferedReader bufferedReader) throws IOException {
        List<String> attendanceLines = new ArrayList<>();
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            attendanceLines.add(line);
        }
        return attendanceLines;
    }
}
