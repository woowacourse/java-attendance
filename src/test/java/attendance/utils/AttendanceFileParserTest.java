package attendance.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import attendance.domain.AttendanceFileParser;
import attendance.domain.AttendanceReader;
import attendance.dto.FileRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceFileParserTest {

    @Test
    @DisplayName("파일을 읽고 문자열로 반환한다")
    void readFile() {
        AttendanceReader reader = new AttendanceFileParser("src/test/java/resources/testAttendances.csv");
        List<FileRequestDto> expected = List.of(
                new FileRequestDto("쿠키", LocalDate.of(2024,12,13), LocalTime.of(10,8)),
                new FileRequestDto("빙봉", LocalDate.of(2024,12,13), LocalTime.of(10,7)),
                new FileRequestDto("빙티", LocalDate.of(2024,12,13), LocalTime.of(10,7)),
                new FileRequestDto("이든", LocalDate.of(2024,12,13), LocalTime.of(10,7))
        );

        assertThat(reader.read()).isEqualTo(expected);
    }
}
