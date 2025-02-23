package domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceHistoryTest {

    @ParameterizedTest
    @CsvSource({"5,출석", "10,지각", "30,지각", "31,결석"})
    @DisplayName("출석 결과 테스트")
    void attendanceResultTest(int minutes, String expected) {
        // given
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 10, minutes);
        AttendanceHistory attendanceHistory = new AttendanceHistory(time);
        // when
        String result = attendanceHistory.getAttendanceResult();
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("휴일일 경우 예외 발생 테스트")
    void validateHoliday() {
        // given
        LocalDateTime time = LocalDateTime.of(2025, 2, 15, 10, 7);
        // when & then
        assertThatThrownBy(() -> new AttendanceHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("등교일이 아닙니다.");
    }

    @ParameterizedTest
    @DisplayName("캠퍼스 등록 시간이 아닌 경우 예외 발생 테스트")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 23})
    void validateOpeningCompany(int hour) {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 2, 16, hour, 7);
        // when & then
        assertThatThrownBy(() -> new AttendanceHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 캠퍼스 운영 시간은 08:00~23:00 입니다.");
    }


    @Test
    @DisplayName("특정 날짜 이전 기록인지 확인하는 메서드 테스트")
    void isBeforeHistoryTest() {
        // given
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 10, 7);
        AttendanceHistory attendanceHistory = new AttendanceHistory(time);
        LocalDateTime testTime1 = LocalDateTime.of(2024, 12, 17, 10, 7);
        LocalDateTime testTime2 = LocalDateTime.of(2024, 12, 18, 10, 7);
        // when
        boolean check1 = attendanceHistory.isBeforeHistory(testTime1);
        boolean check2 = attendanceHistory.isBeforeHistory(testTime2);
        // then
        assertThat(check1).isFalse();
        assertThat(check2).isTrue();
    }
}
