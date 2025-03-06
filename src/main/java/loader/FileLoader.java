package loader;

import domain.AttendanceBook;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileLoader {

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final AttendanceBook attendanceBook;

    public FileLoader(AttendanceBook attendanceBook) {
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
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR] 파일을 찾을 수 없습니다: " + filePath);
        } catch (IOException e) {
            System.err.println("[ERROR] 파일을 읽는 도중 오류가 발생했습니다: " + e.getMessage());
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
        String crewName = fields.get(0);
        String dateTimeString = fields.get(1);

        LocalDateTime attendanceDateTime = LocalDateTime.parse(dateTimeString, FORMATTER);
        LocalDate attendanceDate = attendanceDateTime.toLocalDate();
        LocalTime attendanceTime = attendanceDateTime.toLocalTime();

        attendanceBook.addCrew(crewName, attendanceDate, attendanceTime);
    }
}
