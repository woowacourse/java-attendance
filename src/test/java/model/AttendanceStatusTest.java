package model;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {
    @Test
    @DisplayName("월요일을 제외한 입력 받은 출석 시간 전에 도착한 경우 출석 상태 가져오기")
    void 입력_받은_출석_시간으로_출석_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime input = LocalTime.parse("09:03");

        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        AttendanceStatus result = AttendanceStatus.attendanceStatusCalculate(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일을 제외한 입력 받은 출석 시간 5분 이후 도착한 경우 지각 상태 가져오기")
    void 입력_받은_지각_시간으로_지각_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime input = LocalTime.parse("10:06");

        AttendanceStatus expect = AttendanceStatus.LATE;
        AttendanceStatus result = AttendanceStatus.attendanceStatusCalculate(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일을 제외한 입력 받은 출석 시간을 30분 이후 도착한 경우 결석 상태 가져오기")
    void 입력_받은_결석_시간으로_결석_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime input = LocalTime.parse("10:31");
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        AttendanceStatus result = AttendanceStatus.attendanceStatusCalculate(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일인 경우 입력 받은 출석 시간으로 출석 상태 가져오기")
    void 월요일의_경우_입력_받은_출석_시간으로_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 9);
        LocalTime input = LocalTime.parse("13:00");

        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        AttendanceStatus result = AttendanceStatus.attendanceStatusCalculate(today, input);
        Assertions.assertEquals(expect, result);
    }
}
