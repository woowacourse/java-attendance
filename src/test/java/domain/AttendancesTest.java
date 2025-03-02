package domain;

import static org.assertj.core.api.Assertions.assertThat;

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
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
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
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
            Attendances attendances = new Attendances();
            attendances.attend(attendanceRecord);

            // when
            boolean result = attendances.isAttended();

            // then
            assertThat(result).isEqualTo(true);
        }
    }

    @Nested
    @DisplayName("출석 수정 테스트")
    class EditTest {
        @Test
        @DisplayName("수정할 출석 기록을 가지고 출석을 수정한다")
        void should_edit_by_attendanceRecord_to_edit() {
            // given
            Attendances attendances = new Attendances();
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");
            attendances.attend(attendanceRecord);
            AttendanceRecord editAttendanceRecord = AttendanceRecord.of("11", "11:00");
            int prevHash = attendances.hashCode();

            // when
            attendances.edit(editAttendanceRecord);

            // then
            assertThat(attendances.hashCode()).isNotEqualTo(prevHash);
        }
    }
}
