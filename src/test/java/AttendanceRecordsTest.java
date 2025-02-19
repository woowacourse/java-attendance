import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록이 없는 날짜가 결석으로 기록되었는지 확인한다.")
    void fillAbsencesTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv", () -> LocalDate.of(2024, 12, 13));
        Crew crew = new Crew("짱수");
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, LocalDate.of(2024, 12, 13));

        assertThat(hasRecord).isTrue();
    }

    @Test
    @DisplayName("날짜를 입력 받아서 출석 기록을 삭제하고 반환한다.")
    void removeRecordTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-03 10:07"));
        AttendanceRecord expectedRecord = new AttendanceRecord("2024-12-03 10:07");
        AttendanceRecord attendanceRecord = attendanceRecords.removeRecord(LocalDate.of(2024, 12, 3));

        assertThat(attendanceRecord).isEqualTo(expectedRecord);
    }

    @Test
    @DisplayName("출석 기록이 없는 날짜를 삭제하려고 할 경우 예외가 발생한다.")
    void removeRecordExceptionTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-03 10:00"));

        assertThatThrownBy(() -> attendanceRecords.removeRecord(LocalDate.of(2024, 12, 4)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다.");
    }

    @Test
    @DisplayName("총 출석 횟수를 반환한다.")
    void getPresentCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-02 13:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-03 09:58"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-04 10:02"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-05 10:06"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-06 10:01"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-09 14:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-10 10:08"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-11 11:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-12 11:00"));

        assertThat(attendanceRecords.getPresentCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("총 지각 횟수를 반환한다.")
    void getTardyCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-02 13:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-03 09:58"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-04 10:02"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-05 10:06"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-06 10:01"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-09 14:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-10 10:08"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-11 11:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-12 11:00"));

        assertThat(attendanceRecords.getTardyCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("총 결석 횟수를 반환한다.")
    void getAbsentCountTest() {
        AttendanceRecords attendanceRecords = new AttendanceRecords();
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-02 13:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-03 09:58"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-04 10:02"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-05 10:06"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-06 10:01"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-09 14:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-10 10:08"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-11 11:00"));
        attendanceRecords.addRecord(new AttendanceRecord("2024-12-12 11:00"));

        assertThat(attendanceRecords.getAbsentCount()).isEqualTo(3);
    }
}
