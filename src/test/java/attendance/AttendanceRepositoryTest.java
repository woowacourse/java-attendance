package attendance;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRepositoryTest {

    @DisplayName("출석 정보 저장 성공 ")
    @Test
    void test1() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        AttendanceRepository attendanceRepository = new AttendanceRepository();

        assertThatCode(() -> attendanceRepository.add(localDateTime)).doesNotThrowAnyException();
    }

    @DisplayName("출석 정보 저장 실패")
    @Test
    void test2() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        AttendanceRepository attendanceRepository = new AttendanceRepository();

        attendanceRepository.add(localDateTime);

        assertThatThrownBy(() -> attendanceRepository.add(localDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

}
