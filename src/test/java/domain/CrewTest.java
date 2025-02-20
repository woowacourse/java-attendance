package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루_제적인_상태_확인() {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 2, 19, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 3, 19, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 4, 19, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 5, 14, 58)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 6, 14, 58)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 9, 14, 58)));

        Crew crew = new Crew("메이");
        Assertions.assertThat(crew.getExpelStatus(attendanceTimes)).isEqualTo(true);
    }

        @Test
    void 크루_제적_아닌_상태_확인() {
        List<AttendanceTime> attendanceTimes = new ArrayList<>();
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 2, 14, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 3, 9, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 4, 9, 0)));
        attendanceTimes.add(new AttendanceTime(LocalDateTime.of(2024, 12, 5, 9, 58)));

        Crew crew = new Crew("메이");
        Assertions.assertThat(crew.getExpelStatus(attendanceTimes)).isEqualTo(false);
    }
}
