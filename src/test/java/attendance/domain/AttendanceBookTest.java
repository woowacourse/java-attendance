package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("해당 크루의 출석 기록을 추가하고 조회할 수 있다.")
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

    @DisplayName("해당 크루의 특정 날짜의 기록을 지각으로 수정할 수 있다.")
    @Test
    void test_modifyCrewRecord() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook();
        AttendanceRecord record = new AttendanceRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        String crewName = "빙티";
        attendanceBook.add(crewName, record);

        // when
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        LocalTime modifyTime = LocalTime.of(13, 6);
        attendanceBook.modify(crewName, targetDate, modifyTime);

        // then
        AttendanceRecord findRecord = attendanceBook.getRecordBy(crewName, targetDate);
        Assertions.assertThat(findRecord.getAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("해당 크루의 기록에 따라 알맞은 경고 등급을 반환한다.")
    @Test
    void test_getWarningStatus() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook();
        String crewName = "빙티";
        attendanceBook.add(crewName, new AttendanceRecord(LocalDateTime.of(2024, 12, 3, 11, 0)));
        attendanceBook.add(crewName, new AttendanceRecord(LocalDateTime.of(2024, 12, 4, 11, 0)));

        // when
        WarningStatus warning = attendanceBook.getWarningByCrew(crewName);

        // then
        Assertions.assertThat(warning).isEqualTo(WarningStatus.WARNING);
    }
}