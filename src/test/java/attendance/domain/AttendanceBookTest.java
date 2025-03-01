package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceBookTest {

    @Test
    public void 출석부_생성() {
        //given
        Crew crew = new Crew("우가");
        Crews crews = new Crews(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2025, 2, 28, 9, 59);

        assertDoesNotThrow(() -> new AttendanceBook(crews, now));
    }

    @ParameterizedTest
    @CsvSource(value = "2025, 2, 28, 9, 59, 19")
    public void 현재_날짜_이전날까지_출석부_없는_평일날_생성(int year, int month, int day, int hour, int minute, int expectedResult) {
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
}
