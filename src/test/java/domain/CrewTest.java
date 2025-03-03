package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewTest {

    private Crew crew;

    @BeforeEach
    void beforeEach(){
        List<LocalDateTime> histories = List.of(LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0));
        crew = new Crew("a", histories, LocalDate.of(2024, 12, 5));
    }

    @Test
    @DisplayName("이름 5글자 이상이면 예외가 발생한다.")
    void crewNameLengthTest() {
        assertThatThrownBy(() -> new Crew("aaaaaa", List.of(), LocalDate.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름은 최대 5글자입니다.");
    }

    @ParameterizedTest
    @CsvSource({"5,false", "6,false", "7,true", "8,true"})
    @DisplayName("제적 대상자인지 확인하는 메서드 테스트")
    void isExpulsionTargetTest(int day, boolean expected) {
        // given
        List<LocalDateTime> histories = List.of(LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0));
        Crew crew = new Crew("a", histories, LocalDate.of(2024, 12, day));
        LocalDate standard = LocalDate.of(2024, 12, day);
        // when
        boolean isExpulsionTarget = crew.isExpulsionTarget(standard);
        // then
        assertThat(isExpulsionTarget).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("정상적으로 출석 기록을 추가하면 결과를 반환한다.")
    void addAttendanceHistoryTest(LocalDateTime attendanceTime, AttendanceResult expected) {
        // when
        AttendanceResult result = crew.addAttendanceHistory(attendanceTime);
        // then
        assertThat(result).isEqualTo(expected);
    }

    public static Stream<Arguments> addAttendanceHistoryTest(){
        return Stream.of(
                Arguments.of(LocalDateTime.of(2024, 12, 9, 12, 50), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 6), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 9, 13, 31), AttendanceResult.ABSENCE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 9, 50), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 10, 0), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 10, 5), AttendanceResult.ATTENDANCE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 10, 6), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 10, 30), AttendanceResult.LATE),
                Arguments.of(LocalDateTime.of(2024, 12, 5, 10, 31), AttendanceResult.ABSENCE)
        );
    }

    @Test
    @DisplayName("이미 기록이 있는데 추가하려하면 예외가 발생한다.")
    void addAttendanceHistoryException(){
        assertThatThrownBy(()->crew.addAttendanceHistory(LocalDateTime.of(2024,12,3,10,5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 오늘 이미 출석을 하셨습니다. 수정 메뉴로 이동해주세요!");
    }

    @Test
    @DisplayName("해당 날짜 출석 기록 조회 기능 테스트")
    void findAttendanceHistoryTest(){
        // given
        LocalDate standard = LocalDate.of(2024,12,3);
        // when
        AttendanceHistory attendanceHistory = crew.findAttendanceHistory(standard);
        // then
        assertThat(attendanceHistory.getAttendanceDate()).isEqualTo(standard);
        assertThat(attendanceHistory.getAttendanceTime()).isPresent();
        assertThat(attendanceHistory.getAttendanceTime().get()).isEqualTo(LocalTime.of(10,0));
        assertThat(attendanceHistory.getAttendanceResult()).isEqualTo(AttendanceResult.ATTENDANCE);
    }

    @Test
    @DisplayName("해당 날짜 출석 기록 조회 예외 테스트(기록이 존재하지 않을 때)")
    void findAttendanceHistoryException(){
        // given
        LocalDate standard = LocalDate.of(2024,12,20);
        // when & then
        assertThatThrownBy(()->crew.findAttendanceHistory(standard))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] 기록이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("특정 날짜 수정 기능 테스트")
    void editAttendanceHistoryTest(){
        // given
        LocalDateTime editTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        // when
        AttendanceResult result = crew.editAttendanceHistory(editTime);
        // then
        assertThat(result).isEqualTo(AttendanceResult.LATE);
        AttendanceHistory attendanceHistory = crew.findAttendanceHistory(editTime.toLocalDate());
        assertThat(attendanceHistory.getAttendanceTime().get()).isEqualTo(LocalTime.of(10,6));
    }

    @Test
    @DisplayName("특정 날짜 수정 예외 테스트(기존 기록이 존재하지 않을 때)")
    void editAttendanceHistoryException(){
        // given
        LocalDateTime editTime = LocalDateTime.of(2024, 12, 20, 10, 6);
        // when
        assertThatThrownBy(()->crew.editAttendanceHistory(editTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 기록이 존재하지 않습니다.");
    }

    @Test
    @DisplayName("기록 요약 정보 조회 테스트")
    void getAttendanceAnalyzeTest(){
        // given
        LocalDate standard = LocalDate.of(2024, 12, 4);
        // when
        AttendanceAnalyze attendanceAnalyze = crew.getAttendanceAnalyze(standard);
        // then
        assertThat(attendanceAnalyze.getLateCount()).isEqualTo(0);
        assertThat(attendanceAnalyze.getAttendanceCount()).isEqualTo(2);
        assertThat(attendanceAnalyze.getAbsenceCount()).isEqualTo(0);
        assertThat(attendanceAnalyze.getAttendanceHistories().size()).isEqualTo(2);
    }
}
