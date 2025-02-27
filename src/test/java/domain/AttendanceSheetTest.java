package domain;

import domain.policy.AbsentPolicy;
import domain.policy.AttendanceState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static domain.policy.AttendanceState.*;
import static domain.policy.ExpellState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceSheetTest {
    AttendanceSheet attendanceSheet;
    LocalDate today;

    @BeforeEach
    void setUp() {
        today = LocalDate.of(2024, 12, 11);
        attendanceSheet = new AttendanceSheet(new AbsentPolicy(),
                new ArrayList<>(
                        List.of(
                                new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크2", LocalDate.of(2024, 12, 8), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크2", LocalDate.of(2024, 12, 9), LocalTime.of(10,5), ATTENDANCE),
                                new Attendance("링크2", LocalDate.of(2024, 12, 10), LocalTime.of(10,10), LATE),
                                new Attendance("링크2", LocalDate.of(2024, 12, 11), LocalTime.of(10,10), LATE),
                                new Attendance("링크3", LocalDate.of(2024, 12, 2), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크3", LocalDate.of(2024, 12, 3), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크3", LocalDate.of(2024, 12, 4), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크3", LocalDate.of(2024, 12, 5), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크3", LocalDate.of(2024, 12, 6), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 2), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 3), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 4), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 5), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 6), LocalTime.of(13,5), ATTENDANCE),
                                new Attendance("링크4", LocalDate.of(2024, 12, 9), LocalTime.of(13,5), ATTENDANCE)
                        ))
        );
    }

    @Test
    @DisplayName("닉네임과 등교 날짜, 등교 시간을 입력하면 출석 기록을 추가할 수 있다")
    public void attendTest() {
        //given
        String nickname = "링크";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(10,0);

        //when-then
        assertDoesNotThrow(() -> attendanceSheet.add(nickname, date, time));
    }

    @Test
    @DisplayName("이미 출석한 경우 다시 출석할 수 없으며 수정 기능을 이용하도록 안내하는 예외가 발생한다")
    public void alreadyAttendTest() {
        //given
        String nickname = "링크";
        LocalDate date = LocalDate.of(2024, 12, 10);

        //when-then
        assertThatThrownBy(() -> attendanceSheet.validateIsAlreadyAttendance(nickname, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석 확인을 수정할 수 있다")
    public void updateAttendanceTest() {
        //given
        String nickname = "링크";
        int dayOfMonth = 10;
        LocalTime updateTime = LocalTime.of(11,0);

        //when-then
        assertDoesNotThrow(() -> attendanceSheet.update(nickname, dayOfMonth, updateTime));
    }

    @Test
    @DisplayName("출석 기록이 없을 때 수정하려고 하면 예외가 발생한다")
    public void attendNotFoundTest() {
        //given
        String nickname = "링크";
        int dayOfMonth = 15;
        LocalTime updateTime = LocalTime.of(11,0);

        //when-then
        assertThatThrownBy(() -> attendanceSheet.update(nickname, dayOfMonth, updateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("크루의 출석 상태 횟수를 계산할 수 있다")
    @MethodSource("provideAttendanceStateForCount")
    public void countAttendanceStateTest(AttendanceState state, int expected) {
        assertThat(attendanceSheet.countAttendanceState("링크", today).get(state)).isEqualTo(expected);
    }

    static Stream<Arguments> provideAttendanceStateForCount() {
        return Stream.of(
                Arguments.of(ATTENDANCE, 1),
                Arguments.of(LATE, 0),
                Arguments.of(ABSENT, 6)
        );
    }

    @Test
    @DisplayName("모든 크루의 출석 상태 횟수를 계산할 수 있다")
    public void countAttendancesStateTest() {
        assertSoftly(softly -> {
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크").get(ATTENDANCE)).isEqualTo(1);
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크").get(LATE)).isEqualTo(0);
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크").get(ABSENT)).isEqualTo(6);
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크2").get(ATTENDANCE)).isEqualTo(2);
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크2").get(LATE)).isEqualTo(2);
            softly.assertThat(attendanceSheet.countAttendancesState(today).get("링크2").get(ABSENT)).isEqualTo(3);
        });
    }

    @Test
    @DisplayName("크루의 제적 상태를 알 수 있다")
    public void countExpellTest() {
        //given
        Map<String, Map<AttendanceState, Long>> attendances = attendanceSheet.countAttendancesState(today);

        //when-then
        assertSoftly(softly -> {
            softly.assertThat(attendanceSheet.checkExpellStatus(attendances).get("링크")).isEqualTo(EXPELL);
            softly.assertThat(attendanceSheet.checkExpellStatus(attendances).get("링크2")).isEqualTo(INTERVIEW);
            softly.assertThat(attendanceSheet.checkExpellStatus(attendances).get("링크3")).isEqualTo(WARNING);
            softly.assertThat(attendanceSheet.checkExpellStatus(attendances).get("링크4")).isEqualTo(NONE);
        });
    }
}
