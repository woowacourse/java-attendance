import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import domain.AttendanceStatus;
import domain.Crew;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SomeTest {
    @Nested
    @DisplayName("크루의 출석 기록 저장 상태 확인")
    class AddAttendanceTest {
        @DisplayName("정상 출석 확인")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("빙봉");
            LocalDateTime attendedTime = LocalDateTime.of(2024, 12, 3,8,25);

            // when
            crew.addAttendanceWithDateTime(attendedTime);

            // then
            assertThat(crew.getAttendanceHistory()).extracting("dateTime").containsExactly(attendedTime);
        }

        @DisplayName("정상 출석 확인2")
        @Test
        void test2() {
            // given
            Crew crew = new Crew("빙봉");
            Attendance attendance1 = new Attendance(LocalDateTime.of(2024, 12, 3,8,25));
            Attendance attendance2 = new Attendance(LocalDateTime.of(2024, 12, 4,10,25));

            // when
            crew.addAttendance(attendance1);
            crew.addAttendance(attendance2);

            // then
            assertThat(crew.getAttendanceHistory()).containsExactly(attendance1, attendance2);
        }
    }



}
