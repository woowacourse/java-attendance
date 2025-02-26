package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @Test
    void 출석_기록에서_지각_회수를_조회한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));

        assertThat(attendanceRecord.computeLateCount()).isEqualTo(2);
    }
}
