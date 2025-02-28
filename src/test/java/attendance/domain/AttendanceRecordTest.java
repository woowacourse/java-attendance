package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @Nested
    class ValidCases {

        @Test
        void 출석일시를_기록한다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);

            AttendanceDate attendanceDate = new AttendanceDate(
                2024, 12, 2);
            AttendanceTime attendanceTime = new AttendanceTime(
                13, 0);
            AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                attendanceDate, attendanceTime);
            // when
            attendanceRecord.addAttendanceDateTime(attendanceDateTime);

            // then
            assertThat(attendanceRecord.getAttendanceDateTimes())
                .containsEntry(attendanceDate, attendanceTime);
        }

        @Test
        void 출석일시를_수정한다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceDate attendanceDate = new AttendanceDate(
                2024, 12, 2);
            AttendanceTime attendanceTime = new AttendanceTime(
                13, 0);
            attendanceDateTimes.put(attendanceDate, attendanceTime);
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);

            AttendanceTime modifiedAttendanceTime = new AttendanceTime(
                14, 0);
            AttendanceDateTime modifiedAttendanceDateTime = new AttendanceDateTime(
                attendanceDate, modifiedAttendanceTime);
            
            // when
            attendanceRecord.modifyAttendanceDateTime(
                modifiedAttendanceDateTime);

            // then
            assertThat(attendanceRecord.getAttendanceDateTimes())
                .containsEntry(attendanceDate, modifiedAttendanceTime);
        }
    }

    @Nested
    class InvalidCases {

        @Test
        void 출석_기록은_기록을_가지지않는다면_기록하지않는다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceRecord(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록은 기록을 가지고 있어야 합니다.");
        }

        @Test
        void 출석_기록은_출석날짜와_출석시간을_가지지않는다면_기록하지않는다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceRecord = new HashMap<>();
            attendanceRecord.put(null, null);

            // when & then
            assertThatThrownBy(
                () -> new AttendanceRecord(attendanceRecord))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록은 출석 날짜와 출석 시간을 가지고 있어야 합니다.");
        }

        @Test
        void 이미_해당_날짜의_출석시간이_기록되어있다면_기록하지않는다() {
            // given
            Map<AttendanceDate, AttendanceTime> attendanceDateTimes = new HashMap<>();
            AttendanceDate attendanceDate = new AttendanceDate(
                2024, 12, 2);
            AttendanceTime attendanceTime = new AttendanceTime(
                13, 0);
            attendanceDateTimes.put(attendanceDate, attendanceTime);
            AttendanceRecord attendanceRecord = new AttendanceRecord(
                attendanceDateTimes);

            AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                attendanceDate, attendanceTime);

            // when & then
            assertThatThrownBy(
                () -> attendanceRecord.addAttendanceDateTime(
                    attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 해당 날짜의 출석 시간이 기록되어 있습니다.");
        }
    }
}
