package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @Nested
    @DisplayName("출석 등록 테스트")
    class AttendTest {
        @Test
        @DisplayName("출석 기록을 가지고 출석을 기록한다")
        void should_attend_by_attendanceRecord() {
            // given
            AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse("2024-12-11"),
                    LocalTime.parse("10:00"));
            Attendances attendances = new Attendances();

            // when
            attendances.attend(attendanceRecord);

            // then
            assertThat(attendances).isNotEqualTo(new Attendances());
        }

        @Test
        @DisplayName("이미 출석한 경우를 알 수 있다")
        void should_return_true_when_already_attended() {
            // given
            AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDate.parse("2024-12-11"),
                    LocalTime.parse("10:00"));
            Attendances attendances = new Attendances();
            attendances.attend(attendanceRecord);

            // when
            boolean result = attendances.isAttended();

            // then
            assertThat(result).isEqualTo(true);
        }
    }
}
