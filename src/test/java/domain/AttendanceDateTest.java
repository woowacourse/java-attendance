package domain;

import static org.assertj.core.api.Assertions.*;

import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceState;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceDateTest {
    @DisplayName("휴일일 때 출석일자 생성이 안 된다")
    @Test
    void test() {
        // given
        LocalDateTime christmasDateTime = LocalDateTime.of(2024, 12, 25, 0, 0);

        // when && then
        assertThatThrownBy(() -> new AttendanceDate(christmasDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("12월 25일 수요일은 등교일이 아닙니다.");
    }

    @DisplayName("주말일 때 출석일자 생성이 안 된다")
    @Test
    void test1() {
        LocalDateTime weekendDateTime = LocalDateTime.of(
                2025,
                2,
                16,
                0,
                0);

        assertThatThrownBy(() -> new AttendanceDate(weekendDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("02월 16일 일요일은 등교일이 아닙니다.");
    }

    @DisplayName("평일일 때 출석일자 생성")
    @Test
    void test2() {
        LocalDateTime weekDaysDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                0,
                0);

        assertThat(new AttendanceDate(weekDaysDateTime)).isInstanceOf(AttendanceDate.class);
    }

    @DisplayName("Attendance Date 5분 이내 출석여부 확인")
    @Test
    void test3() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                13,
                5);
        AttendanceDate attendanceDate = new AttendanceDate(localDateTime);

        //when
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.ATTENDANCE);
    }

    @DisplayName("Attendance Date 30분 이내 지각여부 확인")
    @Test
    void test4() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                13,
                30);
        AttendanceDate attendanceDate = new AttendanceDate(localDateTime);

        //when
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.TARDY);
    }

    @DisplayName("Attendance Date 30분 이내 결석여부 확인")
    @Test
    void test5() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(
                2025,
                2,
                17,
                13,
                31);
        AttendanceDate attendanceDate = new AttendanceDate(localDateTime);

        //when
        AttendanceState attendanceState = attendanceDate.calculateAttendanceState();

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.ABSENCE);
    }

    @DisplayName("isEqualsLocalDate 테스트")
    @Test
    void test6(){
        AttendanceDate attendanceDate = new AttendanceDate(LocalDateTime.of(
                2025,
                1,
                2,
                3,
                4));
        assertThat(attendanceDate.isEqualsLocalDate(LocalDate.of(2025,1,2))).isTrue();
    }

    @DisplayName("editDateTime 테스트")
    @Test
    void test7(){
        AttendanceDate attendanceDate = new AttendanceDate(LocalDateTime.of(
                2025,
                1,
                2,
                3,
                4));
        attendanceDate.editDateTime(LocalDateTime.of(
                2025,
                1,
                1,
                2,
                3));

        assertThat(attendanceDate.isEqualsLocalDate(LocalDate.of(2025,1,1))).isTrue();
    }

    @DisplayName("!equals 테스트")
    @Test
    void test7_1(){
        AttendanceDate attendanceDate = new AttendanceDate(LocalDateTime.of(
                2025,
                1,
                2,
                3,
                4));
        attendanceDate.editDateTime(LocalDateTime.of(
                2025,
                1,
                3,
                2,
                3));

        assertThat(attendanceDate.isEqualsLocalDate(LocalDate.of(2025,1,1))).isFalse();
    }
}
