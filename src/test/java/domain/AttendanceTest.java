package domain;

import static domain.Crew.*;
import static org.assertj.core.api.Assertions.*;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceTime;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("학생 한 명의 12월 2일부터 오늘까지의 출석부를 생성한다")
    @Test
    void test1() {
        // given & when
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        // then
        assertThat(attendance)
                .isInstanceOf(Attendance.class);
    }

    @DisplayName("학생 한 명의 결석횟수")
    @Test
    void test2() {
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.of(2025, 2, 17));
        assertThat(attendance.countAbsence()).isEqualTo(53);
    }

    @DisplayName("학생 한 명의 출석 횟수")
    @Test
    void test3() {
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        attendance.editAttendanceDateTime(LocalDateTime.of(2025, 2, 10, 10, 0));

        assertThat(attendance.countAttendance()).isEqualTo(1);
    }

    @DisplayName("학생 한 명의 지각 횟수")
    @Test
    void test4() {
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        attendance.editAttendanceDateTime(LocalDateTime.of(2025, 2, 18, 10, 10));

        assertThat(attendance.countTardy()).isEqualTo(1);
    }

    @DisplayName("지각 3번이면 결석 1번 입니다.")
    @Test
    void test4_1(){
        Attendance attendance = new Attendance(LocalDate.of(2025,2,20), LocalDate.now());

        attendance.editAttendanceDateTime(LocalDateTime.of(2025, 2, 20, 10, 6));
        attendance.editAttendanceDateTime(LocalDateTime.of(2025, 2, 21, 10, 6));
        attendance.editAttendanceDateTime(LocalDateTime.of(2025, 2, 24, 13, 6));

        assertThat(attendance.countAbsenceIncludingTardy()).isEqualTo(1);
    }

    @DisplayName("결석이 여섯 번 이상일 때 제적대상자임을 반환한다")
    @Test
    void test5() {
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(
                attendance.countAbsenceIncludingTardy());

        assertThat(attendanceWarning).isEqualTo(AttendanceWarning.WEEDING);
    }

    @DisplayName("결석이  번일 때 경고대상자임을 반환한다")
    @Test
    void test6() {
        Attendance attendance = new Attendance(DEFAULT_START_DATE
                , LocalDate.of(2024, 12, 10));

        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(
                attendance.countAbsenceIncludingTardy());

        assertThat(attendanceWarning).isEqualTo(AttendanceWarning.WEEDING);
    }

    @DisplayName("출석부의 출석일자를 업데이트 한다")
    @Test
    void test7() {
        // given
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.of(2024, 12, 3));
        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);

        // then
        attendance.editAttendanceDateTime(updateDateTime);

        // given
        assertThat(attendance.findAttendanceDate(DEFAULT_START_DATE).checkAttendanceTime())
                .isEqualTo(updateDateTime);
    }

    @DisplayName("미래의 경우 조회할 수 없다")
    @Test
    void test7_1(){
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        assertThatThrownBy(() -> attendance.findAttendanceDate(LocalDate.of(
                2025,
                5,
                6))).isInstanceOf(IllegalArgumentException.class);

    }

    @DisplayName("학생이 오늘 날짜에 출석한다")
    @Test
    void test8() {
        // given
        LocalDateTime nowDateTime = LocalDateTime.now();
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        // when
        attendance.attend(nowDateTime);

        // then
        assertThat(attendance.findAttendanceDate(nowDateTime.toLocalDate()))
                .isInstanceOf(AttendanceDate.class);
    }

    @DisplayName("학생은 미래에 출석할 수 없다")
    @Test
    void test9() {
        // given
        LocalDateTime tomorrowDateTime = LocalDateTime.now().plusDays(1);
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());

        // when & then
        assertThatThrownBy(() -> attendance.attend(tomorrowDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("아직 출석할 수 없습니다.");
    }

    @DisplayName("학생이 이미 출석했으면 다시 출석할 수 없다")
    @Test
    void test10() {
        // given
        LocalDateTime todayDateTime = LocalDateTime.now();
        Attendance attendance = new Attendance(DEFAULT_START_DATE, LocalDate.now());
        attendance.attend(todayDateTime);

        // when & then
        assertThatThrownBy(() -> attendance.attend(todayDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
    }

    @DisplayName("월요일의 경우 13시 5분까지 출석으로 인정된다")
    @Test
    void test11() {
        boolean isAttendance = AttendanceTime.isAttendance(
                1,
                LocalDateTime.of(
                    2025,
                    2,
                    17,
                    13,
                    5));
        assertThat(isAttendance).isTrue();
    }

    @DisplayName("월요일의 경우 13시 30분 이후 부터 결석이다")
    @Test
    void test12() {
        boolean isAbsence = AttendanceTime.isAbsence(1,
                LocalDateTime.of(2025, 2, 17, 13, 31));
        assertThat(isAbsence).isTrue();
    }

    @DisplayName("월요일을 제외한 평일은 10시 5분까지 출석해야 출석으로 인정된다")
    @Test
    void test13() {
        boolean isAttendance = AttendanceTime.isAttendance(2,
                LocalDateTime.of(2025, 2, 18, 10, 5));
        Assertions.assertThat(isAttendance).isTrue();
    }

    @DisplayName("월요일을 제외한 평일은 10시 30분 초과 출석하면 결석이다")
    @Test
    void test14() {
        boolean isAbsenceTrue = AttendanceTime.isAbsence(2,
                LocalDateTime.of(2025, 2, 18, 10, 31));
        Assertions.assertThat(isAbsenceTrue).isTrue();
    }
}
