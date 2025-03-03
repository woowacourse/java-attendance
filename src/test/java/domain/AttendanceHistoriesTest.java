package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceHistoriesTest {

    private AttendanceHistories attendanceHistories;

    @BeforeEach
    void beforeEach() {
        attendanceHistories = new AttendanceHistories(List.of(), LocalDate.of(2024, 12, 1));
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적으로 출석 기록을 추가하면 결과를 반환한다.")
    void createAttendanceTest(LocalDateTime attendanceTime, AttendanceResult expected) {
        AttendanceResult result = attendanceHistories.addAttendanceHistory(attendanceTime);
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> createAttendanceTest() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 23, 12, 50), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 6), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 23, 13, 31), AttendanceResult.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 9, 50), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 6), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 24, 10, 31), AttendanceResult.ABSENCE)
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("캠퍼스 운영 시간 외 시간으로 입력하면 예외 발생한다.")
    void createAttendanceTest_Exception(LocalDateTime attendanceTime) {
        assertThatThrownBy(() -> attendanceHistories.addAttendanceHistory(attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }

    public static Stream<Arguments> createAttendanceTest_Exception() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 19, 7, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 6, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 5, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 4, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 23, 59)),
                Arguments.of(LocalDateTime.of(2024, 12, 19, 23, 1))
        );
    }

    @Test
    @DisplayName("이미 출석한 날짜면 예외를 발생한다.")
    void isSameDateTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 24, 10, 31);
        attendanceHistories.addAttendanceHistory(attendanceTime);
        LocalDateTime addAttendanceTime = LocalDateTime.of(2024, 12, 24, 10, 31);
        // when & then
        assertThatThrownBy(() -> attendanceHistories.addAttendanceHistory(addAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 오늘 이미 출석을 하셨습니다. 수정 메뉴로 이동해주세요!");
    }

    @Test
    @DisplayName("해당 날짜 기록 가져오기")
    void getAttendanceHistory() {
        // given
        AttendanceHistories histories = new AttendanceHistories(List.of(
                LocalDateTime.of(2024, 12, 23, 10, 0),
                LocalDateTime.of(2024, 12, 26, 10, 0),
                LocalDateTime.of(2024, 12, 27, 10, 0)), LocalDate.of(2024, 12, 28));
        LocalDate findDate = LocalDate.of(2024, 12, 23);
        AttendanceHistory expected = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 10, 0));
        // when
        AttendanceHistory history = histories.findAttendanceHistoryByDate(findDate);
        // then
        assertThat(history).isEqualTo(expected);
    }

    @Test
    @DisplayName("해당 날짜 기록 가져오기 - 출석 기록이 없으면 예외가 발생한다.")
    void getAttendanceHistory_Exception() {
        // given
        AttendanceHistories histories = new AttendanceHistories(List.of(
                LocalDateTime.of(2024, 12, 23, 10, 0),
                LocalDateTime.of(2024, 12, 26, 10, 0),
                LocalDateTime.of(2024, 12, 27, 10, 0)), LocalDate.of(2024, 12, 28));
        LocalDate findDate = LocalDate.of(2024, 12, 28);
        // when & then
        assertThatThrownBy(() -> histories.findAttendanceHistoryByDate(findDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기록이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("날짜 수정 기능 테스트")
    void editAttendanceHistory() {
        // given
        AttendanceHistories histories = new AttendanceHistories(List.of(
                LocalDateTime.of(2024, 12, 23, 13, 0),
                LocalDateTime.of(2024, 12, 26, 10, 0),
                LocalDateTime.of(2024, 12, 27, 10, 0)), LocalDate.of(2024, 12, 28));
        LocalDateTime editTime = LocalDateTime.of(2024, 12, 23, 13, 10);
        AttendanceHistory expected = new AttendanceHistory(LocalDateTime.of(2024, 12, 23, 13, 10));
        // when
        AttendanceResult result = histories.editAttendanceHistory(editTime);
        // then
        AttendanceHistory findHistory = histories.findAttendanceHistoryByDate(editTime.toLocalDate());
        assertThat(result).isEqualTo(AttendanceResult.LATE);
        assertThat(findHistory).isEqualTo(expected);
    }

    @Test
    @DisplayName("csv 파일에 특정 날짜가 없으면 결석으로 처리한다.")
    void readCsvAndCreateHistories() {
        // given & when
        AttendanceHistories histories = new AttendanceHistories(List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0)), LocalDate.of(2024, 12, 6));
        AttendanceHistory expected = new AttendanceHistory(LocalDate.of(2024, 12, 5), null);
        // then
        AttendanceHistory attendanceHistory = histories.findAttendanceHistoryByDate(LocalDate.of(2024, 12, 5));
        assertThat(attendanceHistory).isEqualTo(expected);
    }

    @Test
    @DisplayName("특정 시점 이전의 기록 통계 조회 기능 테스트")
    void getBeforeAttendanceAnalyze() {
        // given
        AttendanceHistories histories = new AttendanceHistories(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 2, 13, 7),
                LocalDateTime.of(2024, 12, 4, 10, 0)), LocalDate.of(2024, 12, 13));
        LocalDate standard = LocalDate.of(2024, 12, 7);
        // when
        AttendanceAnalyze analyze = histories.getAttendanceAnalyze(standard);
        // then
        assertThat(analyze.getLateCount()).isEqualTo(1);
        assertThat(analyze.getAbsenceCount()).isEqualTo(2);
        assertThat(analyze.getAttendanceCount()).isEqualTo(2);
        List<AttendanceHistory> findHistories = analyze.getAttendanceHistories();
        assertThat(findHistories).containsExactly(new AttendanceHistory(LocalDateTime.of(2024, 12, 2, 13, 7)),
                new AttendanceHistory(LocalDateTime.of(2024, 12, 3, 10, 0)),
                new AttendanceHistory(LocalDateTime.of(2024, 12, 4, 10, 0)),
                new AttendanceHistory(LocalDate.of(2024,12,5),null),
                new AttendanceHistory(LocalDate.of(2024,12,6),null));
    }

}
