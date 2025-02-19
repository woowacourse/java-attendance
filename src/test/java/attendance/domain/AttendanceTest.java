package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {


    @DisplayName("출석 정보 저장 성공 ")
    @Test
    void test14() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        assertThatCode(() -> attendance.add(localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("출석 정보 저장 실패")
    @Test
    void test15() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");

        attendance.add(localDateTime);

        assertThatThrownBy(() -> attendance.add(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @DisplayName("출석 정보 수정 시 기존 시간, 분 반환")
    @Test
    void test16() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        HourMinute hourMinute = new HourMinute(newLocalDateTime);

        HourMinute prevHourMinute = attendance.modify(newLocalDateTime.toLocalDate(), hourMinute);

        assertThat(prevHourMinute.hour()).isEqualTo(localDateTime.getHour());
        assertThat(prevHourMinute.minute()).isEqualTo(localDateTime.getMinute());
    }

    @DisplayName("출석 날짜가 존재하지 않는 경우 false 반환")
    @Test
    void test17() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 22);

        boolean result = attendance.hasTimeStamp(localDate);

        assertThat(result)
                .isFalse();
    }

    @DisplayName("출석 날짜가 존재하는 경우 true 반환")
    @Test
    void test18() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23,13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 23);

        boolean result = attendance.hasTimeStamp(localDate);

        assertThat(result)
                .isTrue();
    }

    @DisplayName("출석, 지각, 결석 횟수 조회")
    @Test
    void test19() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 5, 10, 6);
        LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 6, 10, 1);

        Attendance attendance = new Attendance("빙티");
        attendance.add(localDateTime1);
        attendance.add(localDateTime2);
        attendance.add(localDateTime3);
        attendance.add(localDateTime4);

        Map<AttendanceStatus, Integer> attendanceStatuses = attendance.countAttendanceStatus(6);

        assertThat(attendanceStatuses.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatuses.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatuses.get(ABSENCE)).isEqualTo(1);
    }
}
