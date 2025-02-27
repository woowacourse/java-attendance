import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Attendance;
import domain.Crew;
import domain.ERROR_MESSAGE;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class SomeTest {
    @Nested
    @DisplayName("크루 출석 기록 저장 테스트")
    class AddAttendanceTest {

        @DisplayName("정상 출석 확인")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("빙봉");
            LocalDateTime attendedTime = LocalDateTime.of(2024, 12, 3, 8, 25);

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
            Attendance attendance1 = new Attendance(LocalDateTime.of(2024, 12, 3, 8, 25));
            Attendance attendance2 = new Attendance(LocalDateTime.of(2024, 12, 4, 10, 25));

            // when
            crew.addAttendance(attendance1);
            crew.addAttendance(attendance2);

            // then
            assertThat(crew.getAttendanceHistory()).containsExactly(attendance1, attendance2);
        }
    }

    @Nested
    @DisplayName("크루 출석 기록 저장 오류 테스트")
    class AbnormalAddAttendanceTest {

        @DisplayName("이미 출석한 경우")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("띠용");
            Attendance attendanceBefore = new Attendance(LocalDateTime.of(2024, 12, 3, 8, 25));
            Attendance attendanceAfter = new Attendance(LocalDateTime.of(2024, 12, 3, 10, 25));
            crew.addAttendance(attendanceBefore);

            // when & then
            assertThatThrownBy(() -> crew.addAttendance(attendanceAfter))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(ERROR_MESSAGE.ALREADY_ATTENDED.getMessage());
        }
    }

    @Nested
    @DisplayName("크루 출석 상태 갯수")
    class CountAttendanceStatusTest {

        @DisplayName("출석 상태별 개수 반환")
        @Test
        void test1() {
            // given
            Crew crew = new Crew("빙봉");

            // when
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 2, 8, 25)); // 출석
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 3, 8, 25)); // 출석
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 4, 8, 25)); // 출석
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 5, 10, 25)); // 지각
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 6, 10, 25)); // 지각
            crew.addAttendanceWithDateTime(LocalDateTime.of(2024, 12, 9, 15, 0)); // 결석

            // then
            assertAll(
                    () -> assertThat(crew.getAttendCount()).isEqualTo(3),
                    () -> assertThat(crew.getLateCount()).isEqualTo(2),
                    () -> assertThat(crew.getAbsentCount()).isEqualTo(1)
            );
        }
    }
}
