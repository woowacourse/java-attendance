package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CrewAttendanceTest {
    @DisplayName("신규 출석 기록 추가 성공")
    @Test
    void test1() {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, 3, 10, 0);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThat(crewAttendance.isExistDay(attendance)).isTrue();
        assertThat(crewAttendance.getAttendanceOn(attendance)).isEqualTo(Attendance.of(attendance));
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

        assertThat(crewAttendance.getAttendanceOn(attendance)).isEqualTo(Attendance.of(attendance));
        assertThat(crewAttendance.getAttendanceOn(attendance).status())
                .isEqualTo(AttendanceStatus.valueOf(expectedStatus));
    }

    @DisplayName("이미 존재하는 날짜의 출석 기록 추가 시 예외 발생")
    @Test
    void test3() {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, 3, 10, 0);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThatThrownBy(() -> crewAttendance.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
    }

    @DisplayName("출석 기록 수정 성공")
    @Test
    void test4() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime prevTime = LocalTime.parse("10:07");
        LocalDateTime prevDateTime = LocalDateTime.of(date, prevTime);
        LocalTime newTime = LocalTime.parse("09:58");
        LocalDateTime newDateTime = LocalDateTime.of(date, newTime);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(prevDateTime);
        Attendance prevAttendance = crewAttendance.getAttendanceOn(date);

        crewAttendance.modify(newDateTime);
        Attendance newAttendance = crewAttendance.getAttendanceOn(date);

        assertThat(prevAttendance).isEqualTo(Attendance.of(prevDateTime));
        assertThat(newAttendance).isEqualTo(Attendance.of(newDateTime));
    }

    @DisplayName("전날까지의 출석 상태 기록 조회 테스트")
    @Test
    void test5() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 16, 13, 0);

        List<LocalDateTime> attendances = List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0), //출석
                LocalDateTime.of(2024, 12, 3, 10, 7), //지각
                LocalDateTime.of(2024, 12, 4, 10, 2), //출석
                LocalDateTime.of(2024, 12, 5, 10, 6), //지각
                LocalDateTime.of(2024, 12, 6, 10, 1), //출석
                LocalDateTime.of(2024, 12, 10, 10, 3), //출석
                LocalDateTime.of(2024, 12, 13, 10, 2), //출석
                today
        ); //결석 3회

        CrewAttendance crewAttendance = new CrewAttendance();
        for (LocalDateTime attendance : attendances) {
            crewAttendance.add(attendance);
        }

        Map<LocalDate, AttendanceStatus> attendanceStatuses = crewAttendance.getAttendanceStatusesBefore(today);

        assertThat(attendanceStatuses).hasSize(10);
        assertThat(attendanceStatuses.get(LocalDate.of(2024, 12, 9))).isEqualTo(AttendanceStatus.ABSENT);
        assertThat(attendanceStatuses.get(LocalDate.of(2024, 12, 11))).isEqualTo(AttendanceStatus.ABSENT);
        assertThat(attendanceStatuses.get(LocalDate.of(2024, 12, 12))).isEqualTo(AttendanceStatus.ABSENT);
    }

    @DisplayName("전날까지의 출석, 지각, 결석 횟수 계산 테스트")
    @Test
    void test6() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 16, 13, 0);

        List<LocalDateTime> attendances = List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0), //출석
                LocalDateTime.of(2024, 12, 3, 10, 7), //지각
                LocalDateTime.of(2024, 12, 4, 10, 2), //출석
                LocalDateTime.of(2024, 12, 5, 10, 6), //지각
                LocalDateTime.of(2024, 12, 6, 10, 1), //출석
                LocalDateTime.of(2024, 12, 10, 10, 3), //출석
                LocalDateTime.of(2024, 12, 13, 10, 2), //출석
                today
        ); //결석 3회

        CrewAttendance crewAttendance = new CrewAttendance();
        for (LocalDateTime attendance : attendances) {
            crewAttendance.add(attendance);
        }

        Map<AttendanceStatus, Integer> attendanceStatusCounts = crewAttendance.countAttendanceStatusesBefore(today);

        assertThat(attendanceStatusCounts.get(AttendanceStatus.PRESENT)).isEqualTo(5);
        assertThat(attendanceStatusCounts.get(AttendanceStatus.LATE)).isEqualTo(2);
        assertThat(attendanceStatusCounts.get(AttendanceStatus.ABSENT)).isEqualTo(3);
    }
}
