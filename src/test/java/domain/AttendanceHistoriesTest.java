package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceHistoriesTest {

    @Test
    @DisplayName("출석 결과 카운팅 오늘 날짜 제외 테스트")
    void historiesTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 16, 9, 0),
                LocalDateTime.of(2024, 12, 17, 10, 6),
                LocalDateTime.of(2024, 12, 18, 10, 35));
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime standard = LocalDateTime.of(2024, 12, 18, 10, 35);
        // when
        Map<AttendanceResult, Integer> result = attendanceHistories.getAttendanceResultCount(standard);
        // then
        assertThat(result.get(AttendanceResult.LATE)).isEqualTo(1);
        assertThat(result.get(AttendanceResult.ABSENCE)).isEqualTo(10);
        assertThat(result.get(AttendanceResult.ATTENDANCE)).isEqualTo(1);
    }

    @ParameterizedTest
    @CsvSource({
            "5,정상",
            "6,정상",
            "9,경고",
            "10,면담",
            "11,면담",
            "12,면담",
            "13,제적"
    })
    @DisplayName("출석 결과 카운팅에 따른 제적 위험자 조회 테스트")
    void classifyAbsenceLevel(int day, String expected) {
        // given
        LocalDate testDate = LocalDate.of(2024, 12, day);
        List<LocalDateTime> historiesTimes = List.of(
                LocalDateTime.of(2024, 12, 2, 10, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0)
        );
        LocalDateTime standard = LocalDateTime.of(2024, 12, day, 10, 35);

        // when
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, testDate);
        String result = attendanceHistories.classifyAbsenceLevel(standard).getLevel();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("출석 기록 존재 확인 테스트 - TRUE")
    void hasHistoryTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 18, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2025, 12, 18, 11, 50);
        // when
        boolean check = attendanceHistories.hasHistory(time);
        // then
        assertThat(check).isTrue();
    }

    @Test
    @DisplayName("출석 기록 존재 확인 테스트 - FALSE")
    void hasNotHistoryTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2024, 12, 18, 11, 50);
        // when
        boolean check = attendanceHistories.hasHistory(time);
        // then
        assertThat(check).isFalse();
    }

    @Test
    @DisplayName("기록 삭제 테스트 - 정상")
    void deleteHistory() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 17));
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 11, 50);
        // when
        attendanceHistories.deleteHistory(time);
        // then
        boolean check = attendanceHistories.hasHistory(time);
        assertThat(check).isFalse();
    }

    @Test
    @DisplayName("기록 삭제 테스트 - 예외(기록이 없는 경우)")
    void deleteHistory_Exception() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2024, 12, 18, 11, 50);
        // when & then
        assertThatThrownBy(() -> attendanceHistories.deleteHistory(time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 날짜 출석 기록이 없습니다.");
    }

    @Test
    @DisplayName("출석 날짜 변경 테스트")
    void editHistoryTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime time = LocalDateTime.of(2024, 12, 17, 11, 50);
        LocalDateTime standard = LocalDateTime.of(2024, 12, 18, 11, 50);
        // when
        attendanceHistories.editHistory(time);
        // then
        List<AttendanceHistory> historiesResult = attendanceHistories.getSortedHistories(standard);
        List<LocalDateTime> dateList = historiesResult.stream().map(AttendanceHistory::getAttendanceTime).toList();
        boolean editContain = dateList.contains(time);
        assertThat(editContain).isEqualTo(true);
    }

    @Test
    @DisplayName("출석 기록 추가 - 예외(이미 출석한 경우)")
    void addHistory_Exception() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 40);
        // when & then
        assertThatThrownBy(() -> attendanceHistories.addHistory(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석하셨습니다.");
    }

    @Test
    @DisplayName("출석 기록 추가 - 정상")
    void addHistoryTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 18, 10, 40);
        // when
        attendanceHistories.addHistory(attendanceTime);
        // then
        assertThat(attendanceHistories.hasHistory(attendanceTime)).isTrue();
    }

    @Test
    @DisplayName("특정 날짜 출석 확인 - 테스트")
    void getTodayHistoryResult() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        // when
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime todayTime = LocalDateTime.of(2024, 12, 12, 10, 40);
        // then
        assertThat(attendanceHistories.getHistoryResult(todayTime)).isEqualTo("지각");
    }

    @Test
    @DisplayName("특정 날짜 출석 확인 - 예외 테스트")
    void getTodayHistoryResult_Exception() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        // when
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        LocalDateTime todayTime = LocalDateTime.of(2024, 12, 18, 10, 40);
        // then
        assertThatThrownBy(() -> attendanceHistories.getHistoryResult(todayTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("특정 날짜 출석 조회")
    void getHistoryTest() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        // when
        LocalDateTime time = attendanceHistories.getHistory(LocalDateTime.of(2024, 12, 12, 0, 0));
        // then
        assertThat(time).isEqualTo(LocalDateTime.of(2024, 12, 12, 10, 9));
    }

    @Test
    @DisplayName("특정 날짜 출석 조회 - 예외")
    void getHistoryTest_Exception() {
        // given
        List<LocalDateTime> historiesTimes = List.of(LocalDateTime.of(2024, 12, 13, 10, 9),
                LocalDateTime.of(2024, 12, 11, 10, 9),
                LocalDateTime.of(2024, 12, 12, 10, 9),
                LocalDateTime.of(2024, 12, 17, 10, 40)

        );
        AttendanceHistories attendanceHistories = new AttendanceHistories(historiesTimes, LocalDate.of(2024, 12, 18));
        // when & then
        assertThatThrownBy(() -> attendanceHistories.getHistory(LocalDateTime.of(2024, 12, 18, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 해당 날짜 출석 기록이 없습니다.");
    }
}
