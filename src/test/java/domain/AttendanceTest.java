package domain;

import domain.attendance.Attendance;
import domain.attendance.AttendanceDate;
import domain.attendance.AttendanceWarning;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("학생 한 명의 12월 1일부터 오늘까지의 출석부를 생성한다")
    @Test
    void test1() {
        // given
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));

        // when
        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());

        // then
        Assertions.assertThat(attendance).isInstanceOf(Attendance.class);
    }

    @DisplayName("학생 한 명의 결석횟수")
    @Test
    void test2() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));

        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());

        Assertions.assertThat(attendance.countAbsence()).isEqualTo(54);
    }

    @DisplayName("학생 한 명의 출석 횟수")
    @Test
    void test3() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));

        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());

        Assertions.assertThat(attendance.countAttendance()).isEqualTo(1);
    }

    @DisplayName("학생 한 명의 지각 횟수")
    @Test
    void test4() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));

        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());

        Assertions.assertThat(attendance.countTardy()).isEqualTo(0);
    }

    @DisplayName("결석이 여섯 번 이상일 때 제적대상자임을 반환한다")
    @Test
    void test5() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));
        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.now());

        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(
                attendance.countAbsenceIncludingTardy());

        Assertions.assertThat(attendanceWarning).isEqualTo(AttendanceWarning.WEEDING);
    }

    @DisplayName("결석이 두 번일 때 경고대상자임을 반환한다")
    @Test
    void test6() {
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));
        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE
                , LocalDate.of(2024, 12, 5));

        AttendanceWarning attendanceWarning = AttendanceWarning.determineAttendanceWarning(
                attendance.countAbsenceIncludingTardy());

        Assertions.assertThat(attendanceWarning).isEqualTo(AttendanceWarning.WARNING);
    }

    @DisplayName("출석부의 출석일자를 업데이트 한다")
    @Test
    void test7() {
        // given
        Attendance attendance = new Attendance(AttendanceDate.DEFAULT_START_DATE, LocalDate.of(2024, 12, 3));
        LocalDateTime updateDateTime = LocalDateTime.of(2024, 12, 2, 10, 0);

        // then
        attendance.updateAttendanceDate(updateDateTime);

        // given
        Assertions.assertThat(attendance.findAttendanceDate(AttendanceDate.DEFAULT_START_DATE).checkAttendanceTime())
                .isEqualTo(updateDateTime);
    }
}
