package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("크루 생성 테스트")
    void test1() {
        //given
        final String name = "윌슨";
        final Map<LocalDate, Attendance> attendanceMap = new LinkedHashMap<>();

        //should
        assertThatCode(() -> new Crew(name, attendanceMap)).doesNotThrowAnyException();

    }

    @Test
    @DisplayName("출석 추가 테스트")
    void test2() {
        //given
    	final String name = "윌슨";
        final Map<LocalDate, Attendance> attendanceMap = new LinkedHashMap<>();
        final LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 13, 10, 6);
        final LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 20, 10, 5);
        final LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 20, 10, 31);
        final LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 9, 13, 6);
        final LocalDateTime localDateTime5 = LocalDateTime.of(2024, 12, 16, 13, 5);
        final LocalDateTime localDateTime6 = LocalDateTime.of(2024, 12, 16, 13, 31);

        //when
        final Crew crew = new Crew(name, attendanceMap);
        crew.putAttendance(localDateTime1);
        crew.putAttendance(localDateTime2);
        crew.putAttendance(localDateTime3);
        crew.putAttendance(localDateTime4);
        crew.putAttendance(localDateTime5);
        crew.putAttendance(localDateTime6);

        //then
        assertThat(attendanceMap).containsEntry(localDateTime1.toLocalDate(), Attendance.of(localDateTime1))
                .containsEntry(localDateTime2.toLocalDate(), Attendance.of(localDateTime2))
                .containsEntry(localDateTime3.toLocalDate(), Attendance.of(localDateTime3))
                .containsEntry(localDateTime4.toLocalDate(), Attendance.of(localDateTime4))
                .containsEntry(localDateTime5.toLocalDate(), Attendance.of(localDateTime5))
                .containsEntry(localDateTime6.toLocalDate(), Attendance.of(localDateTime6));

    }
}
