package util;

import domain.dto.AttendanceRecordDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceParser {

    public static List<AttendanceRecordDto> parse(List<String[]> records) {
        return records.stream()
                .map(data -> new AttendanceRecordDto(
                        data[0], LocalDateTime.parse(data[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                )
                .toList();
    }
}
