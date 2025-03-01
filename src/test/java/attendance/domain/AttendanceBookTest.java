package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceBookTest {

    @Test
    void 출석부_생성() {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2025, 2, 28, 9, 59);

        assertDoesNotThrow(() -> new AttendanceBook(crews, now));
    }

    @ParameterizedTest
    @CsvSource(value = "2025, 2, 28, 9, 59, 19")
    void 현재_날짜_이전날까지_출석부_없는_평일날_생성(int year, int month, int day, int hour, int minute, int expectedResult) {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(year, month, day, hour, minute);

        //when
        AttendanceBook attendanceBook = new AttendanceBook(crews, now);

        //then
        Assertions.assertThat(attendanceBook.getAttendanceBook().get(crew).getAttendanceRecord().size())
                .isEqualTo(expectedResult);

    }

    @ParameterizedTest
    @CsvSource(value = {
            "2025, 2, 24, 12, 59, ATTENDANCE", "2025, 2, 24, 13, 6, LATE", "2025, 2, 24, 13, 31, ABSENCE",
            "2025, 2, 25, 9, 59, ATTENDANCE", "2025, 2, 25, 10, 6, LATE", "2025, 2, 25, 10, 31, ABSENCE",
            "2025, 2, 26, 9, 59, ATTENDANCE", "2025, 2, 26, 10, 6, LATE", "2025, 2, 26, 10, 31, ABSENCE",
            "2025, 2, 27, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE",
            "2025, 2, 28, 9, 59, ATTENDANCE", "2025, 2, 27, 10, 6, LATE", "2025, 2, 27, 10, 31, ABSENCE"})
    void 출석_확인(int year, int month, int day, int hour, int minute, AttendanceStatus attendanceStatus) {
        //given
        String crewName = "우가";
        Crew crew = new Crew(crewName);

        Crews crews = new Crews(Set.of(crew));

        LocalDateTime currentTime = LocalDateTime.of(2025, 2, 21, 9, 59);
        AttendanceBook attendanceBook = new AttendanceBook(crews, currentTime);

        LocalDateTime inputTime = LocalDateTime.of(year, month, day, hour, minute);
        Assertions.assertThat(attendanceBook.registerAttendance(crewName, inputTime))
                .isEqualTo(attendanceStatus);
    }
}
