package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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

        @Test
        @DisplayName("동일한 날짜에 출석 기록을 등록하면 예외가 발생한다")
        void should_throw_exception_when_attend_same_date() {
            // given
            Attendances attendances = new Attendances();
            attendances.attend(AttendanceRecord.of("11", "10:00"));
            AttendanceRecord attendanceRecord = AttendanceRecord.of("11", "10:00");

            // when, then
            assertThatThrownBy(() -> attendances.attend(attendanceRecord))
                    .isInstanceOf(IllegalArgumentException.class);
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

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록을 가져온다")
        void should_return_attendanceRecord_of_same_date() {
            // given
            Attendances attendances = new Attendances();
            AttendanceRecord attendanceRecord = AttendanceRecord.of("2", "10:00");
            attendances.attend(attendanceRecord);
            AttendanceRecord targetAttendanceRecord = AttendanceRecord.of("2", "00:00");

            // when
            AttendanceRecord result = attendances.getAttendanceRecordOfSameDate(targetAttendanceRecord);

            // then
            assertThat(result).isEqualTo(attendanceRecord);
        }

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록이 없다면 생성해 가져온다")
        void should_create_and_return_attendanceRecord_of_same_date() {
            // given
            Attendances attendances = new Attendances();
            AttendanceRecord targetAttendanceRecord = AttendanceRecord.of("2", "00:00");

            // when
            AttendanceRecord result = attendances.getAttendanceRecordOfSameDate(targetAttendanceRecord);

            // then
            AttendanceRecord expected = AttendanceRecord.dateOf(2);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록을 가져온다")
        void should_return_attendanceRecord_of_same_dateInt() {
            // given
            Attendances attendances = new Attendances();
            AttendanceRecord attendanceRecord = AttendanceRecord.of("2", "10:00");
            attendances.attend(attendanceRecord);
            int date = 2;

            // when
            AttendanceRecord result = attendances.getAttendanceRecordOfSameDate(date);

            // then
            assertThat(result).isEqualTo(attendanceRecord);
        }

        @Test
        @DisplayName("출석 기록과 동일한 날짜의 출석 기록이 없다면 생성해 가져온다")
        void should_create_and_return_attendanceRecord_of_same_dateInt() {
            // given
            Attendances attendances = new Attendances();
            int date = 2;

            // when
            AttendanceRecord result = attendances.getAttendanceRecordOfSameDate(date);

            // then
            AttendanceRecord expected = AttendanceRecord.dateOf(2);
            assertThat(result).isEqualTo(expected);
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

        @Test
        @DisplayName("출석 기록들로부터 출석 상태를 계산한다.")
        void should_return_attendance_status_by_attendances() {
            // given
            Attendances attendances = new Attendances();
            attendances.attend(AttendanceRecord.of("2", "10:00"));
            attendances.attend(AttendanceRecord.of("3", "10:00"));
            attendances.attend(AttendanceRecord.of("4", "10:00"));
            attendances.attend(AttendanceRecord.of("5", "10:00"));

            // when
            AttendanceStatusCount attendanceStatusCount = attendances.countAttendanceStatus();

            // then
            assertThat(attendanceStatusCount.getCount(AttendanceStatus.ATTENDANT)).isEqualTo(4);
        }
    }
}
