package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewStatisticTest {
    private static final LocalDate START_DATE = LocalDate.of(2025, 2, 1);
    private static final LocalDate END_DATE = LocalDate.of(2025, 2, LocalDate.now().getDayOfMonth());
    private static final Set<DayOfWeek> WEEKEND = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
    private static final int HOLIDAY_COUNT = 0;

    private static final List<List<String>> ATTENDANCE_RECORDS_NO_ABSENT = List.of(
            List.of("쿠키", "2025-02-19 10:08"),
            List.of("쿠키", "2025-02-18 10:00"),
            List.of("쿠키", "2025-02-17 13:03"),
            List.of("쿠키", "2025-02-14 10:02"),
            List.of("쿠키", "2025-02-13 10:07"),
            List.of("쿠키", "2025-02-12 10:01"),
            List.of("쿠키", "2025-02-11 10:00"),
            List.of("쿠키", "2025-02-10 13:09")
    );

    private final Crews crews = new Crews();
    private final Attendances attendances = new Attendances();
    private final Crew crew = new Crew("쿠키");
    private CrewStatistic crewStatistic;
    private int workingDays;

    @BeforeEach
    void setUp() {
        crews.initCrews(ATTENDANCE_RECORDS_NO_ABSENT);
        attendances.initAttendances(crews, ATTENDANCE_RECORDS_NO_ABSENT);

        List<Attendance> crewAttendances = attendances.findCrewAttendances(crew);
        crewStatistic = new CrewStatistic(crew, crewAttendances);
        crewStatistic.checkCrewStatistic();

        calculateWorkingDays();
    }

    private void calculateWorkingDays() {
        workingDays = (int) START_DATE.datesUntil(END_DATE)
                .map(LocalDate::getDayOfWeek)
                .filter(date -> !WEEKEND.contains(date))
                .count();
        workingDays -= HOLIDAY_COUNT;
    }

    @Test
    @DisplayName("기능: 크루의 전체 출석 기록 개수를 확인")
    void checkTotalAttendanceDays() {
        Attendances crewAttendance = crewStatistic.getCrewAttendances();
        assertThat(crewAttendance.getAttendances())
                .hasSize(workingDays);
    }

    @Test
    @DisplayName("기능: 크루의 출결 횟수 및 상태 정보 계산 확인")
    void calculateCrewStatisticStatusInfo() {
        int safeCount = 5;
        int lateCount = 3;
        int absentCount = workingDays - ATTENDANCE_RECORDS_NO_ABSENT.size() + 1;

        assertThat(List.of(crewStatistic.getSafeCount(), crewStatistic.getLateCount(), crewStatistic.getAbsentCount(),
                crewStatistic.getCrewStatus().toString()))
                .isEqualTo(List.of(safeCount, lateCount, absentCount, "제적"));
    }

    @Test
    @DisplayName("기능: 제적 위험자인 크루들 정보 계산 확인")
    void calculateExpelExpectedCrewsInfo() {
        int absentCount = workingDays - ATTENDANCE_RECORDS_NO_ABSENT.size() + 1;
        int lateCount = 3;

        assertThat(List.of(crewStatistic.getCrewName(), crewStatistic.getAbsentCount(), crewStatistic.getLateCount(),
                crewStatistic.getCrewStatus().toString())).isEqualTo(List.of("쿠키", absentCount, lateCount, "제적"));
    }
}
