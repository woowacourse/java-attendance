package model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
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
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("출석 정보 수정 후 수정 시간 확인 테스트")
    void 출석_정보_수정_후_수정_시간_확인_테스트() {
        //given
        LocalDate recordData = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.parse("10:59");

        int modifyDate = 13;
        LocalTime modifyTime = LocalTime.parse("10:00");

        LocalTime expect = LocalTime.of(10, 0);
        student.registerAttendanceRecord(recordData, attendanceTime);
        //when
        student.modifyAttendanceRecord(modifyDate, modifyTime);
        //then
        LocalTime result = student.findAttendanceLocalTimeByLocalDate(recordData);
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("학생의 지각 횟수 확인 테스트")
    void 학생의_지각_횟수_확인_테스트() {
        long expect = 1;

        long result = student.calculateLateCount();

        assertEquals(expect, result);
    }

    @Test
    @DisplayName("오늘을 기준으로 출석 기록이 없다면 결석 처리한다.")
    void 오늘을_기준으로_출석_기록이_없다면_결석_처리() {
        LocalDate today = LocalDate.of(2024, 12, 13);
        AttendanceStatus expect = AttendanceStatus.ABSENT;
        student.updateNonAttendanceRecordStatusIsAbsent(today);
        LocalTime localTime = student.findAttendanceLocalTimeByLocalDate(LocalDate.of(2024,12,12));
        AttendanceStatus result = AttendanceStatus.calculateAttendanceStatus(today, localTime);
        assertEquals(expect, result);
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

    @Test
    @DisplayName("지각 횟수 가져오기 테스트")
    void 지각_횟수_확인_테스트(){
        List<LocalDateTime> testLocalDateTime = List.of(
                LocalDateTime.of(2024,12,13,10,7),
                LocalDateTime.of(2024,12,12,10,6),
                LocalDateTime.of(2024,12,11,10,0),
                LocalDateTime.of(2024,12,10,10,8)
        );
        Student testStudent = new Student("테스트", testLocalDateTime);
        long expect = 3;
        long result = testStudent.calculateLateCount();
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("순수 결석 횟수 가져오기 테스트")
    void 순수_결석_횟수_확인_테스트(){
        List<LocalDateTime> testLocalDateTime = List.of(
                LocalDateTime.of(2024,12,13,10,31),
                LocalDateTime.of(2024,12,12,10,6),
                LocalDateTime.of(2024,12,11,10,31),
                LocalDateTime.of(2024,12,10,10,8)
        );
        Student testStudent = new Student("테스트", testLocalDateTime);
        long expect = 2;
        long result = testStudent.calculateAbsentCount();
        assertEquals(expect, result);
    }

    @Test
    @DisplayName("지각 3회는 결석1회로 전환해서 결석 횟수 가져오기 테스트")
    void 지각_3회_결석_1회로_전환_후_결석_횟수_확인_테스트(){
        List<LocalDateTime> testLocalDateTime = List.of(
                LocalDateTime.of(2024,12,13,10,31),
                LocalDateTime.of(2024,12,12,10,6),
                LocalDateTime.of(2024,12,11,10,9),
                LocalDateTime.of(2024,12,10,10,8),
                LocalDateTime.of(2024,12,9,10,10)
        );
        Student testStudent = new Student("테스트", testLocalDateTime);
        long expect = 2;
        long result = testStudent.calculateTotalAbsentCount();
        assertEquals(expect, result);
    }

}
