package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

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

    private List<CrewAttendance> createCrewAttendances() {
        return List.of(
                CrewAttendance.of(
                        Crew.of("차니"), createAttendanceTimes()
                ),
                CrewAttendance.of(
                        Crew.of("포비"), createAttendanceTimes()
                )
        );
    }

    private AttendanceTimes createAttendanceTimes() {
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 11),
                LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 12),
                LocalTime.of(10, 5)
        );
        AttendanceTime attendanceTime4 = AttendanceTime.of(
                LocalDate.of(2024, 12, 13),
                LocalTime.of(11, 6)
        );
        return AttendanceTimes.of(
                List.of(attendanceTime1,
                        attendanceTime2,
                        attendanceTime3,
                        attendanceTime4)
        );
    }
}