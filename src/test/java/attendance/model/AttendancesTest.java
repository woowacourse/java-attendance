package attendance.model;

import static attendance.model.AttendanceTestFixtures.createAttendanceInRawDateTime;
import static attendance.model.AttendanceTestFixtures.createCrewGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 내역 테스트")
class AttendancesTest {

    @DisplayName("크루는 같은 날에 또 출석할 경우 예외가 발생한다")
    @Test
    void shouldThrowException_WhenCrewAgainAttendanceInToday() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendance beforeAttendance = createAttendanceInRawDateTime(pobi, "2024-12-02 10:01");
        Attendance afterAttendance = createAttendanceInRawDateTime(pobi, "2024-12-02 10:01");
        Attendances attendances = new Attendances(crewGroup, Set.of(beforeAttendance));

        // when & then
        assertThatThrownBy(() -> attendances.add(afterAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루는 같은 날에 또 출석할 수 없습니다.");
    }

    @DisplayName("등록되지 않은 닉네임을 사용하려고 하는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNotExistNickname() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendance attendance = createAttendanceInRawDateTime(pobi, "2024-12-02 10:01");
        Attendances attendances = new Attendances(crewGroup, Set.of(attendance));
        Nickname notExistNickname = new Nickname("네오");

        // when & then
        assertThatThrownBy(() -> attendances.validateExistNickname(notExistNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 닉네임입니다.");
    }

    @DisplayName("출석 기록을 수정할 수 있다.")
    @Test
    void attendanceUpdateTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendance attendance = createAttendanceInRawDateTime(pobi, "2024-12-13 10:01");
        Attendances attendances = new Attendances(crewGroup, Set.of(attendance));

        // when
        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 13, 11, 1);
        Attendance modifidedAttendance = attendances.update(new Attendance(pobi, updateDateTime));

        // then
        assertThat(attendances)
                .extracting("attendances")
                .isEqualTo(Set.of(modifidedAttendance));
    }

    @DisplayName("크루가 찾으려는 날짜에 출석한 경우 닉네임과 날짜로 기존 출석을 찾을 수 있다.")
    @Test
    void attendanceFindTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendance attendance = createAttendanceInRawDateTime(pobi, "2024-12-13 10:01");
        Attendances attendances = new Attendances(crewGroup, Set.of(attendance));

        // when & then
        LocalDate findDate = LocalDate.of(2024, 12, 13);
        assertThat(attendances.findByCrewAndDate(pobi, findDate))
                .isEqualTo(attendance);
    }

    @DisplayName("크루가 찾으려는 날짜에 출석하지 않은 경우 닉네임과 날짜로 시간이 기록되지 않은 출석을 찾을 수 있다.")
    @Test
    void attendanceNotFoundTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendances attendances = new Attendances(crewGroup, Set.of());

        // when & then
        LocalDate findDate = LocalDate.of(2024, 12, 13);
        assertThat(attendances.findByCrewAndDate(pobi, findDate))
                .isEqualTo(new Attendance(pobi, findDate, null));
    }

    @DisplayName("크루의 해당 달의 출석 기록을 조회할 수 있다.")
    @Test
    void attendanceHistoryByCrewTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        CrewGroup crewGroup = createCrewGroup(pobi);
        Attendances attendances = new Attendances(crewGroup, Set.of(
                createAttendanceInRawDateTime(pobi, "2024-11-01 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-03 10:12")
        ));

        // when
        Month findMonth = Month.DECEMBER;
        Set<Attendance> attendanceHistory = attendances.findAllByCrewAndMonth(pobi, findMonth);

        // then
        assertThat(attendanceHistory)
                .isEqualTo(Set.of(
                                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                                createAttendanceInRawDateTime(pobi, "2024-12-03 10:12")
                        )
                );
    }

    @DisplayName("모든 크루의 해당 달의 출석 기록을 조회할 수 있다.")
    @Test
    void attendanceHistoryTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        Crew neo = AttendanceTestFixtures.NEO;
        CrewGroup crewGroup = createCrewGroup(pobi, neo);
        Attendances attendances = new Attendances(crewGroup, Set.of(
                createAttendanceInRawDateTime(pobi, "2024-11-01 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-03 10:12"),
                createAttendanceInRawDateTime(neo, "2024-11-01 10:01"),
                createAttendanceInRawDateTime(neo, "2024-12-02 10:01"),
                createAttendanceInRawDateTime(neo, "2024-12-03 10:12")
        ));

        // when
        Month findMonth = Month.DECEMBER;
        Map<Crew, Set<Attendance>> attendanceHistory = attendances.findAllByMonth(findMonth);

        // then
        assertThat(attendanceHistory)
                .isEqualTo(Map.of(
                        pobi, Set.of(
                                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                                createAttendanceInRawDateTime(pobi, "2024-12-03 10:12")),
                        neo, Set.of(
                                createAttendanceInRawDateTime(neo, "2024-12-02 10:01"),
                                createAttendanceInRawDateTime(neo, "2024-12-03 10:12"))
                ));
    }
}
