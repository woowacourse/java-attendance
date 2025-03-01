package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StudentTest {

    private final Student student = new Student("빙티", Arrays
            .asList(LocalDateTime.of(2024, 12, 2, 13, 0),
                    LocalDateTime.of(2024, 12, 3, 10, 7)));

    @Test
    @DisplayName("출석 등록 시간 확인 테스트")
    void 출석_등록_시간_확인_테스트() {
        LocalDate todayDate = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");

        LocalTime expect = LocalTime.of(10, 59);

        student.registerAttendanceRecord(todayDate, attendanceTime);
        LocalTime result = student.findAttendanceLocalTimeByLocalDate(todayDate);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 등록 후 출석 상태 확인 테스트")
    void 출석_등록_후_출석_상태_확인_테스트() {
        LocalDate todayDate = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");

        AttendanceStatus expect = AttendanceStatus.ABSENT;

        student.registerAttendanceRecord(todayDate, attendanceTime);
        AttendanceStatus result = student.findAttendanceStatusByLocalDate(todayDate);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 정보 수정 후 수정 시간 확인 테스트")
    void 출석_정보_수정_후_수정_시간_확인_테스트() {
        //given
        LocalDate recordData = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");

        String modifyDate = "13";
        LocalTime modifyTime = LocalTime.parse("10:00");

        LocalTime expect = LocalTime.of(10, 0);
        student.registerAttendanceRecord(recordData, attendanceTime);
        //when
        student.modifyAttendanceRecord(modifyDate, modifyTime);
        //then
        LocalTime result = student.findAttendanceLocalTimeByLocalDate(recordData);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 정보 수정 후 수정 출결 상태 확인 테스트")
    void 출석_정보_수정_후_수정_출결_상태_확인_테스트() {
        //given
        LocalDate recordData = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");

        String modifyDate = "13";
        LocalTime modifyTime = LocalTime.parse("10:00");

        AttendanceStatus expect = AttendanceStatus.ATTENDANCE;
        student.registerAttendanceRecord(recordData, attendanceTime);
        //when
        student.modifyAttendanceRecord(modifyDate, modifyTime);
        //then
        AttendanceStatus result = student.findAttendanceStatusByLocalDate(recordData);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("학생의 출결 횟수 확인 테스트")
    void 학생의_출석_횟수_확인_테스트() {
        long expect = 1;
        student.updateAttendanceCount();
        long result = student.attendanceStatusCount.attendanceStatusCount.get(AttendanceStatus.ATTENDANCE);

        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("학생의 지각 횟수 확인 테스트")
    void 학생의_지각_횟수_확인_테스트() {
        long expect = 1;

        student.updateAttendanceCount();
        long result = student.attendanceStatusCount.attendanceStatusCount.get(AttendanceStatus.LATE);

        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("지각 3회는 결석 1회로 간주하여 계산하기 테스트")
    void 지각_3회는_결석_1회로_간주() {
        LocalDate todayDate1 = LocalDate.of(2024, 12, 13);
        LocalDate todayDate2 = LocalDate.of(2024, 12, 12);
        LocalDate todayDate3 = LocalDate.of(2024, 12, 11);
        LocalTime attendanceTime1 = LocalTime.parse("10:06");
        long expect = 1;
        student.registerAttendanceRecord(todayDate1, attendanceTime1);
        student.registerAttendanceRecord(todayDate2, attendanceTime1);
        student.registerAttendanceRecord(todayDate3, attendanceTime1);

        long result = student.convertTardiesToAbsence();
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("지각 6회는 결석 2회로 간주하여 계산하기 테스트")
    void 지각_6회는_결석_2회로_간주() {
        LocalDate todayDate1 = LocalDate.of(2024, 12, 13);
        LocalDate todayDate2 = LocalDate.of(2024, 12, 12);
        LocalDate todayDate3 = LocalDate.of(2024, 12, 11);
        LocalDate todayDate4 = LocalDate.of(2024, 12, 6);
        LocalDate todayDate5 = LocalDate.of(2024, 12, 5);
        LocalDate todayDate6 = LocalDate.of(2024, 12, 4);
        LocalTime attendanceTime = LocalTime.parse("10:06");

        long expect = 2;
        student.registerAttendanceRecord(todayDate1, attendanceTime);
        student.registerAttendanceRecord(todayDate2, attendanceTime);
        student.registerAttendanceRecord(todayDate3, attendanceTime);
        student.registerAttendanceRecord(todayDate4, attendanceTime);
        student.registerAttendanceRecord(todayDate5, attendanceTime);
        student.registerAttendanceRecord(todayDate6, attendanceTime);

        long result = student.convertTardiesToAbsence();
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("오늘을 기준으로 출석 기록이 없다면 결석 처리한다.")
    void 오늘을_기준으로_출석_기록이_없다면_결석_처리() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        student.updateNonAttendanceRecordStatusIsAbsent(today);
        AttendanceStatus result = student.findAttendanceStatusByLocalDate(today);
        Assertions.assertEquals(expect, result);
    }

    @Test
    @DisplayName("이미 출석 기록이 있을 경우 출석 시도 시 예외 테스트")
    void 이미_출석_기록이_존재하는_경우() {
        LocalDate todayDate = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");
        LocalTime duplicateAttendanceTime = LocalTime.parse("11:02");

        student.registerAttendanceRecord(todayDate, attendanceTime);

        assertThatThrownBy(() -> student.registerAttendanceRecord(todayDate, duplicateAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석기록이 존재합니다.");
    }
}
