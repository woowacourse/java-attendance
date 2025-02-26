package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceDateTime;
import attendance.model.AttendanceRecord;
import attendance.model.Panalty;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
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
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 31)
        ));

        assertThat(attendanceRecord.computeLateCount()).isEqualTo(2);
    }

    @Test
    void 출석_기록에서_출석_회수를_조회한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(9, 50)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 17),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computeAttendanceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("출석하지 않아서 결석인 경우 포함")
    void 출석_기록에서_결석_회수를_조회한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(9, 50)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 17),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computeAbsenceCount()).isEqualTo(17);
    }

    @Test
    @DisplayName("해당 없는 경우")
    void 출석_기록에서_패널티_상태를_조회한다_1() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computePanalty()).isEqualTo(Panalty.NONE);
    }

    @Test
    @DisplayName("경고인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_2() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computePanalty()).isEqualTo(Panalty.WARN);
    }

    @Test
    @DisplayName("면담인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_3() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computePanalty()).isEqualTo(Panalty.INTERVIEW);
    }

    @Test
    @DisplayName("제적인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_4() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 31)
        ));
        assertThat(attendanceRecord.computePanalty()).isEqualTo(Panalty.DISMISSAL);
    }
}
