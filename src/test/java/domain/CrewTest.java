package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("크루의 제적 상태 확인 테스트")
    @Test
    void checkCrewStatusTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.findCrewStatus(LocalDate.of(2024, 12, 4))).isEqualTo(CrewStatus.WARNING);
    }

    @DisplayName("크루 이름 동일성 확인 테스트")
    @Test
    void crewNameEqualsTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of());
        Crew crew = new Crew(crewName, attendances);

        Assertions.assertThat(crew.isSameName("메이"))
                .isEqualTo(true);
    }

    @DisplayName("크루 출석 확인 테스트")
    @Test
    void crewAttendTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of());
        Crew crew = new Crew(crewName, attendances);

        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 24));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 30));
        crew.attend(attendanceDate, attendanceTime);

        Assertions.assertThat(crew.checkAlreadyAttend(attendanceDate)).isEqualTo(true);
    }

    @DisplayName("크루 결석에서 출석으로 기록 수정 테스트")
    @Test
    void crewAttendanceEditTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of());
        Crew crew = new Crew(crewName, attendances);
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 3));
        AttendanceTime oldTime = new AttendanceTime(LocalTime.of(10, 35));
        AttendanceTime newTime = new AttendanceTime(LocalTime.of(9, 55));

        crew.attend(attendanceDate, oldTime);
        crew.edit(attendanceDate, newTime);

        Assertions.assertThat(crew.findAttendanceByDate(attendanceDate).isLate()).isEqualTo(false);
    }

    @DisplayName("크루가 제적 위험인지 확인")
    @Test
    void checkCrewExpelledTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 31)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.isExpelledStatus(LocalDate.of(2024, 12, 5))).isEqualTo(true);
    }

    @DisplayName("크루의 지각 수 카운트 테스트")
    @Test
    void checkCrewLateCountTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 6))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 6)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.getLateCount()).isEqualTo(2);
    }

    @DisplayName("크루의 결석 수 카운트 테스트")
    @Test
    void checkCrewAbsentCountTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.getAbsentCount(LocalDate.of(2024, 12, 4))).isEqualTo(2);
    }

    @DisplayName("크루의 지각 환산 포함 결석 수 카운트 테스트")
    @Test
    void checkCrewAbsentWithLateCountTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of(
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 2)), new AttendanceTime(LocalTime.of(13, 6))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 6))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 6)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.getExpelledAbsentCount(LocalDate.of(2024, 12, 5))).isEqualTo(1);
    }
}
