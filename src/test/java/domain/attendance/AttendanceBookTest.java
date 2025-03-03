package domain.attendance;

import static domain.testdata.AttendanceTestData.AttendanceTimesData.createCounselingAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createDismissAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createNormalAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createWarnedAttendanceTimes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.crew.Crew;
import domain.crew.CrewAttendance;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    @DisplayName("출석부 생성")
    void createAttendanceBookTest() {
        // given
        List<CrewAttendance> crewAttendances = createCrewAttendances();

        // when, then
        assertThatCode(() -> AttendanceBook.of(crewAttendances))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("크루를 입력받아 해당 크루의 출석 기록을 반환")
    void findCrewAttendanceByCrewTest() {
        // given
        List<CrewAttendance> crewAttendances = createCrewAttendances();
        AttendanceBook attendanceBook = AttendanceBook.of(crewAttendances);
        Crew crew = Crew.of("차니");

        // when
        CrewAttendance crewAttendance = attendanceBook.findCrewAttendanceByCrew(crew);

        // then
        assertThat(crewAttendance.belongsTo(crew))
                .isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 크루 검색 시 예외 발생")
    void findCrewAttendanceWithNonExistCrewThrowException() {
        // given
        List<CrewAttendance> crewAttendances = createCrewAttendances();
        AttendanceBook attendanceBook = AttendanceBook.of(crewAttendances);
        Crew crew = Crew.of("pobi");

        // when, then
        assertThatThrownBy(() -> attendanceBook.findCrewAttendanceByCrew(crew))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 이름의 크루가 존재하지 않습니다.");
    }

    @Test
    @DisplayName("제적 위험자 조회")
    void findDisciplinaryCrewsTest() {
        // given
        LocalDate today = LocalDate.of(2024, 12, 10);
        List<CrewAttendance> crewAttendances = createCrewAttendances();
        AttendanceBook attendanceBook = AttendanceBook.of(crewAttendances);

        // when
        List<CrewAttendance> disciplinaryCrews = attendanceBook.findDisciplinaryCrews(today);

        // then
        assertThat(disciplinaryCrews).hasSize(4);
    }

    private List<CrewAttendance> createCrewAttendances() {
        return List.of(
                CrewAttendance.of(
                        Crew.of("차니"), createNormalAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("포비"), createWarnedAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("이든"), createCounselingAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("새로이"), createDismissAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("고든"), createNormalAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("램지"), createWarnedAttendanceTimes()
                )
        );
    }
}
