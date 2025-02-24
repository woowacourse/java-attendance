package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordsTest {
    private final AttendanceRecords attendanceRecords = new AttendanceRecords();
    private final List<AttendanceRecord> absentRecords = List.of(
            AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 2)),
            AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 3)),
            AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 4)),
            AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 5)),
            AttendanceRecord.asAbsent(LocalDate.of(2024, 12, 6)));
    private final List<AttendanceRecord> tardyRecords = List.of(
            AttendanceRecord.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 15)),
            AttendanceRecord.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 15)),
            AttendanceRecord.of(LocalDate.of(2024, 12, 11), LocalTime.of(10, 15)),
            AttendanceRecord.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 15)));

    {
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 11), LocalTime.of(11, 0)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 8)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 9), LocalTime.of(14, 0)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 3), LocalTime.of(9, 58)));
        attendanceRecords.addRecord(AttendanceRecord.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)));
    }

    @Test
    @DisplayName("날짜를 입력 받아서 출석 기록을 삭제하고 반환한다.")
    void removeRecordTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 3);
        // when
        AttendanceRecord attendanceRecord = attendanceRecords.removeRecord(date);
        // then
        AttendanceRecord expectedRecord = AttendanceRecord.of(date, LocalTime.of(9, 58));
        assertThat(attendanceRecord).isEqualTo(expectedRecord);
    }

    @Test
    @DisplayName("출석 기록이 없는 날짜를 삭제하려고 할 경우 예외가 발생한다.")
    void removeRecordExceptionTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 12);
        // when & then
        assertThatThrownBy(() -> attendanceRecords.removeRecord(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다.\n");
    }

    @Test
    @DisplayName("총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        // given & when
        int presentCount = attendanceRecords.getAttendanceCount(Attendance.PRESENT);
        // then
        assertThat(presentCount).isEqualTo(4);
    }

    @Test
    @DisplayName("총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        // given & when
        int tardyCount = attendanceRecords.getAttendanceCount(Attendance.TARDY);
        // then
        assertThat(tardyCount).isEqualTo(2);
    }

    @Test
    @DisplayName("총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        // given & when
        int absentCount = attendanceRecords.getAttendanceCount(Attendance.ABSENT);
        // then
        assertThat(absentCount).isEqualTo(2);
    }

    @Test
    @DisplayName("출결 기록을 날짜순으로 정렬해서 반환한다.")
    void getSortedRecordsTest() {
        // given & when
        List<AttendanceRecord> actualRecords = attendanceRecords.getSortedRecords();
        // then
        List<AttendanceRecord> expectedRecords = List.of(
                AttendanceRecord.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 3), LocalTime.of(9, 58)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 9), LocalTime.of(14, 0)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 10), LocalTime.of(10, 8)),
                AttendanceRecord.of(LocalDate.of(2024, 12, 11), LocalTime.of(11, 0)));
        assertThat(actualRecords).isEqualTo(expectedRecords);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(경고)를 반환한다.")
    void getDisciplinaryStatusWarningTest() {
        // given
        AttendanceRecords actualRecords = new AttendanceRecords();
        absentRecords.stream().limit(2).forEach(actualRecords::addRecord);
        // when
        DisciplinaryStatus disciplinaryStatus = actualRecords.getDisciplinaryStatus();
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.WARNING);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(면담)를 반환한다.")
    void getDisciplinaryStatusOneOnOneTest() {
        // given
        AttendanceRecords actualRecords = new AttendanceRecords();
        absentRecords.stream().limit(2).forEach(actualRecords::addRecord);
        tardyRecords.stream().limit(3).forEach(actualRecords::addRecord);
        // when
        DisciplinaryStatus disciplinaryStatus = actualRecords.getDisciplinaryStatus();
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.ONE_ON_ONE);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(제적)를 반환한다.")
    void getDisciplinaryStatusExpelledTest() {
        // given
        AttendanceRecords actualRecords = new AttendanceRecords();
        absentRecords.forEach(actualRecords::addRecord);
        tardyRecords.forEach(actualRecords::addRecord);
        // when
        DisciplinaryStatus disciplinaryStatus = actualRecords.getDisciplinaryStatus();
        // then
        assertThat(disciplinaryStatus).isEqualTo(DisciplinaryStatus.EXPELLED);
    }
}
