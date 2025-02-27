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
import java.util.stream.Stream;

import static domain.policy.AttendanceState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceSheetTest {
    AttendanceSheet attendanceSheet;

    @BeforeEach
    void setUp() {
        attendanceSheet = new AttendanceSheet(new AbsentPolicy(),
                new ArrayList<>(
                        List.of(new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(10,0), ATTENDANCE),
                                new Attendance("링크", LocalDate.of(2024, 12, 11), LocalTime.of(10,0), ATTENDANCE),
                                new Attendance("링크", LocalDate.of(2024, 12, 12), LocalTime.of(10,10), LATE)
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
        LocalDate date = LocalDate.of(2024, 12, 11);

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
        assertThat(attendanceSheet.countAttendanceState("링크").get(state)).isEqualTo(expected);
    }

    static Stream<Arguments> provideAttendanceStateForCount() {
        return Stream.of(
                Arguments.of(ATTENDANCE, 2),
                Arguments.of(LATE, 1),
                Arguments.of(ABSENT, 6)
        );
    }

    @Test
    @DisplayName("제적 위험자를 확인할 수 있다")
    public void countAttendancesStateTest() {
        //given
        //2 3 4 5 6
        //9 10 11 12 13
        LocalDate today = LocalDate.of(2024, 12, 13);
        attendanceSheet = new AttendanceSheet(new AbsentPolicy(),
                new ArrayList<>(
                        List.of(new Attendance("링크", LocalDate.of(2024, 12, 9), LocalTime.of(13,10), LATE),
                                new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(10,10), LATE),
                                new Attendance("링크", LocalDate.of(2024, 12, 11), LocalTime.of(10,10), LATE),
                                new Attendance("링크", LocalDate.of(2024, 12, 12), LocalTime.of(10,10), LATE)
                        ))
        );

        //when-then
        assertThat(attendanceSheet.countAttendancesState().get("링크").get(LATE)).isEqualTo(4);
        assertThat(attendanceSheet.countAttendancesState().get("링크").get(ABSENT)).isEqualTo(5);
    }
}
