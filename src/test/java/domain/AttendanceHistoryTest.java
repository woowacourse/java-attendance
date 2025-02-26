package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {
    @Test
    void hasSameDateTest1() {
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 26, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 2, 26, 20, 9);

        Crew crew = Crew.from("히스타");

        Assertions.assertThat(AttendanceHistory.of(crew, dateTime1).hasSameDate(dateTime2)).isTrue();
    }

    @Test
    void hasSameDateTest2() {
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 26, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 2, 27, 20, 9);

        Crew crew = Crew.from("히스타");

        Assertions.assertThat(AttendanceHistory.of(crew, dateTime1).hasSameDate(dateTime2)).isFalse();
    }

    @Test
    void hasSameCrewTest1() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, LocalDateTime.now());

        Assertions.assertThat(history.hasSameCrew(crew)).isTrue();
    }

    @Test
    void hasSameCrewTest2() {
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, LocalDateTime.now());

        Assertions.assertThat(history.hasSameCrew(Crew.from("히로"))).isFalse();
    }
}
