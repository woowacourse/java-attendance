package attendance;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.AttendanceManager;

public class AttendanceModifyTest {

    @AfterEach
    void afterTest() {
        AttendanceManager.initiateInstance();
    }

    @Test
    @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
    void test_modifyAttendance() {
        //given&when

        //then
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력할 경우, 예외가 발생한다.")
    void error_notRegisteredNickname() {
        //given&when

        //then
    }

    @Test
    @DisplayName("잘못된 날짜 형식을 입력할 경우, 예외가 발생한다")
    void error_wrongDateFormat() {
        //given&when

        //then
    }

    @Test
    @DisplayName("잘못된 시간 형식을 입력할 경우, 예외가 발생한다.")
    void error_wrongTimeFormat() {
        //given&when

        //then
    }

    @Test
    @DisplayName("출석 상태를, 지각으로 수정한다.")
    void test_modifyStateToLate() {
        //given&when

        //then
    }

    @Test
    @DisplayName("출석 상태를, 결석으로 수정한다.")
    void test_modifyStateToAbsence() {
        //given&when

        //then
    }

    @Test
    @DisplayName("수정 시간이 캠퍼스 운영 시간 이외일 경우, 예외를 발생한다.")
    void error_outOfRangeOnCampusSchedule() {
        //given&when

        //then
    }

    @Test
    @DisplayName("수정 날짜가 주말일 경우, 예외를 발생한다.")
    void error_modifyOnWeekend() {
        //given&when

        //then
    }

    @Test
    @DisplayName("미래 날짜에 대해 수정할 경우, 예외가 발생한다.")
    void error_modifyFutureAttendance() {
        //given&when

        //then
    }

    @Test
    @DisplayName("월요일의 출석 상태를, 지각으로 수정한다.")
    void error_modifyToLateOnMonday() {
        //given&when

        //then
    }

    @Test
    @DisplayName("월요일의 출석 상태를, 결석으로 수정한다.")
    void error_modifyToAbsenceOnMonday() {
        //given&when

        //then
    }

    @Test
    @DisplayName("수정하는 날짜가 공휴일인 경우, 예외를 발생한다.")
    void error_modifyOnHoliday() {
        //given&when

        //then
    }
}
