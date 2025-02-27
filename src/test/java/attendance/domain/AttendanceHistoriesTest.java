package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static attendance.error.ErrorMessage.ALREADY_EXIST_ATTENDANCE;
import static attendance.error.ErrorMessage.NOT_EXIST_ATTENDANCE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoriesTest {

    @DisplayName("이미 출석을 한 날짜에 중복해서 출석한다면, 예외를 발생시킨다.")
    @Test
    void already_attendance_again_attendance_then_throw_exception() {
        //given
        AttendanceHistories attendanceHistories = AttendanceHistories.create();
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 20, 10, 5);
        LocalDateTime duplicateDateTime = LocalDateTime.of(2025, 2, 20, 10, 0);
        AttendanceHistory attendanceHistory = AttendanceHistory.from(localDateTime);
        AttendanceHistory duplicateAttendanceHistory = AttendanceHistory.from(duplicateDateTime);

        //when, then
        attendanceHistories.addAttendanceHistory(attendanceHistory);
        assertThatThrownBy(
            () -> attendanceHistories.addAttendanceHistory(duplicateAttendanceHistory))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ALREADY_EXIST_ATTENDANCE.getMessage());
    }

    @DisplayName("날짜가 주어졌을 때, 해당하는 날짜에 출근 기록이 없다면 예외를 발생시킨다.")
    @Test
    void when_given_date_but_not_exist_attendance_then_throw_exception() {
        //given
        AttendanceHistories attendanceHistories = AttendanceHistories.create();
        LocalDate findDate = LocalDate.of(2024, 2, 20);

        //when, then
        assertThatThrownBy(() -> attendanceHistories.getAttendanceHistoryByDate(findDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_EXIST_ATTENDANCE.getMessage());
    }

    @DisplayName("날짜가 주어졌을 때, 해당 날짜에 속하는 출근 기록을 가져온다.")
    @Test
    void given_date_then_return_attendance() {
        AttendanceHistories attendanceHistories = AttendanceHistories.create();
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 10, 11, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 11, 10, 0);
        AttendanceHistory attendanceHistory1 = AttendanceHistory.from(localDateTime1);
        AttendanceHistory attendanceHistory2 = AttendanceHistory.from(localDateTime2);

        attendanceHistories.addAttendanceHistory(attendanceHistory1);
        attendanceHistories.addAttendanceHistory(attendanceHistory2);

        LocalDate findDate = LocalDate.of(2024, 12, 10);
        AttendanceHistory foundAttendanceHistory = attendanceHistories.getAttendanceHistoryByDate(
            findDate);
        assertThat(foundAttendanceHistory.getAttendanceTime().isSameDate(findDate)).isTrue();
        assertThat(foundAttendanceHistory.getAttendanceType()).isEqualTo(ABSENCE);
    }

    @DisplayName("수정할 날짜가 주어졌을 경우, 출석 기록을 수정한다.")
    @Test
    void given_modify_date_then_modify_attendance_result() {
        AttendanceHistories attendanceHistories = AttendanceHistories.create();

        LocalDateTime currentAttendanceTime = LocalDateTime.of(2024, 12, 11, 11, 0);
        AttendanceHistory currentAttendanceHistory = AttendanceHistory.from(currentAttendanceTime);
        attendanceHistories.addAttendanceHistory(currentAttendanceHistory);

        AttendanceHistory beforeDate = attendanceHistories.getAttendanceHistoryByDate(
            LocalDate.of(2024, 12, 11));

        LocalDateTime modifyAttendanceTime = LocalDateTime.of(2024, 12, 11, 10, 0);

        attendanceHistories.modifyAttendanceResult(modifyAttendanceTime);

        AttendanceHistory afterDate = attendanceHistories.getAttendanceHistoryByDate(
            LocalDate.of(2024, 12, 11));

        AttendanceType beforeAttendanceType = beforeDate.getAttendanceType();
        AttendanceType afterAttendanceType = afterDate.getAttendanceType();

        assertThat(beforeDate).isNotEqualTo(afterDate);
        assertThat(beforeAttendanceType).isEqualTo(ABSENCE);
        assertThat(afterAttendanceType).isEqualTo(ATTENDANCE);
    }
}
