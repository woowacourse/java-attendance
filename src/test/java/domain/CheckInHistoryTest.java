package domain;

import exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;

class CheckInHistoryTest {

    CheckInHistory history;

    @BeforeEach
    void setUp() {
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 3);
        CheckInTime checkInTime = CheckInTime.of(10, 0);
        TreeMap<CheckInDate, CheckInTime> dateAndTimes = new TreeMap<>();
        dateAndTimes.put(checkInDate, checkInTime);
        history = CheckInHistory.of(dateAndTimes);
    }

    @Test
    @DisplayName("출석 기록부에 내역을 정상적으로 추가")
    void addCheckInTimeTest() {
        //given
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 4);
        CheckInTime checkInTime = CheckInTime.of(10, 0);
        //when
        history.checkIn(checkInDate, checkInTime);
        //then
        assertThat(history.getCheckInCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("당일에 이미 출석 기록이 있는 경우 예외 발생")
    void alreadyCheckInExceptionTest() {
        //given
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 3);
        CheckInTime checkInTime = CheckInTime.of(11, 0);
        //when
        //then
        assertThatThrownBy(() -> history.checkIn(checkInDate, checkInTime))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("출석 기록을 정상적으로 수정")
    void modifyCheckInTimeTest() {
        //given
        CheckInDate modifyDate = CheckInDate.of(2024, 12, 3);
        CheckInTime modifyTime = CheckInTime.of(11, 0);
        //when
        //then
        assertThatCode(() -> history.checkIn(modifyDate, modifyTime)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수정하려는 시간이 캠퍼스 오픈 시간이 아닐 경우 에외 발생")
    void nonCampusTimeException() {
        //given
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 3);
        CheckInTime checkInTime = CheckInTime.of(23, 10);
        //when
        //then
        assertThatThrownBy(() -> history.checkIn(checkInDate, checkInTime))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("수정하려는 시간이 기존과 같을 경우 예외 발생")
    void modifySameTimeException() {
        //given
        CheckInDate modifyDate = CheckInDate.of(2024, 12, 3);
        CheckInTime modifyTime = CheckInTime.of(10, 0);
        //when
        //then
        assertThatThrownBy(() -> history.checkIn(modifyDate, modifyTime))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }
}