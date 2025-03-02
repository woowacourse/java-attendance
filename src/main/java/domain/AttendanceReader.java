package domain;

import except.AttendanceException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceReader {

    private static final String SPLITTER = ",";
    private final String fileName;
    private final String INVALID_ATTENDANCE_FILE_FORMAT = "유효하지 않은 출석 파일 양식입니다.";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String FILE_DOESNT_EXIST = "파일이 존재하지 않습니다.";

    public AttendanceReader(String fileName) {
        this.fileName = fileName;
    }

    public List<AttendanceReadUnit> readAttendances() {
        File file = new File(getClass().getResource(fileName).getFile());
        if (!file.exists()) {
            throw new AttendanceException(FILE_DOESNT_EXIST);
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            return readAttendances(bufferedReader);
        } catch (IOException e) {
            throw new AttendanceException(INVALID_ATTENDANCE_FILE_FORMAT);
        }
    }

    private List<AttendanceReadUnit> readAttendances(BufferedReader bufferedReader) throws IOException {
        String line;
        bufferedReader.readLine();
        List<AttendanceReadUnit> attendanceReadUnits = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            AttendanceReadUnit attendanceReadUnit = parseAttendanceReadUnit(line);
            attendanceReadUnits.add(attendanceReadUnit);
        }
        return attendanceReadUnits;
    }

    private AttendanceReadUnit parseAttendanceReadUnit(String line) {
        String[] lines = line.split(SPLITTER);
        if (lines.length != 2) {
            throw new AttendanceException(INVALID_ATTENDANCE_FILE_FORMAT);
        }
        CrewName crewName = new CrewName(lines[0]);
        LocalDateTime dateTime = LocalDateTime.parse(lines[1], dateTimeFormatter);
        AttendanceDate attendanceDate = new AttendanceDate(dateTime.toLocalDate());
        AttendanceTime attendanceTime = new AttendanceTime(dateTime.toLocalTime(), attendanceDate);
        return new AttendanceReadUnit(crewName, attendanceDate, attendanceTime);
    }
}
