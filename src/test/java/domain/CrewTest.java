package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("크루 객체 생성 테스트")
    void test1() {
        //given
        final String name = "윌슨";

        //when
        final Crew crew = new Crew(name);
        final CrewName crewName = crew.getName();

        //then
        assertThat(crewName.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("크루에 출석 시간을 추가하는 테스트")
    void test2() {
        //given
        final String name = "윌슨";
        final String time = "2024-12-13 10:08";
        final Crew crew = new Crew(name);
        final LocalDateTime expectedTime = LocalDateTime.of(2024, 12, 13, 10, 8, 0);

        //when
        crew.addAttendance(time);
        final List<Attendance> attendances = crew.getAttendances();

        //then
        assertThat(attendances.size()).isEqualTo(1);
        assertThat(attendances.getFirst().getDateTime()).isEqualTo(expectedTime);
    }

    @Test
    @DisplayName("크루의 출석 통계를 반환하는 테스트")
    void test3() {
        //given
        final String name = "윌슨";
        final String attendancedTime = "2024-12-13 10:05";
        final String latedTime = "2024-12-13 10:30";
        final String absencedTime = "2024-12-13 10:35";
        final Crew crew = new Crew(name);

        //when
        crew.addAttendance(attendancedTime);
        crew.addAttendance(latedTime);
        crew.addAttendance(absencedTime);
        final Map<AttendanceStatus, Integer> actualStatistics = crew.calculateAttendanceStatistics();

        //then
        assertThat(actualStatistics.size()).isEqualTo(3);
        assertThat(actualStatistics)
                .containsEntry(AttendanceStatus.ATTENDANCE, 1)
                .containsEntry(AttendanceStatus.LATE, 1)
                .containsEntry(AttendanceStatus.ABSENCE, 1);
    }

    @Test
    @DisplayName("크루의 제적 위험성을 계산하는 테스트")
    void test4() {
        //given
        final String name = "윌슨";
        final List<String> absences = List.of("2024-12-12 10:35", "2024-12-11 10:35", "2024-12-10 10:35");
        final Crew crew = new Crew(name);
        absences.forEach(crew::addAttendance);

        //when
        ExpulsionStatus actual = crew.calculateExpulsionStatus();

        //then
        assertThat(actual).isEqualTo(ExpulsionStatus.INTERVIEW);

    }

}
