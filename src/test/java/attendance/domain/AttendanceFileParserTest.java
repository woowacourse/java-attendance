package attendance.domain;

import attendance.dto.AttendanceFileDto;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceFileParserTest {

    @Test
    void 출석_파일을_읽고_dto로_반환한다() {
        // given
        AttendanceFileParser attendanceFileParser = new AttendanceFileParser("src/test/java/resources/testAttendances.csv");

        List<AttendanceFileDto> attendanceFileDtos = List.of (
            new AttendanceFileDto("쿠키", LocalDate.of(2024, 12, 13), LocalTime.of(10, 8)),
            new AttendanceFileDto("빙봉", LocalDate.of(2024, 12, 13), LocalTime.of(10, 7)),
            new AttendanceFileDto("빙티", LocalDate.of(2024, 12, 13), LocalTime.of(10, 7)),
            new AttendanceFileDto("이든", LocalDate.of(2024, 12, 13), LocalTime.of(10, 7))
        );

        // when & then
        assertThat(attendanceFileParser.read()).isEqualTo(attendanceFileDtos);
    }
}
