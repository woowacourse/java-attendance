package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRepositoryTest {

    @DisplayName("크루 출석 정보 저장 성공")
    @Test
    void test1() {
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance("빙티"));
        attendances.add(new Attendance("이든"));
        attendances.add(new Attendance("쿠키"));
        attendances.add(new Attendance("빙봉"));
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceRepository.add(name, localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("크루 출석 정보 저장 실패")
    @Test
    void test2() {
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance("빙티"));
        attendances.add(new Attendance("이든"));
        attendances.add(new Attendance("쿠키"));
        attendances.add(new Attendance("빙봉"));

        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        Attendance attendance = new Attendance(name);
        attendance.add(localDateTime);

        attendanceRepository.add(name, localDateTime);

        String otherName = "루키";

        assertThatThrownBy(() -> attendanceRepository.add(otherName, localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 닉네임입니다.");
    }

    @DisplayName("크루 출석 정보 수정 성공")
    @Test
    void test3() {
        List<Attendance> attendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceRepository.update(name, newLocalDateTime))
                .doesNotThrowAnyException();
    }

    @DisplayName("유효하지 않은 닉네임 크루 출석 정보 수정 실패")
    @Test
    void test4() {
        List<Attendance> attendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        String name = "빙티";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceRepository.update(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");

    }

    @DisplayName("기록이 없는 날짜 크루 출석 정보 수정 실패")
    @Test
    void test6() {
        List<Attendance> attendances = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Attendance attendance = new Attendance("빙봉");
        attendance.add(localDateTime);

        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        String name = "빙봉";
        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceRepository.update(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");

    }
}
