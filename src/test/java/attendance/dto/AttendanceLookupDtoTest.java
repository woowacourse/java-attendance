package attendance.dto;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import attendance.domain.Crew;
import attendance.domain.CrewStatistic;
import attendance.domain.Crews;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceLookupDtoTest {
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

    @DisplayName("기능: 크루 출석 기록 정보 반환 목록 초기화 확인")
    @Test
    void getAttendanceExpelInfo() {
        AttendanceLookupDto attendanceLookupDto = AttendanceLookupDto.fromAttendanceHistoryInfo(this.crew,
                this.crewStatistic);
        assertThat(attendanceLookupDto.attendanceLookupRecords()).hasSize(workingDays);
    }
}
