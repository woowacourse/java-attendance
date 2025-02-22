package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.CrewRepository;
import service.AttendanceService;

public class CrewTest {
    private static final LocalDate TUESDAY = LocalDate.of(2025, 2, 4);
    private final AttendanceService attendanceService = new AttendanceService();

    @BeforeEach
    void initCrewRepository() {
        CrewRepository.clear();
    }

    @Test
    @DisplayName("닉네임과 등교 시간으로 출석한다")
    void attendanceTest() {
        CrewRepository.addCrew(new Crew("pobi"));
        Crew crew = CrewRepository.findByNickname("pobi");
        crew.insertAttendanceTime(TUESDAY, LocalTime.of(10, 1));
    }

    @Test
    @DisplayName("이미_출석한_경우_예외를_던진다")
    void attendanceExceptionTest() {
        Crew crew = new Crew("pobi");
        crew.insertAttendanceTime(TUESDAY, LocalTime.of(10, 0));
        assertThatThrownBy(() -> {
            crew.insertAttendanceTime(TUESDAY, LocalTime.of(10, 1));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임과 수정날짜와 등교시간으로 기록을 수정한다")
    void modifyAttendanceTest() {
        // given
        CrewRepository.addCrew(new Crew("pobi"));
        Crew crew = CrewRepository.findByNickname("pobi");
        crew.insertAttendanceTime(TUESDAY, LocalTime.of(10, 0));

        // when
        crew.modifyAttendanceTime(TUESDAY, LocalTime.of(10, 10));
        AttendanceTime attendanceTime = crew.getAttendanceTimeByDate(TUESDAY);

        // then
        assertThat(attendanceTime.time())
                .isEqualTo(LocalTime.of(10, 10));
        assertThat(attendanceTime.status())
                .isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("날짜와 시간으로 출석 상태를 계산한다")
    void getAttendanceStatusByDateTest() {
        CrewRepository.addCrew(new Crew("pobi"));
        Crew crew = CrewRepository.findByNickname("pobi");
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 17), LocalTime.of(13, 6));
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 18), LocalTime.of(10, 5));
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 19), LocalTime.of(10, 31));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 2, 3)))
                    .isEqualTo(AttendanceStatus.ABSENT);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 2, 17)))
                    .isEqualTo(AttendanceStatus.LATE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 2, 18)))
                    .isEqualTo(AttendanceStatus.ATTENDANCE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 2, 19)))
                    .isEqualTo(AttendanceStatus.ABSENT_LATE);
        });
    }

    @Test
    @DisplayName("출석 상태별 횟수를 계산한다")
    void getAttendanceStatusCounterTest() {
        // given
        Crew crew = new Crew("pobi");
        // LATE
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 3), LocalTime.of(13, 6));
        // ATTENDANCE
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 4), LocalTime.of(10, 5));
        // ABSENT_LATE
        crew.insertAttendanceTime(LocalDate.of(2025, 2, 5), LocalTime.of(10, 31));
        // 2월6일, 2월7일 -> ABSENT
        // when
        AttendanceStatusStatistics attendanceStatusStatistics = attendanceService.getAttendanceStatusStatistics(
                crew,
                LocalDate.of(2025, 2, 10)
        );

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceStatusStatistics.getCountByStatus(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(attendanceStatusStatistics.getCountByStatus(AttendanceStatus.LATE)).isEqualTo(1);
            softly.assertThat(attendanceStatusStatistics.getCountByStatus(AttendanceStatus.ABSENT_LATE)).isEqualTo(1);
            softly.assertThat(attendanceStatusStatistics.getCountByStatus(AttendanceStatus.ABSENT)).isEqualTo(2);
        });
    }
}
