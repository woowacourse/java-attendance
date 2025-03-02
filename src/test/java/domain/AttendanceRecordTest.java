package domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

class AttendanceRecordTest {

    @Test
    void 출석일자와_시간을_입력하면_출석일시가_기록된다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord(datetime);

        assertThat(attendanceRecord.hasAttendanceDate(datetime.toLocalDate())).isEqualTo(true);
    }

    @Test
    void 출석일자와_시간을_입력하여_출석일시를_수정한다() {
        final LocalDateTime beforeDatetime = LocalDateTime.of(2024, 12, 14, 10, 5);
        final LocalDateTime afterDatetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord(beforeDatetime);
        attendanceRecord.editAttendanceDate(afterDatetime.toLocalDate(), afterDatetime.toLocalTime());
        assertThat(attendanceRecord.getAttendanceDate(beforeDatetime.toLocalDate()).getTime()).isEqualTo(afterDatetime.toLocalTime());
    }

    @Test
    void 출석이_정상적으로_기록되었는지_확인한다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord(datetime);
        AttendanceDate attendanceDate = attendanceRecord.getAttendanceDate(datetime.toLocalDate());
        AttendanceDate attendanceDateToCompare = new AttendanceDate(datetime);

        assertThat(attendanceDate.getDate()).isEqualTo(attendanceDateToCompare.getDate());
        assertThat(attendanceDate.getTime()).isEqualTo(attendanceDateToCompare.getTime());
        assertThat(attendanceDate.getStatus()).isEqualTo(attendanceDateToCompare.getStatus());
    }

    @Test
    void 여러_일자의_출석이_정상적으로_기록된다() {
        final List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 11, 10, 5), LocalDateTime.of(2024, 12, 12, 10, 5),
                LocalDateTime.of(2024, 12, 13, 10, 5), LocalDateTime.of(2024, 12, 14, 10, 5));

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        dateTimes.forEach(attendanceRecord::addAttendanceDate);

        assertThat(attendanceRecord.getAttendanceDates().size()).isEqualTo(dateTimes.size());
    }

    @Test
    void 이미_출석된_일자에_출석하려는_경우_예외가_발생한다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord(datetime);

        assertThatThrownBy(() -> attendanceRecord.validateBeforeAdd(datetime.toLocalDate())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석되지_않은_일자를_수정하려는_경우_예외가_발생한다() {
        final LocalDateTime datetime = LocalDateTime.of(2024, 12, 14, 10, 5);

        AttendanceRecord attendanceRecord = new AttendanceRecord();

        assertThatThrownBy(() -> attendanceRecord.validateBeforeEdit(datetime.toLocalDate())).isInstanceOf(IllegalArgumentException.class);
    }
}