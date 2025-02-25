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
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 3)), new AttendanceTime(LocalTime.of(10, 31))),
                new Attendance(new AttendanceDate(LocalDate.of(2024, 12, 4)), new AttendanceTime(LocalTime.of(10, 31)))
        ));
        Crew crew = new Crew(crewName, attendances);

        assertThat(crew.getCrewStatus()).isEqualTo(CrewStatus.WARNING);
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

    @DisplayName("크루 출석 테스트")
    @Test
    void crewAttendTest() {
        String crewName = "메이";
        Attendances attendances = new Attendances(List.of());
        Crew crew = new Crew(crewName, attendances);

        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 24));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 30));
        crew.attend(attendanceDate, attendanceTime);

        Assertions.assertThatThrownBy(() -> crew.checkAlreadyAttend(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
