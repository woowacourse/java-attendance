package attendance.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("해당 크루에 출석 기록을 추가할 수 있다.")
    @Test
    void test_addCrewRecord() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook();
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        String crewName = "빙티";

        // when
        attendanceBook.add(crewName, record);

        // then
        List<AttendanceRecord> records = attendanceBook.getRecordsByName(crewName);
        Assertions.assertThat(records).hasSize(1);
        Assertions.assertThat(records.contains(record)).isTrue();
    }

}