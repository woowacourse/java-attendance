import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CrewAttendanceRecordsTest {
    @Test
    @DisplayName("출석 기록 존재 여부를 반환한다.")
    void hasRecordTest() {
        CrewAttendanceRecords crewAttendanceRecords = new CrewAttendanceRecords("/attendances.csv");
        Crew crew = new Crew("쿠키");
        LocalDate date = LocalDate.of(2024, 12, 13);
        boolean hasRecord = crewAttendanceRecords.hasRecord(crew, date);

        assertThat(hasRecord).isTrue();
    }
}
