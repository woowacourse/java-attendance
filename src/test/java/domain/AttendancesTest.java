package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.DateUtil;

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

        @ParameterizedTest
        @DisplayName("확인할 출석 기록으로 출석 기록과 동일한 날짜의 출석 기록이 존재하는지 확인할 수 있다")
        @CsvSource(value = {"11, 11, true", "10, 11, false"})
        void should_return_true_when_same_date_attended_attendanceRecord(String attendedDate, String checkDate,
                                                                         boolean expected) {
            // given
            Attendances attendances = new Attendances();
            AttendanceRecord attendedAttendanceRecord = AttendanceRecord.of(attendedDate, "10:00");
            attendances.attend(attendedAttendanceRecord);
            AttendanceRecord checkAttendanceRecord = AttendanceRecord.of(checkDate, "10:00");

            // when
            boolean result = attendances.isAttended(checkAttendanceRecord);

            // then
            assertThat(result).isEqualTo(expected);
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

    @Nested
    @DisplayName("크루별 출석 기록 확인 테스트")
    class CheckAttendanceTest {
        @Test
        @DisplayName("날짜들로부터 출석 기록을 확인한다")
        void should_check_attendance_by_dates() {
            // given
            Attendances attendances = new Attendances();
            attendances.attend(AttendanceRecord.of("2", "10:00"));
            attendances.attend(AttendanceRecord.of("3", "10:00"));
            attendances.attend(AttendanceRecord.of("4", "10:00"));
            attendances.attend(AttendanceRecord.of("5", "10:00"));
            List<Integer> attendAbleDates = DateUtil.getAttendAbleDates(6);

            // when
            Attendances result = attendances.checkAttendance(attendAbleDates);

            // then
            assertThat(result).isNotEqualTo(attendances);
        }
    }
}
