package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 내역 테스트")
class AttendancesTest {

    @DisplayName("크루는 같은 날에 또 출석할 경우 예외가 발생한다")
    @Test
    void shouldThrowException_WhenCrewAgainAttendanceInToday() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2024, 12, 13, 11, 1);
        Attendance beforeAttendance = new Attendance(crew, now);
        Attendance afterAttendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, List.of(beforeAttendance));

        Assertions.assertThatThrownBy(() -> attendances.add(afterAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루는 같은 날에 또 출석할 수 없습니다.");
    }

    @DisplayName("등록되지 않은 닉네임을 사용하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNotExistNickname() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2024, 12, 13, 11, 1);
        Attendance attendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, List.of(attendance));

        String notExistNickname = "네오";
        Assertions.assertThatThrownBy(() -> attendances.validateExistNickname(notExistNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석 기록을 수정할 수 있다.")
    @Test
    void attendanceUpdateTest() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2024, 12, 13, 10, 1);
        Attendance attendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, List.of(attendance));

        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 13, 11, 1);
        Attendance modifidedAttendance = attendances.update(
                now.toLocalDate(),
                new Attendance(crew, updateDateTime));

        assertThat(attendances)
                .extracting("attendances")
                .isEqualTo(List.of(modifidedAttendance));
    }

    @DisplayName("크루가 찾으려는 날짜에 출석한 경우 닉네임과 날짜로 기존 출석을 찾을 수 있다.")
    @Test
    void attendanceFindTest() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        LocalDateTime now = LocalDateTime.of(2024, 12, 13, 10, 1);
        Attendance attendance = new Attendance(crew, now);
        Attendances attendances = new Attendances(crewGroup, List.of(attendance));
        LocalDate findDate = LocalDate.of(2024, 12, 13);

        Attendance actual = attendances.findByCrewAndDate(crew, findDate);

        assertThat(actual).isEqualTo(attendance);
    }

    @DisplayName("크루가 찾으려는 날짜에 출석하지 않은 경우 닉네임과 날짜로 기존 출석을 찾을 수 없다.")
    @Test
    void attendanceNotFoundTest() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        Attendances attendances = new Attendances(crewGroup, List.of());

        LocalDate findDate = LocalDate.of(2024, 12, 13);
        Attendance attendance = attendances.findByCrewAndDate(crew, findDate);

        assertThat(attendance.getAttendanceTime()).isNull();
    }

    @DisplayName("크루의 해당 달의 출석 기록을 조회할 수 있다.")
    @Test
    void findMonthlyAttendance() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));
        Attendances attendances = new Attendances(crewGroup, List.of(
                new Attendance(crew, LocalDateTime.of(2024, 11, 1, 10, 1)),
                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 10, 12))
        ));

        Month findMonth = Month.DECEMBER;
        MonthlyAttendance monthlyAttendance = attendances.findMonthlyAttendance(crew, findMonth);

        assertThat(monthlyAttendance)
                .isEqualTo(new MonthlyAttendance(
                        Month.DECEMBER,
                        crew,
                        List.of(
                                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 10, 12))
                        )
                ));
    }

    @DisplayName("모든 크루의 출석 결과를 조회할 수 있다.")
    @Test
    void attendanceResultTest() {
        Crew pobi = new Crew("포비");
        Crew neo = new Crew("네오");
        CrewGroup crewGroup = new CrewGroup(Set.of(pobi, neo));
        Attendances attendances = new Attendances(crewGroup, List.of(
                new Attendance(pobi, LocalDateTime.of(2024, 11, 1, 10, 1)),
                new Attendance(pobi, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(pobi, LocalDateTime.of(2024, 12, 3, 10, 12)),
                new Attendance(neo, LocalDateTime.of(2024, 11, 1, 10, 1)),
                new Attendance(neo, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(neo, LocalDateTime.of(2024, 12, 3, 10, 12))
        ));
        LocalDate endDate = LocalDate.of(2024, 12, 3);

        List<AttendanceResult> attendanceResults = attendances.findAllCrewAttendanceResultUntilDate(endDate);

        assertThat(attendanceResults)
                .contains(
                        new AttendanceResult(
                                pobi,
                                Map.of(AttendanceType.OK, 1, AttendanceType.LATE, 1),
                                List.of(new Attendance(pobi, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                        new Attendance(pobi, LocalDateTime.of(2024, 12, 3, 10, 12)))),
                        new AttendanceResult(
                                neo,
                                Map.of(AttendanceType.OK, 1, AttendanceType.LATE, 1),
                                List.of(new Attendance(neo, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                        new Attendance(neo, LocalDateTime.of(2024, 12, 3, 10, 12))))
                );
    }
}
