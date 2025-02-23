package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록이 없는 날짜가 결석으로 기록되었는지 확인한다.")
    void fillAbsencesTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("쿠키");
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, LocalDate.of(2024, 12, 12));

        assertThat(hasRecord).isTrue();
    }

    @Test
    @DisplayName("입력 받은 날짜에 해당하는 출석 기록을 삭제한다.")
    void removeRecordTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        AttendanceRecord attendanceRecord = AttendanceRecord.parse("2024-12-03 10:07");
        attendanceRecords.addRecord(attendanceRecord);
        attendanceRecords.removeRecord(attendanceRecord);

        assertThat(attendanceRecords.hasRecordOfDate(attendanceRecord.getDate())).isFalse();
    }

    @Test
    @DisplayName("출석 기록이 없는 날짜를 가져오려고 할 경우 예외가 발생한다.")
    void getRecordAtDateExceptionTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        AttendanceRecord attendanceRecord = AttendanceRecord.parse("2024-12-03 10:00");

        assertThatThrownBy(() -> attendanceRecords.getRecordAtDate(attendanceRecord.getDate()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다.\n");
    }

    @Test
    @DisplayName("총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-02 13:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-03 09:58"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-04 10:02"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-05 10:06"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-06 10:01"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-09 14:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-10 10:08"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-11 11:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-12 11:00"));

        assertThat(attendanceRecords.getAttendanceCount(Attendance.PRESENT)).isEqualTo(4);
    }

    @Test
    @DisplayName("총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-02 13:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-03 09:58"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-04 10:02"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-05 10:06"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-06 10:01"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-09 14:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-10 10:08"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-11 11:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-12 11:00"));

        assertThat(attendanceRecords.getTardyCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-02 13:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-03 09:58"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-04 10:02"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-05 10:06"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-06 10:01"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-09 14:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-10 10:08"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-11 11:00"));
        attendanceRecords.addRecord(AttendanceRecord.parse("2024-12-12 11:00"));

        assertThat(attendanceRecords.getAbsentCount()).isEqualTo(3);
    }

    @Test
    @DisplayName("출결 기록을 날짜순으로 저장한다.")
    void getAttendanceRecordsTest() {
        AttendanceRecords actualRecords = new AttendanceRecords();
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-12 11:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-11 11:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-10 10:08"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-09 14:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-06 10:01"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-05 10:06"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-04 10:02"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-03 09:58"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-02 13:00"));

        List<AttendanceRecord> expectedRecords = List.of(
                AttendanceRecord.parse("2024-12-02 13:00"),
                AttendanceRecord.parse("2024-12-03 09:58"),
                AttendanceRecord.parse("2024-12-04 10:02"),
                AttendanceRecord.parse("2024-12-05 10:06"),
                AttendanceRecord.parse("2024-12-06 10:01"),
                AttendanceRecord.parse("2024-12-09 14:00"),
                AttendanceRecord.parse("2024-12-10 10:08"),
                AttendanceRecord.parse("2024-12-11 11:00"),
                AttendanceRecord.parse("2024-12-12 11:00"));

        assertThat(actualRecords.getAttendanceRecords().stream().toList()).isEqualTo(expectedRecords);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(경고)를 반환한다.")
    void getDisciplinaryStatusWarningTest() {
        AttendanceRecords actualRecords = new AttendanceRecords();
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-12 12:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-11 12:00"));

        assertThat(actualRecords.getDisciplinaryStatus()).isEqualTo(DisciplinaryStatus.WARNING);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(면담)를 반환한다.")
    void getDisciplinaryStatusOneOnOneTest() {
        AttendanceRecords actualRecords = new AttendanceRecords();
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-12 12:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-11 12:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-10 12:08"));

        assertThat(actualRecords.getDisciplinaryStatus()).isEqualTo(DisciplinaryStatus.ONE_ON_ONE);
    }

    @Test
    @DisplayName("제적 위험자 대상 상태(제적)를 반환한다.")
    void getDisciplinaryStatusExpelledTest() {
        AttendanceRecords actualRecords = new AttendanceRecords();
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-13 12:08"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-12 12:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-11 12:00"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-10 12:08"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-09 14:08"));
        actualRecords.addRecord(AttendanceRecord.parse("2024-12-06 14:08"));

        assertThat(actualRecords.getDisciplinaryStatus()).isEqualTo(DisciplinaryStatus.EXPELLED);
    }
}
