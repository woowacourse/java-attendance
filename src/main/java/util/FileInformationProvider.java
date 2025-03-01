package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileInformationProvider {
    public Map<String, List<LocalDateTime>> loadStudentAttendance() throws IOException {
        List<String> fileAttendanceRecord = attendanceRecordReader();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return fileAttendanceRecord.stream()
                .map(record -> record.split(","))
                .collect(Collectors.groupingBy(
                        (arr -> arr[0]),
                        Collectors.mapping(arr -> LocalDateTime.parse(arr[1], formatter), Collectors.toList())
                ));
    }

    private List<String> attendanceRecordReader() throws IOException {
        String filePath = "src/main/resources/attendances.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("[ERROR] 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }
}
