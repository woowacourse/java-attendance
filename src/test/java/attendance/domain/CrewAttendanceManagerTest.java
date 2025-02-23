package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceManagerTest {

    @DisplayName("크루의 출석 기록을 저장할 수 있어야 한다.")
    @Test
    void should_save_crew_attendance_histories() {
        CrewAttendanceManager crewAttendanceManager = CrewAttendanceManager.create();

        Crew crew = Crew.from("젠슨");
        LocalDateTime attendanceDateTime1 = LocalDateTime.of(2025, 2, 24, 10, 0);
        LocalDateTime attendanceDateTime2 = LocalDateTime.of(2025, 2, 25, 10, 5);

        AttendanceHistory attendanceHistory1 = AttendanceHistory.from(attendanceDateTime1);
        AttendanceHistory attendanceHistory2 = AttendanceHistory.from(attendanceDateTime2);

        crewAttendanceManager.addCrewAttendanceInfo(crew, attendanceHistory1);
        crewAttendanceManager.addCrewAttendanceInfo(crew, attendanceHistory2);

        AttendanceHistories attendanceHistories = crewAttendanceManager.findAttendanceHistoriesByCrew(
            crew);

        List<AttendanceHistory> findAttendanceHistories = attendanceHistories.getAttendanceHistories();
        assertThat(findAttendanceHistories.get(0).getAttendanceTime().getAttendanceTime()).isEqualTo(attendanceDateTime1);
        assertThat(findAttendanceHistories.get(0).getAttendanceType()).isEqualTo(ATTENDANCE);

        assertThat(findAttendanceHistories.get(1).getAttendanceTime().getAttendanceTime()).isEqualTo(attendanceDateTime2);
        assertThat(findAttendanceHistories.get(1).getAttendanceType()).isEqualTo(LATE);
    }


}
