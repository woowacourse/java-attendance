package domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 크루_닉네임으로_크루를_올바르게_찾아온다() {
        String input = "2024-12-13 10:08";
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(input);
        Attendance attendance = new Attendance(attendanceDateTime);
        List<Attendance> attendanceList = List.of(attendance);
        Attendances attendances = new Attendances(new LinkedList<>(attendanceList));
        Crew crew = new Crew(new Nickname("밍키"), attendances, AttendanceCounter.of(attendances));

        List<Crew> crewList = new ArrayList<>();
        crewList.add(crew);
        Crews crews = new Crews(crewList);

        Crew foundCrew = crews.findByNickname(crew.getNickname());

        Assertions.assertThat(foundCrew).isEqualTo(crew);
    }
}
