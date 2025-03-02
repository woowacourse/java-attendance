package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
}
