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
        Map<Crew, AttendanceHistory> map = new HashMap<>();
        Crew crew = new Crew("빙티");
        map.put(crew, new AttendanceHistory());
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));

        // when
        attendanceBook.add(crew, record);

        // then
        AttendanceHistory history = attendanceBook.getHistoryByCrew(crew);
        assertThat(history.getRecords()).hasSize(1);
        assertThat(history.getRecords()).contains(record);
    }

    @DisplayName("해당 크루의 특정 날짜의 기록을 지각으로 수정할 수 있다.")
    @Test
    void test_modifyCrewRecord() {
        // given
        Map<Crew, AttendanceHistory> map = new HashMap<>();
        Crew crew = new Crew("빙티");
        AttendanceHistory history = new AttendanceHistory();
        map.put(crew, history);
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 2, 13, 0));
        attendanceBook.add(crew, record);

        // when
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        LocalTime modifyTime = LocalTime.of(13, 6);
        attendanceBook.modify(crew, TestUtil.WoowaDatefrom(targetDate), modifyTime);

        // then
        AttendanceHistory updatedHistory = attendanceBook.getHistoryByCrew(crew);
        assertThat(updatedHistory.findRecordByDate(TestUtil.WoowaDatefrom(targetDate)).get().getAttendanceStatus())
                .isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("크루 이름으로 해당 크루의 기록을 찾는다.")
    @Test
    void test_findCrewHistory() {
        // given
        Map<Crew, AttendanceHistory> map = new HashMap<>();
        Crew crew = new Crew("빙티");
        map.put(crew, new AttendanceHistory()); // 미리 해당 크루의 기록을 등록
        AttendanceBook attendanceBook = new AttendanceBook(map);
        AttendanceRecord record = TestUtil.createRecord(LocalDateTime.of(2024, 12, 3, 11, 0));
        attendanceBook.add(crew, record);

        // when
        AttendanceHistory history = attendanceBook.getHistoryByCrew(crew);

        // then
        assertThat(history.getRecords()).hasSize(1);
        AttendanceRecord findRecord = history.findRecordByDate(
                TestUtil.WoowaDatefrom(LocalDate.of(2024, 12, 3))
        ).get();
        assertThat(findRecord).isEqualTo(record);
    }
}