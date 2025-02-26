package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class CheckInHistoryTest {
    @Test
    @DisplayName("출석 기록부에 내역을 정상적으로 추가")
    void addCheckInTimeTest() {
        //given
        CheckInHistory history = CheckInHistory.of(new TreeSet<CheckInDateTime>());
        CheckInDateTime checkInDateTime = CheckInDateTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)
        );
        //when
        history.checkIn(checkInDateTime);
        //then
        assertThat(history.getCheckInCount()).isEqualTo(1);
    }
}