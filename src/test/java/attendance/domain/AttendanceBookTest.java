package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @DisplayName("출석부에 신규 크루 추가")
    @Test
    void test1() {
        AttendanceBook attendanceBook = new AttendanceBook();

        attendanceBook.addCrew("빙봉");

        assertThat(attendanceBook.countCrews()).isEqualTo(1);
        assertThat(attendanceBook.isCrew("빙봉")).isTrue();
    }

    @DisplayName("출석부에 추가하려는 크루가 존재하면 해당 크루의 CrewAttendance 반환")
    @Test
    void test2() {
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.addCrew("빙봉");
        int expectedSize = attendanceBook.countCrews();

        CrewAttendance crewAttendance = attendanceBook.addCrew("빙봉");

        assertThat(attendanceBook.countCrews()).isEqualTo(expectedSize);
        assertThat(crewAttendance).isInstanceOf(CrewAttendance.class);
    }

    @DisplayName("크루의 출석, 지각, 결석 횟수 확인 테스트")
    @Test
    void test3() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 16, 13, 0);
        String nickname = "빙티";
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

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.addCrew(nickname);
        for (LocalDateTime attendance : attendances) {
            attendanceBook.addAttendance(nickname, attendance);
        }

        Map<AttendanceStatus, Integer> attendanceStatusCounts =
                attendanceBook.getAttendanceStatusCounts(nickname, today);

        assertThat(attendanceStatusCounts.get(AttendanceStatus.PRESENT)).isEqualTo(5);
        assertThat(attendanceStatusCounts.get(AttendanceStatus.LATE)).isEqualTo(2);
        assertThat(attendanceStatusCounts.get(AttendanceStatus.ABSENT)).isEqualTo(3);
    }

    @DisplayName("크루의 제적, 면담, 경고 대상자 확인 테스트")
    @Test
    void test4() {
        LocalDateTime today = LocalDateTime.of(2024, 12, 16, 13, 0);
        String nickname = "빙티";
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

        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.addCrew(nickname);
        for (LocalDateTime attendance : attendances) {
            attendanceBook.addAttendance(nickname, attendance);
        }

        WarningLevel actual = attendanceBook.getWarningLevelOf(nickname, today);

        assertThat(actual).isEqualTo(WarningLevel.ONE_ON_ONE);
    }
}
