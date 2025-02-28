package domain;

import static domain.AttendanceStatus.ABSENT;
import static domain.AttendanceStatus.ABSENT_THRESHOLD_MINUTES;
import static domain.AttendanceStatus.ATTEND;
import static domain.AttendanceStatus.EXCEPT_MONDAY_ATTEND_TIME;
import static domain.AttendanceStatus.LATE;
import static domain.AttendanceStatus.LATE_THRESHOLD_MINUTES;
import static domain.AttendanceStatus.MONDAY_ATTEND_TIME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    @DisplayName("월요일인 경우, 출석 상태를 올바르게 반환한다.")
    void attendanceStatusTest1() {
        LocalDate mondayDate = LocalDate.of(2024, 12, 2);
        LocalTime mondayAttendTime = MONDAY_ATTEND_TIME;
        LocalTime mondayLateTime = MONDAY_ATTEND_TIME.plusMinutes(LATE_THRESHOLD_MINUTES + 1);
        LocalTime mondayAbsentTime = MONDAY_ATTEND_TIME.plusMinutes(ABSENT_THRESHOLD_MINUTES + 1);

        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(mondayDate, mondayAttendTime)).isEqualTo(
                ATTEND.getMessage());
        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(mondayDate, mondayLateTime)).isEqualTo(
                LATE.getMessage());
        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(mondayDate, mondayAbsentTime)).isEqualTo(
                ABSENT.getMessage());
    }

    @Test
    @DisplayName("월요일이 아닌 경우, 출석 상태를 올바르게 반환한다.")
    void attendanceStatusTest2() {
        LocalDate exceptMondayDate = LocalDate.of(2024, 12, 3);
        LocalTime exceptMondayAttendTime = EXCEPT_MONDAY_ATTEND_TIME;
        LocalTime exceptMondayLateTime = EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(LATE_THRESHOLD_MINUTES + 1);
        LocalTime exceptMondayAbsentTime = EXCEPT_MONDAY_ATTEND_TIME.plusMinutes(ABSENT_THRESHOLD_MINUTES + 1);

        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(exceptMondayDate, exceptMondayAttendTime)).isEqualTo(
                ATTEND.getMessage());
        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(exceptMondayDate, exceptMondayLateTime)).isEqualTo(
                LATE.getMessage());
        assertThat(AttendanceStatus.findMessageByAttendDateAndTime(exceptMondayDate, exceptMondayAbsentTime)).isEqualTo(
                ABSENT.getMessage());
    }
}
