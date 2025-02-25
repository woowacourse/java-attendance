package attendance.domain;

import attendance.exception.AttendanceArgumentException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceReader {
    
    private final String attendanceFileSrc;
    private final String WRONG_FILE_FORMAT = "유효하지 않은 파일 포맷입니다.";
    private final String FILE_DOESNT_EXISTS = "존재하지 않은 파일입니다.";
    private final String DEFAULT_FILE_NAME = "/attendances.csv";

    public AttendanceReader() {
        this.attendanceFileSrc = DEFAULT_FILE_NAME;
    }

    public AttendanceReader(String attendanceFileSrc) {
        this.attendanceFileSrc = attendanceFileSrc;
    }

    public List<AttendanceRequest> loadAttendanceLinesFromAttendanceFile() {
        File file = new File(getClass().getResource(attendanceFileSrc).getFile());
        return loadAttendanceLines(file);
    }

    private List<AttendanceRequest> loadAttendanceLines(File file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            return loadAttendancesByLine(bufferedReader);
        } catch (IOException e) {
            throw new AttendanceArgumentException(FILE_DOESNT_EXISTS);
        } catch (AttendanceArgumentException e) {
            throw new AttendanceArgumentException(WRONG_FILE_FORMAT);
        }
    }

    private List<AttendanceRequest> loadAttendancesByLine(BufferedReader bufferedReader) throws IOException {
        List<AttendanceRequest> attendanceRequets = new ArrayList<>();
        String line = bufferedReader.readLine();
        while ((line = bufferedReader.readLine()) != null) {
            String[] lines = line.split(",");
            validateLocalDateTime(lines[1]);
            LocalDateTime attendanceDateTime = DateTimeFormatterWrapper.parsingAttendanceDateTime(lines[1]);
            attendanceRequets.add(new AttendanceRequest(CrewName.from(lines[0]), attendanceDateTime));
        }
        return attendanceRequets;
    }

    private void validateLocalDateTime(String dateTime) {
        DateTimeFormatterWrapper.parsingAttendanceDateTime(dateTime);
    }
}
