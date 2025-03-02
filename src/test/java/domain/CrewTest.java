package domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("유효한 출석 시간을 입력하면 출석 기록")
    @Test
    void validAttendance() {
        // given
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from("2024-12-16 10:00");
        // when
        // then
        final Crew crew = CrewsTest.generateCrew("토미", List.of("2024-12-13 10:00"));

        assertThatCode(() -> crew.attend(attendanceDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("이미 출석한 기록이 있다면 예외 처리")
    @Test
    void invalidAttendance() {
        // given
        final AttendanceDateTime attendanceDateTime = AttendanceDateTime.from("2024-12-13 10:00");
        final Crew crew = CrewsTest.generateCrew("토미", List.of("2024-12-13 10:00"));

        // when
        // then
        assertThatThrownBy(() -> crew.attend(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
