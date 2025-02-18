package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRepositoryTest {

    @DisplayName("크루 출석 정보 저장 성공")
    @Test
    void test1() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(attendanceRepository.add(name, localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("크루 출석 정보 저장 실패")
    @Test
    void test2() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
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
}
