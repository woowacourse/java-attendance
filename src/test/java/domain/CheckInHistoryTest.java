package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.assertThat;

class CheckInHistoryTest {

    CheckInHistory history;

    @BeforeEach
    void setUp() {
        CheckInDateTime checkInDateTime = CheckInDateTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)
        );
        TreeSet<CheckInDateTime> dateTimes = new TreeSet<>();
        dateTimes.add(checkInDateTime);
        history = CheckInHistory.of(dateTimes);
    }

    @Test
    @DisplayName("출석 기록부에 내역을 정상적으로 추가")
    void addCheckInTimeTest() {
        //given
        CheckInDateTime checkInDateTime = CheckInDateTime.of(
                LocalDate.of(2024, 12, 4), LocalTime.of(10, 0)
        );
        //when
        history.checkIn(checkInDateTime);
        //then
        assertThat(history.getCheckInCount()).isEqualTo(2);
    }

/*    @Test
    @DisplayName("당일에 이미 출석 기록이 있는 경우 예외 발생")
    void alreadyCheckInExceptionTest() {
        //given
        CheckInDateTime checkInDateTime = CheckInDateTime.of()
        //when
        //then
    }*/
}