package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {
    @DisplayName("크루 출석 정보 저장 성공")
    @Test
    void test1() {
        Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceBook.add(name, localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("크루 출석 정보 저장 실패")
    @Test
    void test2() {
        Map<String, CrewAttendance> crewAttendances = CrewAttendanceTestFixture.createCrewAttendances();
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        attendanceBook.add(name, localDateTime);

        String otherName = "루키";

        assertThatThrownBy(() -> attendanceBook.add(otherName, localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");
    }

    @DisplayName("크루 출석 정보 수정 성공")
    @Test
    void test3() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceBook.modify(name, newLocalDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 닉네임 크루 출석 정보 수정 실패")
    @Test
    void test4() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        String name = "빙티";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceBook.modify(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");

    }

    @DisplayName("기록이 없는 날짜 출석 정보 수정 실패")
    @Test
    void test6() {
        Map<String, CrewAttendance> crewAttendances = new HashMap<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        CrewAttendance crewAttendance = new CrewAttendance("빙봉");
        crewAttendance.add(localDateTime);
        crewAttendances.put("빙봉", crewAttendance);

        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceBook.modify(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }
}
