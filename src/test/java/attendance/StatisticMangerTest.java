package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendanceManager.StatisticManger;

public class StatisticMangerTest {
    private static final String TEST_FILE = "/attendances.csv";

    private StatisticManger statisticManger;

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = AttendanceFileReader.from(TEST_FILE);
        var lines = repository.getLines();
        AttendanceBook attendanceBook = AttendanceBook.from(lines);
        statisticManger = new StatisticManger(attendanceBook);
    }

    @Test
    @DisplayName("닉네임을 입력하면, 크루 출석 기록을 확인한다.")
    void test_attendanceHistory() {
        var nickname = "이든";
        statisticManger.manage(nickname);

        assertThat(statisticManger.getResult())
            .contains("""
                12월 2일 월요일 13:12 (출석)
                12월 3일 화요일 10:12 (지각)
                12월 4일 수요일 10:12 (지각)
                """);
    }

    @Test
    @DisplayName("등교하지 않은 날에 대해서도 출석 기록에 포함한다.")
    void test_shouldIncludeAbsentDaysInAttendanceHistory() {
        var nickname = "이든";
        statisticManger.manage(nickname);

        assertThat(statisticManger.getResult())
            .contains("12월 5일 목요일 --:-- (결석)");
    }

    @Test
    @DisplayName("등교하지 않은 날에 대해서도 출석 기록에 포함한다.")
    void test_shouldIncludeWeekendInAttendanceHistory() {
        var nickname = "이든";
        statisticManger.manage(nickname);

        assertThat(statisticManger.getResult())
            .doesNotContain("토요일")
            .doesNotContain("일요일");
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력할 경우, 예외가 발생한다.")
    void error_notRegisteredNickname() {
        var nickname = "믹든";

        assertThatThrownBy(() -> statisticManger.manage(nickname))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    @Test
    @DisplayName("해당 크루의 출석 상태 통계를 확인할 수 있다.")
    void test_getAttendanceStateStatistics() {
        var nickname = "이든";
        statisticManger.manage(nickname);

        assertThat(statisticManger.getResult())
            .contains("""
                출석: 11회
                지각: 5회
                결석: 2회
                """);
    }

    @Test
    @DisplayName("해당 크루의 제재 수준을 판단한다.")
    void test_shouldJudgeSanctionLevelOfCrew() {
        var nickname = "이든";
        statisticManger.manage(nickname);

        assertThat(statisticManger.getResult())
            .contains("면담 대상자입니다.");
    }
}
