package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceTest {
    @DisplayName("출석 정보 저장 성공 ")
    @Test
    void test14() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");

        assertThatCode(() -> crewAttendance.add(localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("출석 정보 저장 실패")
    @Test
    void test15() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");

        crewAttendance.add(localDateTime);

        assertThatThrownBy(() -> crewAttendance.add(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @DisplayName("출석 정보 수정 성공")
    @Test
    void test16() {
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        LocalDate targetDate = LocalDate.of(2024, 12, 23);
        LocalTime prevTime = LocalTime.of(13, 3);
        LocalTime newTime = LocalTime.of(13, 1);

        crewAttendance.add(LocalDateTime.of(targetDate, prevTime));
        AttendanceTimeStatus prevAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);
        Map<LocalDate, AttendanceTimeStatus> prevAttendances =
                crewAttendance.getAttendancesBefore(targetDate.plusDays(1));

        crewAttendance.modify(LocalDateTime.of(targetDate, newTime));
        AttendanceTimeStatus newAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);
        Map<LocalDate, AttendanceTimeStatus> newAttendances =
                crewAttendance.getAttendancesBefore(targetDate.plusDays(1));

        assertThat(prevAttendanceTimeStatus.time().orElseThrow()).isEqualTo(prevTime);
        assertThat(newAttendanceTimeStatus.time().orElseThrow()).isEqualTo(newTime);
        assertThat(prevAttendances).hasSameSizeAs(newAttendances);
    }

    @DisplayName("출석 날짜가 존재하지 않는 경우 예외 발생")
    @Test
    void test17() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 22);

        assertThatCode(() -> crewAttendance.getAttendanceOn(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @DisplayName("출석 날짜가 존재하는 경우 해당 날짜의 AttendanceTimeStatus 반환")
    @Test
    void test18() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);

        LocalDate localDate = LocalDate.of(2024, 12, 23);

        AttendanceTimeStatus result = crewAttendance.getAttendanceOn(localDate);

        assertThat(result).isNotNull().isInstanceOf(AttendanceTimeStatus.class);
        assertThat(result.time().orElseThrow()).isEqualTo(localDateTime.toLocalTime());
    }

    @DisplayName("출석, 지각, 결석 횟수 조회")
    @Test
    void test19() {
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 5, 10, 6);
        LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 6, 10, 1);

        CrewAttendance crewAttendance = new CrewAttendance("빙티");
        crewAttendance.add(localDateTime1);
        crewAttendance.add(localDateTime2);
        crewAttendance.add(localDateTime3);
        crewAttendance.add(localDateTime4);

        Map<AttendanceStatus, Integer> attendanceStatusCounts =
                crewAttendance.countAttendanceStatusBefore(LocalDate.of(2024, 12, 6));

        assertThat(attendanceStatusCounts.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatusCounts.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatusCounts.get(ABSENCE)).isEqualTo(1);
    }
}
