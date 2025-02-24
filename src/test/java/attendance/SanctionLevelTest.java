package attendance;

import java.time.LocalDate;
import java.time.LocalTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceManager.AttendanceManager;
import attendance.domain.attendanceManager.AttendanceSanctionManager;

public class SanctionLevelTest {
    private static final String TEST_FILE = "/attendances.csv";

    private AttendanceManager attendanceStatistician;

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = new AttendanceFileReader(TEST_FILE);
        var lines = repository.getLines();
        AttendanceBook attendanceBook = AttendanceBook.from(lines);
        attendanceStatistician = new AttendanceSanctionManager(attendanceBook);
    }

    @Test
    @DisplayName("모든 크루원의 제재 통계를 출력한다.")
    void test_SanctionStatistics() {
        attendanceStatistician.manage("", LocalDate.now(), LocalTime.now());
        Assertions.assertThat(attendanceStatistician.getResult())
            .contains("제적 위험자 조회 결과")
            .contains("- 빙봉: 결석 1회, 지각 6회 (면담)")
            .contains("- 이든: 결석 2회, 지각 5회 (면담)")
            .contains("- 빙티: 결석 3회, 지각 4회 (면담)");
    }

    @Test
    @DisplayName("제재 통계가 제재 수준 순으로 정렬된다.")
    void test_orderBySanctionLevel() {
        // 제적 위험자는 제적 대상자, 면담 대상자, 경고 대상자순으로 출력한다.
        //given&when

        //then
    }

    @Test
    @DisplayName("제재 수준이 같을 경우, 출석 상태 통계를 내림차순으로 정렬한다.")
    void test_orderByAttendanceStateStatisticsDescending() {
        //given&when

        //then
    }

    @Test
    @DisplayName("출석 상태 통계가 같을 경우, 닉네임을 오름차순을 정렬한다.")
    void test_orderByNickNameAscending() {
        //given&when

        //then
    }
}
