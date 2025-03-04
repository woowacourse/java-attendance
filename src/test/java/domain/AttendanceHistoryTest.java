package domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {

    @DisplayName("hasSameDate() - 같은 날짜이면 True")
    @Test
    void hasSameDateTest1() {
        // given
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 26, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 2, 26, 20, 9);
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, dateTime1);

        // when
        boolean result = history.hasSameDate(dateTime2);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("hasSameDate() - 다른 날짜(하루 차이)이면 False")
    @Test
    void hasSameDateTest2() {
        // given
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 26, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 2, 27, 20, 9);
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, dateTime1);

        // when
        boolean result = history.hasSameDate(dateTime2);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("hasSameDate() - 다른 월이면 False")
    @Test
    void hasSameDateTest3() {
        // given
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 27, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2025, 3, 27, 20, 9);
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, dateTime1);

        // when
        boolean result = history.hasSameDate(dateTime2);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("hasSameDate() - 다른 연도이면 False")
    @Test
    void hasSameDateTest4() {
        // given
        LocalDateTime dateTime1 = LocalDateTime.of(2025, 2, 27, 20, 9);
        LocalDateTime dateTime2 = LocalDateTime.of(2024, 2, 27, 20, 9);
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, dateTime1);

        // when
        boolean result = history.hasSameDate(dateTime2);

        // then
        Assertions.assertThat(result).isFalse();
    }

    @DisplayName("hasSameCrew() - 동일한 Crew이면 True")
    @Test
    void hasSameCrewTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, LocalDateTime.now(ZoneId.of("Asia/Seoul")));

        // when
        boolean result = history.hasSameCrew(crew);

        // then
        Assertions.assertThat(result).isTrue();
    }

    @DisplayName("hasSameCrew() - 다른 Crew이면 False")
    @Test
    void hasSameCrewTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory history = AttendanceHistory.of(crew, LocalDateTime.now(ZoneId.of("Asia/Seoul")));
        Crew differentCrew = Crew.from("히로");

        // when
        boolean result = history.hasSameCrew(differentCrew);

        // then
        Assertions.assertThat(result).isFalse();
    }
}
