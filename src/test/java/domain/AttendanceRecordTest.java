package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @Test
    void 출석일자와_시간을_입력하면_출석일시가_기록된다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.applyAttendanceDate(datetime);

        assertThat(attendanceRecord.hasAttendanceDate(datetime.toLocalDate())).isEqualTo(true);
    }

    @Test
    void 출석일자와_시간을_입력하여_출석일시를_수정한다() {
        final LocalDateTime beforeDatetime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDateTime afterDatetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.applyAttendanceDate(beforeDatetime);
        attendanceRecord.applyAttendanceDate(afterDatetime);

        assertThat(attendanceRecord.getAttendanceDate(beforeDatetime.toLocalDate()).getTime()).isEqualTo(afterDatetime.toLocalTime());
    }

    @Test
    void 출석이_정상적으로_기록되었는지_확인한다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.applyAttendanceDate(datetime);

        AttendanceDate attendanceDate = attendanceRecord.getAttendanceDate(datetime.toLocalDate());
        AttendanceDate attendanceDateToCompare = new AttendanceDate(datetime);

        assertThat(attendanceDate.getDate()).isEqualTo(attendanceDateToCompare.getDate());
        assertThat(attendanceDate.getTime()).isEqualTo(attendanceDateToCompare.getTime());
        assertThat(attendanceDate.getStatus()).isEqualTo(attendanceDateToCompare.getStatus());
    }

    @Test
    void 여러_일자의_출석이_정상적으로_기록된다() {
        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 15), LocalDateTime.of(2024, 12, 12, 10, 15),
                LocalDateTime.of(2024, 12, 13, 10, 15), LocalDateTime.of(2024, 12, 14, 10, 15));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        dateTimes.forEach(attendanceRecord::applyAttendanceDate);

        assertThat(attendanceRecord.calculateTardyCount()).isEqualTo(dateTimes.size());
    }

    @Test
    void 출석_횟수를_계산한다() {
        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 0), LocalDateTime.of(2024, 12, 12, 10, 0),
                LocalDateTime.of(2024, 12, 13, 10, 0), LocalDateTime.of(2024, 12, 14, 10, 0));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        dateTimes.forEach(attendanceRecord::applyAttendanceDate);

        assertThat(attendanceRecord.calculateAttendanceCount()).isEqualTo(dateTimes.size());
    }

    @Test
    void 지각_횟수를_계산한다() {
        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 10), LocalDateTime.of(2024, 12, 12, 10, 10),
                LocalDateTime.of(2024, 12, 13, 10, 10), LocalDateTime.of(2024, 12, 14, 10, 10));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        dateTimes.forEach(attendanceRecord::applyAttendanceDate);

        assertThat(attendanceRecord.calculateTardyCount()).isEqualTo(dateTimes.size());
    }

    @Test
    void 결석_횟수를_계산한다() {
        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 15, 10), LocalDateTime.of(2024, 12, 12, 15, 10),
                LocalDateTime.of(2024, 12, 13, 15, 10), LocalDateTime.of(2024, 12, 14, 15, 10));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        dateTimes.forEach(attendanceRecord::applyAttendanceDate);

        assertThat(attendanceRecord.calculateAbsenceCount()).isEqualTo(11);
    }
}
