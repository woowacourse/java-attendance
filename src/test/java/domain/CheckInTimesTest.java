package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CheckInTimesTest {

    @Test
    @DisplayName("출석 기록들을 확인하여 출석 횟수 카운트")
    void countPresenceTest(){
        CheckInTimes checkInTimes = createCheckInTimes();
        int count = checkInTimes.countPresence();
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 기록들을 확인하여 지각 횟수 카운트")
    void countLateTest(){
        CheckInTimes checkInTimes = createCheckInTimes();
        int count = checkInTimes.countLate();
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("출석 기록들을 확인하여 결석 횟수 카운트")
    void countAbsenceTest(){
        CheckInTimes checkInTimes = createCheckInTimes();
        int count = checkInTimes.countAbsence();
        assertThat(count).isEqualTo(1);
    }

    CheckInTimes createCheckInTimes(){
        CheckInTime presence1 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 0));
        CheckInTime presence2 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 1));
        CheckInTime presence3 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 5));
        CheckInTime late1 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 6));
        CheckInTime late2 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 30));
        CheckInTime absence1 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 31));
        return CheckInTimes.of(List.of(presence1, presence2, presence3, late1, late2, absence1));
    }
}