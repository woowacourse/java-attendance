package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
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
                        Crew.of("차니"), createAttendanceTimes()
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
                        Crew.of("고든"), createAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("램지"), createWarnedAttendanceTimes()
                )
        );
    }

    // 결석 1회, 지각 2회
    static AttendanceTimes createAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(11, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(11, 5)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 4),
                LocalTime.of(9, 6)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 5),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime5 = AttendanceTime.of(
                LocalDate.of(2024, 12, 6),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime6 = AttendanceTime.of(
                LocalDate.of(2024, 12, 9),
                LocalTime.of(13, 1)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4,
                        attendanceTime5,
                        attendanceTime6)
        );
    }

    // 결석 1회, 지각 3회, 경고 대상자
    static AttendanceTimes createWarnedAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(13, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 4),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 5),
                LocalTime.of(11, 6)
        );
        AttendanceTime attendanceTime5 = AttendanceTime.of(
                LocalDate.of(2024, 12, 6),
                LocalTime.of(9, 6)
        );
        AttendanceTime attendanceTime6 = AttendanceTime.of(
                LocalDate.of(2024, 12, 9),
                LocalTime.of(11, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4,
                        attendanceTime5,
                        attendanceTime6)
        );
    }

    // 결석 2회, 지각 3회, 면담 대상자
    static AttendanceTimes createCounselingAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(15, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(11, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 4),
                LocalTime.of(11, 6)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 5),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime5 = AttendanceTime.of(
                LocalDate.of(2024, 12, 6),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime6 = AttendanceTime.of(
                LocalDate.of(2024, 12, 9),
                LocalTime.of(11, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4,
                        attendanceTime5,
                        attendanceTime6)
        );
    }

    // 결석 6회
    static AttendanceTimes createDismissAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(16, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(16, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 4),
                LocalTime.of(16, 6)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 5),
                LocalTime.of(16, 6)
        );
        AttendanceTime attendanceTime5 = AttendanceTime.of(
                LocalDate.of(2024, 12, 6),
                LocalTime.of(16, 6)
        );
        AttendanceTime attendanceTime6 = AttendanceTime.of(
                LocalDate.of(2024, 12, 9),
                LocalTime.of(16, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4,
                        attendanceTime5,
                        attendanceTime6)
        );
    }
}