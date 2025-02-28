package domain;

import exception.AppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;

class CheckInHistoryTest {

    CheckInHistory history;
    FixedDateProvider fixedDateProvider = FixedDateProvider.of(LocalDate.of(2024, 12, 13));
    LocalDate now = fixedDateProvider.now();

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
        assertThatCode(() -> history.modifyCheckInTime(modifyDate, modifyTime)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("수정하려는 시간이 기존과 같을 경우 예외 발생")
    void modifySameTimeException() {
        //given
        CheckInDate modifyDate = CheckInDate.of(2024, 12, 3);
        CheckInTime modifyTime = CheckInTime.of(10, 0);
        //when
        //then
        assertThatThrownBy(() -> history.modifyCheckInTime(modifyDate, modifyTime))
                .isInstanceOf(AppException.class)
                .hasMessageContaining(AppException.PREFIX);
    }

    @Test
    @DisplayName("출석 횟수를 정상적으로 카운트")
    void countPresenceTest() {
        //given
        //when
        int absenceCount = history.countPresence(now);
        //then
        assertThat(absenceCount).isEqualTo(1);
    }

    @Test
    @DisplayName("지각 횟수를 정상적으로 카운트")
    void countLateTest() {
        //given
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 4);
        CheckInTime checkInTime = CheckInTime.of(10, 6);
        history.checkIn(checkInDate, checkInTime);
        //when
        int lateCount = history.countLate(now);
        //then
        assertThat(lateCount).isEqualTo(1);
    }
/*
    @Test
    @DisplayName("결석 횟수를 정상적으로 카운트")
    void countAbsenceTest() {
        //given
        CheckInDate checkInDate = CheckInDate.of(2024, 12, 2);
        CheckInTime checkInTime = CheckInTime.of(13, 31);
        history.checkIn(checkInDate, checkInTime);
        //when
        int absenceCount = history.countAbsence(now);
        //then
        assertThat(absenceCount).isEqualTo(8);
    }*/
}