package domain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileWithAttendanceData {

    private final AttendanceBook attendanceBook;

    public FileWithAttendanceData(AttendanceBook attendanceBook) {
        this.attendanceBook = attendanceBook;
    }

    public void loadFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                List<String> fields = Arrays.stream(line.split(",")).toList();
                parseInfoFromFields(fields);
            }
        } catch (Exception e) {
            System.err.println("파일을 불러오는 데 문제가 발생했습니다. 잠시 후 다시 시도해 주세요.");
        }
    }

    private void parseInfoFromFields(List<String> fields) {
        if (hasValidFieldCount(fields)) {
            addCrew(fields);
        }
    }

    private boolean hasValidFieldCount(List<String> fields) {
        int REQUIRED_FIELDS_COUNT = 2;
        return fields.size() == REQUIRED_FIELDS_COUNT;
    }

    private void addCrew(List<String> fields) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String crewName = fields.get(0);
        String dateTimeString = fields.get(1);

        LocalDateTime attendanceDateTime = LocalDateTime.parse(dateTimeString, formatter);
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();

        attendanceBook.addCrew(crewName, attendanceDate, attendanceTime);
    }
}
