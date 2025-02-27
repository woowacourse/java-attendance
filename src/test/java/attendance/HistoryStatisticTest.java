package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceHistory;
import attendance.domain.HistoryStatistic;
import attendance.domain.SanctionLevel;
import attendance.domain.SystemDateTime;
import attendance.exception.AttendanceFileException;
import attendance.utility.CsvReader;

public class HistoryStatisticTest {
    private final CsvReader csvReader = new CsvReader("/attendances.csv");
    private final SystemDateTime systemDateTime = new AttendanceDateTime();
    private final AttendanceBook attendanceBook = AttendanceBook.of(csvReader.getLines(), systemDateTime);

    public HistoryStatisticTest() throws AttendanceFileException {
    }

    @Test
    @DisplayName("해당 크루의 제재 수준을 판단한다.")
    void test_shouldJudgeSanctionLevelOfCrew() {
        var nickname = "이든";
        var datetime = LocalDateTime.of(2024, 12, 11, 10, 0);
        var date = datetime.toLocalDate();
        var attendance = Attendance.of(datetime, systemDateTime);
        var attendanceLate = Attendance.of(datetime.plusMinutes(10), systemDateTime);
        Map<LocalDate, Attendance> attendances = new HashMap<>();
        attendances.put(date, attendance);
        attendances.put(date.plusDays(1), attendanceLate);

        var attendanceHistory = AttendanceHistory.of(attendances, systemDateTime.extractWorkingDays());
        var statusStatistic = new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname);

        assertThat(statusStatistic.judgeSanctionLevel()).isEqualTo(SanctionLevel.DISMISS);
    }

    @Test
    @DisplayName("제재 통계가 제재 수준 순으로 정렬된다.")
    void test_orderBySanctionLevel() {
        List<HistoryStatistic> historyStatistics = new ArrayList<>();
        for (String nickname : attendanceBook.getNicknameSet()) {
            var attendances = attendanceBook.getAttendances(nickname);
            Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
            var attendanceHistory = AttendanceHistory.of(attendancesRecord, systemDateTime.extractWorkingDays());

            historyStatistics.add(new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname));
        }

        Collections.sort(historyStatistics);

        SanctionLevel[] expectedLevels = {
            SanctionLevel.DISMISS,
            SanctionLevel.NEED_MEETING,
            SanctionLevel.NEED_MEETING,
            SanctionLevel.NEED_MEETING,
            SanctionLevel.WARNING
        };

        for (int i = 0; i < expectedLevels.length; i++) {
            assertThat(historyStatistics.get(i).judgeSanctionLevel())
                .isEqualTo(expectedLevels[i]);
        }
    }

    @Test
    @DisplayName("제재 수준이 같을 경우, 출석 상태 통계를 내림차순으로 정렬한다.")
    void test_orderByAttendanceStateStatisticsDescending() {
        List<HistoryStatistic> historyStatistics = new ArrayList<>();
        for (String nickname : attendanceBook.getNicknameSet()) {
            var attendances = attendanceBook.getAttendances(nickname);
            Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
            var attendanceHistory = AttendanceHistory.of(attendancesRecord, systemDateTime.extractWorkingDays());

            historyStatistics.add(new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname));
        }

        Collections.sort(historyStatistics);
        assertAll(
            () -> assertThat(historyStatistics.get(2).judgeSanctionLevel())
                .isEqualTo(historyStatistics.get(3).judgeSanctionLevel()),
            () -> assertThat(historyStatistics.get(2).getWeightForComparingSort())
                .isGreaterThan(historyStatistics.get(3).getWeightForComparingSort())
        );
    }

    @Test
    @DisplayName("출석 상태 통계가 같을 경우, 닉네임을 오름차순을 정렬한다.")
    void test_orderByNickNameAscending() {
        List<HistoryStatistic> historyStatistics = new ArrayList<>();
        for (String nickname : attendanceBook.getNicknameSet()) {
            var attendances = attendanceBook.getAttendances(nickname);
            Map<LocalDate, Attendance> attendancesRecord = attendances.getAttendances();
            var attendanceHistory = AttendanceHistory.of(attendancesRecord, systemDateTime.extractWorkingDays());

            historyStatistics.add(new HistoryStatistic(attendanceHistory.countStatusOnHistory(), nickname));
        }

        Collections.sort(historyStatistics);
        assertAll(
            () -> assertThat(historyStatistics.get(1).judgeSanctionLevel())
                .isEqualTo(historyStatistics.get(2).judgeSanctionLevel()),
            () -> assertThat(historyStatistics.get(1).nickname())
                .isLessThan(historyStatistics.get(2).nickname())
        );
    }
}
