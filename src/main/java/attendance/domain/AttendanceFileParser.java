package attendance.domain;

import attendance.dto.AttendanceFileDto;
import attendance.utils.DateConverter;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.Files.lines;

public class AttendanceFileParser implements AttendanceReader{

    public static final String DELIMITER = ",";
    public static final int NAME_INDEX = 0;
    public static final int LOCAL_DATE_TIME_INDEX = 1;

    private final String path;

    public AttendanceFileParser(String path) {
        this.path = path;
    }

    public List<AttendanceFileDto> read() {
        try (Stream<String> lines = lines(Path.of(path))) {
            return lines.skip(1)
                .map(this::parseToAttendanceFileDto)
                .toList();
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("[ERROR] 파일을 읽기에 실패하였습니다. 경로: %s", path), e);
        }
    }

    private AttendanceFileDto parseToAttendanceFileDto(String line) {
        String[] split = line.split(DELIMITER);
        validateSplit(split);
        LocalDate attendanceDate = DateConverter.convertToDate(split[LOCAL_DATE_TIME_INDEX]);
        LocalTime attendanceTime = DateConverter.convertToTime(split[LOCAL_DATE_TIME_INDEX]);
        return AttendanceFileDto.of(split[NAME_INDEX], attendanceDate, attendanceTime);
    }

    private void validateSplit(String[] split) {
        if (split.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식을 입력하셨습니다.");
        }
    }
}
