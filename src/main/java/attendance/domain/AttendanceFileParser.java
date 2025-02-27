package attendance.domain;

import attendance.dto.AttendanceFileDto;
import attendance.utils.DateConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceFileParser implements AttendanceReader{

    public static final String DELIMITER = ",";
    public static final int NAME_INDEX = 0;
    public static final int LOCAL_DATE_TIME_INDEX = 1;

    private final String path;

    public AttendanceFileParser(String path) {
        this.path = path;
    }

    public List<AttendanceFileDto> read() {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine();

            List<AttendanceFileDto> attendanceFileDtos = new ArrayList<>();
            parseToAttendanceFileDto(br, attendanceFileDtos);
            return attendanceFileDtos;
        } catch (IOException e) {
            throw new UncheckedIOException(String.format("[ERROR] 파일을 읽기에 실패하였습니다. 경로: %s", path), e);
        }
    }

    private void parseToAttendanceFileDto(BufferedReader br, List<AttendanceFileDto> attendanceFileDtos) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(DELIMITER);
            validateSplit(split);
            LocalDate attendanceDate = DateConverter.convertToLocalDate(split[LOCAL_DATE_TIME_INDEX]);
            LocalTime attendanceTime = DateConverter.convertToLocalTime(split[LOCAL_DATE_TIME_INDEX]);
            attendanceFileDtos.add(AttendanceFileDto.of(split[NAME_INDEX], attendanceDate, attendanceTime));
        }
    }

    private void validateSplit(String[] split) {
        if (split.length != 2) {
            throw new IllegalArgumentException("[ERROR] 잘못된 파일 형식을 입력하셨습니다.");
        }
    }
}
