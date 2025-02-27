package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @DisplayName("해당 크루의 출석 기록을 추가하고 조회할 수 있다.")
    @Test
    void test_addCrewRecord() {
        // given
        Map<String, AttendanceHistory> map = new HashMap<>();
        String crewName = "빙티";
        map.put(crewName, new AttendanceHistory());
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));

        // when
        attendanceBook.add(crewName, record);

        // then
        AttendanceHistory history = attendanceBook.getHistoryByName(crewName);
        assertThat(history.getRecords()).hasSize(1);
        assertThat(history.getRecords().contains(record)).isTrue();
    }

    @DisplayName("해당 크루의 특정 날짜의 기록을 지각으로 수정할 수 있다.")
    @Test
    void test_modifyCrewRecord() {
        // given
        Map<String, AttendanceHistory> map = new HashMap<>();
        String crewName = "빙티";
        AttendanceHistory history = new AttendanceHistory();
        map.put(crewName, history);
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(crewName, record);

        // when
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        LocalTime modifyTime = LocalTime.of(13, 6);
        attendanceBook.modify(crewName, TestUtil.WoowaDatefrom(targetDate), modifyTime);

        // then
        AttendanceHistory updatedHistory = attendanceBook.getHistoryByName(crewName);
        assertThat(updatedHistory.getRecordByDate(TestUtil.WoowaDatefrom(targetDate)).getAttendanceStatus())
                .isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("해당 크루의 기록에 따라 알맞은 경고 등급을 반환한다.")
    @Test
    void test_getWarningStatus() {
        // given
        Map<String, AttendanceHistory> map = new HashMap<>();
        String crewName = "빙티";
        map.put(crewName, new AttendanceHistory()); // 미리 해당 크루의 기록을 등록
        AttendanceBook attendanceBook = new AttendanceBook(map);
        attendanceBook.add(crewName, TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 11, 0)));
        attendanceBook.add(crewName, TestUtil.createRecord(LocalDateTime.of(2024, 12, 4, 11, 0)));

        // when
        WarningStatus warning = attendanceBook.getWarningByCrew(crewName);

        // then
        assertThat(warning).isEqualTo(WarningStatus.WARNING);
    }

    @DisplayName("크루 이름으로 해당 크루의 기록을 찾는다.")
    @Test
    void test_findCrewHistory() {
        // given
        Map<String, AttendanceHistory> map = new HashMap<>();
        String crewName = "빙티";
        map.put(crewName, new AttendanceHistory()); // 미리 해당 크루의 기록을 등록
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 11, 0));
        attendanceBook.add(crewName, record);

        // when
        AttendanceHistory history = attendanceBook.getHistoryByName(crewName);

        // then
        assertThat(history.getRecords()).hasSize(1);
        AttendanceRecord findRecord = history.getRecordByDate(TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 3)));
        assertThat(findRecord).isEqualTo(record);
    }
}