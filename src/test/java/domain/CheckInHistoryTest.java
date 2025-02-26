package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

class CheckInHistoryTest {
    @Test
    @DisplayName("출석 기록부에 내역을 정상적으로 추가")
    void addCheckInTimeTest() {
        //given
        CheckInHistory history = CheckInHistory.of(List.of());
        CheckInTime checkInTime = CheckInTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)
        );
        //when
        history.checkIn(checkInTime);
        //then
        assertThat(history.getCheckInCount()).isEqualTo(1);
    }
}