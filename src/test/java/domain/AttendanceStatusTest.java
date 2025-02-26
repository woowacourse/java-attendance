package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceStatusTest {
    @Test
    @DisplayName("수업 시작 시간 이전에 체크인 하면 출석으로 정상 반환")
    void beforeClasspresenceStatusTest() {
        //given
        LocalTime classStartTime = LocalTime.of(10, 0);
        LocalTime checkInTime = LocalTime.of(9, 0);
        //when
        AttendanceStatus status = AttendanceStatus.getAttendanceStatus(classStartTime, checkInTime);
        //then
        assertThat(status).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @Test
    @DisplayName("수업 시작 시간 5분 이내에 체크인 하면 출석으로 정상 반환")
    void afterClasspresenceStatusTest() {
        //given
        LocalTime classStartTime = LocalTime.of(10, 0);
        LocalTime checkInTime = LocalTime.of(10, 4);
        //when
        AttendanceStatus status = AttendanceStatus.getAttendanceStatus(classStartTime, checkInTime);
        //then
        assertThat(status).isEqualTo(AttendanceStatus.PRESENCE);
    }

    @Test
    @DisplayName("수업 시작 시간 이후 5분 초과 30분 이하에 체크인 하면 지각으로 정상 반환")
    void lateStatusTest() {
        //given
        LocalTime classStartTime = LocalTime.of(10, 0);
        //when
        //then
        assertAll(
                () -> assertThat(getAttendanceStatus(classStartTime, LocalTime.of(10, 6)))
                        .isEqualTo(AttendanceStatus.LATE),
                () -> assertThat(getAttendanceStatus(classStartTime, LocalTime.of(10, 30)))
                        .isEqualTo(AttendanceStatus.LATE)
        );
    }

    @Test
    @DisplayName("수업 시작 시간 30분 이후에 체크인 하면 결석으로 정상 반환")
    void absenceStatusTest() {
        //given
        LocalTime classStartTime = LocalTime.of(10, 0);
        LocalTime checkInTime = LocalTime.of(10, 31);
        //when
        AttendanceStatus status = AttendanceStatus.getAttendanceStatus(classStartTime, checkInTime);
        //then
        assertThat(status).isEqualTo(AttendanceStatus.ABSENCE);
    }
}