package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewAttendanceTest {
    @DisplayName("신규 출석 기록 추가 성공")
    @Test
    void test1() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.parse("10:00");
        LocalDateTime attendance = LocalDateTime.of(date, time);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThat(crewAttendance.hasRecord(attendance)).isTrue();
    }

    @DisplayName("신규 출석 기록 추가 시 출석 상태 계산 및 저장 성공")
    @ParameterizedTest
    @CsvSource({"3,10:00,PRESENT", "3,10:05,PRESENT", "3,10:06,LATE", "3,10:30,LATE", "3,10:31,ABSENT"})
    void test2(int dayOfMonth, String attendanceTime, String expectedStatus) {
        LocalDate date = LocalDate.of(2024, 12, dayOfMonth);
        LocalTime time = LocalTime.parse(attendanceTime);
        LocalDateTime attendance = LocalDateTime.of(date, time);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);
        Map<AttendanceRecord, AttendanceStatus> crewAttendances = crewAttendance.getAttendances();

        assertThat(crewAttendance.hasRecord(attendance)).isTrue();
        assertThat(crewAttendances.get(new AttendanceRecord(attendance)))
                .isEqualTo(AttendanceStatus.valueOf(expectedStatus));
    }

    @DisplayName("이미 존재하는 날짜의 출석 기록 추가 시 예외 발생")
    @Test
    void test3() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.parse("10:00");
        LocalDateTime attendance = LocalDateTime.of(date, time);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThatThrownBy(() -> crewAttendance.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
    }
}
