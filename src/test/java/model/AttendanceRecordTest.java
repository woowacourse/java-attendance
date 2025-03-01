package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceDate;
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
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 11),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 12),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 13),
                LocalTime.of(10, 31)
        ));

        // when
        long lateCount = attendanceRecord.computeLateCount();

        // then
        assertThat(lateCount).isEqualTo(2);
    }

    @Test
    void 출석_기록에서_출석_회수를_조회한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(9, 50)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 11),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 12),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 13),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 17),
                LocalTime.of(10, 31)
        ));

        // when
        long attendCount = attendanceRecord.computeAttendanceCount();

        // then
        assertThat(attendCount).isEqualTo(2);
    }

    @Test
    @DisplayName("출석하지 않아서 결석인 경우 포함")
    void 출석_기록에서_결석_회수를_조회한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 10),
                LocalTime.of(9, 50)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 11),
                LocalTime.of(10, 6)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 12),
                LocalTime.of(10, 30)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 13),
                LocalTime.of(10, 5)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 17),
                LocalTime.of(10, 31)
        ));
        LocalDate now = LocalDate.of(2024, 12, 18);

        // when
        long absenceCount = attendanceRecord.computeAbsencesUntil(now);

        // then
        assertThat(absenceCount).isEqualTo(9);
    }

    @Test
    @DisplayName("해당 없는 경우")
    void 출석_기록에서_패널티_상태를_조회한다_1() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                LocalTime.of(10, 31)
        ));
        LocalDate now = LocalDate.of(2024, 12, 3);

        // when
        Panalty panalty = attendanceRecord.computePanaltyUntil(now);

        assertThat(panalty).isEqualTo(Panalty.NONE);
    }

    @Test
    @DisplayName("경고인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_2() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 3),
                LocalTime.of(10, 31)
        ));
        LocalDate now = LocalDate.of(2024, 12, 4);

        // when
        Panalty panalty = attendanceRecord.computePanaltyUntil(now);

        // then
        assertThat(panalty).isEqualTo(Panalty.WARN);
    }

    @Test
    @DisplayName("면담인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_3() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                LocalTime.of(10, 31)
        ));
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 3),
                LocalTime.of(10, 31)
        ));
        LocalDate now = LocalDate.of(2024, 12, 5);

        // when
        Panalty panalty = attendanceRecord.computePanaltyUntil(now);

        // then
        assertThat(panalty).isEqualTo(Panalty.INTERVIEW);
    }

    @Test
    @DisplayName("제적인 경우")
    void 출석_기록에서_패널티_상태를_조회한다_4() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.add(new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                LocalTime.of(10, 31)
        ));
        LocalDate now = LocalDate.of(2024, 12, 17);

        // when
        Panalty panalty = attendanceRecord.computePanaltyUntil(now);

        // then
        assertThat(panalty).isEqualTo(Panalty.DISMISSAL);
    }

    @Test
    void 크루의_출석_기록에_출석을_한다() {
        // given
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.attend(
                new AttendanceDate(2024, 12, 2),
                LocalTime.of(10, 5)
        );
        LocalDate now = LocalDate.of(2024, 12, 17);

        // when
        long attendCount = attendanceRecord.computeAttendanceCount();

        // then
        assertThat(attendCount).isEqualTo(1);
    }
}
