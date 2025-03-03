package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CheckInTimesTest {
    private CheckInTimes checkInTimes;

    @BeforeEach
    void setUp() {
        checkInTimes = createCheckInTimes();
    }

    @Test
    @DisplayName("출석 기록들을 확인하여 출석 횟수 카운트")
    void countPresenceTest() {
        int count = checkInTimes.countPresence();
        assertThat(count).isEqualTo(3);
    }

    @Test
    @DisplayName("출석 기록들을 확인하여 지각 횟수 카운트")
    void countLateTest() {
        int count = checkInTimes.countLate();
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("출근 시간 추가")
    void addCheckInTimeTest() {
        CheckInTime now = CheckInTime.of(LocalDateTime.of(2024, 12, 12, 10, 0));

        checkInTimes.add(now);

        assertThat(checkInTimes.countPresence()).isEqualTo(4);
    }

    @Test
    @DisplayName("이미 출근 기록이 존재하므로 출석시 예외 발생")
    void addDuplicateCheckInTimeTest() {
        CheckInTime now = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 0));

        assertThatThrownBy(() -> checkInTimes.add(now))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출근 시간 수정")
    void modifyCheckInTimeTest() {
        CheckInTime modifiedTime = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 20));

        checkInTimes.modify(modifiedTime);

        assertThat(checkInTimes.countPresence()).isEqualTo(2);
        assertThat(checkInTimes.countLate()).isEqualTo(3);
    }

    @Test
    @DisplayName("특정 날짜를 기준으로 이전의 출석 기록을 반환")
    void getAttendanceLogTest() {
        LocalDateTime now = LocalDateTime.of(2024, 12, 14, 10, 20);

        List<LocalDateTime> log = checkInTimes.getAttendanceLog(now);

        assertThat(log).hasSize(6);
    }

    CheckInTimes createCheckInTimes() {
        CheckInTime presence1 = CheckInTime.of(LocalDateTime.of(2024, 12, 3, 10, 0));
        CheckInTime presence2 = CheckInTime.of(LocalDateTime.of(2024, 12, 4, 10, 1));
        CheckInTime presence3 = CheckInTime.of(LocalDateTime.of(2024, 12, 5, 10, 5));
        CheckInTime late1 = CheckInTime.of(LocalDateTime.of(2024, 12, 6, 10, 6));
        CheckInTime late2 = CheckInTime.of(LocalDateTime.of(2024, 12, 10, 10, 30));
        CheckInTime absence1 = CheckInTime.of(LocalDateTime.of(2024, 12, 11, 10, 31));
        return CheckInTimes.of(List.of(presence1, presence2, presence3, late1, late2, absence1));
    }
}