package domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import util.FileReaderUtil;

public class AttendanceSheetsFactory {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String DELIMITER = ",";

    private final FileReaderUtil fileReaderUtil;

    public AttendanceSheetsFactory(FileReaderUtil fileReaderUtil) {
        this.fileReaderUtil = fileReaderUtil;
    }

    public AttendanceSheets create() {
        List<String> lines = fileReaderUtil.read();

        return new AttendanceSheets(lines.stream()
                .map(this::createAttendanceSheet)
                .toList());
    }

    private AttendanceSheet createAttendanceSheet(String line) {
        String nickname = line.split(DELIMITER)[0];
        String rawDateTime = line.split(DELIMITER)[1];

        return new AttendanceSheet(
                nickname,
                AttendanceDateTime.from(LocalDateTime.parse(rawDateTime, DATE_TIME_FORMATTER))
        );
    }
}
