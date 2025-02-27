package reader;

import java.io.BufferedReader;
import java.io.FileReader;

public class AttendanceFileReader {

    public static final String DEFAULT_ATTENDANCE_DATA_PATH = "src/main/resources/attendances.csv"; // TODO 옮기기
    private static final int HEADER = 1;

    public RawAttendances read(String filePath) throws FileReadException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return RawAttendances.from(
                    reader.lines()
                            .skip(HEADER)
                            .map(RawAttendance::from)
                            .toList());
        } catch (Exception e) {
            throw new FileReadException("출석 데이터를 읽어오는데 실패했습니다: " + filePath);
        }
    }
}
