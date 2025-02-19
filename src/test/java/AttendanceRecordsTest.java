import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록이 없는 날짜가 결석으로 기록되었는지 확인한다.")
    void fillAbsencesTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv");
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
}
