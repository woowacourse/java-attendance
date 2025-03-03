package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
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
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일을 제외한 입력 받은 출석 시간 5분 이후 도착한 경우 지각 상태 가져오기")
    void 입력_받은_지각_시간으로_지각_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime input = LocalTime.parse("10:06");

        AttendanceStatus expect = AttendanceStatus.LATE;
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일을 제외한 입력 받은 출석 시간을 30분 이후 도착한 경우 결석 상태 가져오기")
    void 입력_받은_결석_시간으로_결석_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime input = LocalTime.parse("10:31");
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("월요일인 경우 입력 받은 출석 시간으로 출석 상태 가져오기")
    void 월요일의_경우_입력_받은_출석_시간으로_상태_가져오기() {
        LocalDate today = LocalDate.of(2024, 12, 9);
        LocalTime input = LocalTime.parse("13:00");

        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(today, input);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 횟수 계산하기")
    void 출석_횟수_계산_테스트(){
        Map<LocalDate, LocalTime> testMap = Map.of(
                LocalDate.of(2024,12,13), LocalTime.of(10,0),
                LocalDate.of(2024,12,12), LocalTime.of(10,0),
                LocalDate.of(2024,12,11), LocalTime.of(10,6),
                LocalDate.of(2024,12,10), LocalTime.of(10,0),
                LocalDate.of(2024,12,9), LocalTime.of(13,0));
        long expect = 4;
        long result = AttendanceStatus.calculateAttendanceStatusCount(testMap, AttendanceStatus.ATTENDANCE);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("지각 횟수 계산하기")
    void 지각_횟수_계산_테스트(){
        Map<LocalDate, LocalTime> testMap = Map.of(
                LocalDate.of(2024,12,13), LocalTime.of(10,0),
                LocalDate.of(2024,12,12), LocalTime.of(10,0),
                LocalDate.of(2024,12,11), LocalTime.of(10,6),
                LocalDate.of(2024,12,10), LocalTime.of(10,0),
                LocalDate.of(2024,12,9), LocalTime.of(13,0));
        long expect = 1;
        long result = AttendanceStatus.calculateAttendanceStatusCount(testMap, AttendanceStatus.LATE);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("결설 횟수 계산하기")
    void 결석_횟수_계산_테스트(){
        Map<LocalDate, LocalTime> testMap = Map.of(
                LocalDate.of(2024,12,13), LocalTime.of(10,31),
                LocalDate.of(2024,12,12), LocalTime.of(10,50),
                LocalDate.of(2024,12,11), LocalTime.of(10,6),
                LocalDate.of(2024,12,10), LocalTime.of(10,0),
                LocalDate.of(2024,12,9), LocalTime.of(13,0));
        long expect = 2;
        long result = AttendanceStatus.calculateAttendanceStatusCount(testMap, AttendanceStatus.ABSENT);
        Assertions.assertEquals(expect, result);
    }
}
