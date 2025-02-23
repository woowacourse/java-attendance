package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루_제적인_상태_확인() {
        List<LocalDateTime> attendanceDateTimes = new ArrayList<>();
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 2, 19, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 3, 19, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 4, 19, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 5, 14, 58));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 6, 14, 58));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 9, 14, 58));
        AttendanceTimes attendanceTimes = new AttendanceTimes(attendanceDateTimes, LocalDate.of(2024, 12, 10));

        Crew crew = new Crew("메이");
        Assertions.assertThat(crew.isExpelled(attendanceTimes)).isEqualTo(true);
    }

    @Test
    void 크루_제적_아닌_상태_확인() {
        List<LocalDateTime> attendanceDateTimes = new ArrayList<>();
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 2, 9, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 3, 9, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 4, 9, 0));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 5, 9, 58));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 6, 9, 58));
        attendanceDateTimes.add(LocalDateTime.of(2024, 12, 9, 9, 58));
        AttendanceTimes attendanceTimes = new AttendanceTimes(attendanceDateTimes, LocalDate.of(2024, 12, 10));

        Crew crew = new Crew("메이");
        Assertions.assertThat(crew.isExpelled(attendanceTimes)).isEqualTo(false);
    }


}
