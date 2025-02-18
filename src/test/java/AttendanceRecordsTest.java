import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록이 없는 날짜가 결석으로 기록되었는지 확인한다.")
    void fillAbsencesTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv");
        Crew crew = new Crew("짱수");
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, LocalDate.of(2024, 12, 13));

        assertThat(hasRecord).isTrue();
    }
}
