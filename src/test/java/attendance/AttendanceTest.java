package attendance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.AttendanceManager;

public class AttendanceTest {

    @AfterEach
    void afterTest() {
        AttendanceManager.initiateInstance();
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면, 출석할 수 있다.")
    void test_attendance() {

    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력하면, 예외가 발생된다.")
    void error_notRegisteredNickname() {

    }

    @Test
    @DisplayName("올바르지 않은 시간 형식에 대한, 예외가 발생한다.")
    void error_wrongTimeFormat() {

    }

    @Test
    @Disabled
    @DisplayName("출석 시간보다 5분 초과되어 출석할 때, 지각 처리된다.")
    void test_attendanceOfLate() {

    }

    @Test
    @DisplayName("출석 시간보다 30분 초과되어 출석할 때, 결석 처리된다.")
    void test_attendanceOfAbsence() {

    }

    @Test
    @Disabled
    @DisplayName("월요일에 출석할 경우, 출석 시간이 13시부터이다.")
    void test_attendanceOnMonday() {
    }

    @Test
    @Disabled
    @DisplayName("월요일에 5분 늦게 출석할 경우, 지각 처리된다.")
    void test_attendanceOfLateOnMonday() {
    }

    @Test
    @Disabled
    @DisplayName("월요일에 30분 늦게 출석할 경우, 결석 처리된다.")
    void test_attendanceOfAbsenceOnMonday() {
    }

    @Test
    @DisplayName("출석할 때, 시간의 형식은 24시간 형식이다.")
    void test_attendanceTimeFormat() {

    }

    @Test
    @DisplayName("다시 출석할 경우, 예외가 발생한다.")
    void error_retireAttendance() {

    }

    @Test
    @DisplayName("출석하는 날짜가 주말인 경우, 예외가 발생한다.")
    void error_attendanceOnWeekend() {

    }

    @Test
    @DisplayName("캠퍼스 운영시간 외에 출석할 경우, 예외가 발생한다.")
    void error_attendanceOutOfRangeOnSchedule() {

    }

    @Test
    @DisplayName("출석하는 날짜가 공휴일인 경우, 예외가 발생한다.")
    void error_attendanceOnHoliday() {
        //given&when

        //then
    }
}
