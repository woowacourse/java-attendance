package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceLoaderTest {
    @DisplayName("크루의 출석 기록 1개 저장 성공")
    @Test
    void test1() {
        String data = "쿠키,2024-12-13 10:08";

        AttendanceBook attendanceBook = AttendanceLoader.load(data);

        assertThat(attendanceBook)
                .isNotNull()
                .isInstanceOf(AttendanceBook.class);
        assertThat(attendanceBook.countCrews()).isEqualTo(1);
        assertThat(attendanceBook.isCrew("쿠키")).isTrue();
    }

    @DisplayName("크루의 출석 기록 여러 개 저장 성공")
    @Test
    void test2() {
        List<String> data = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07");

        AttendanceBook attendanceBook = AttendanceLoader.load(data);

        assertThat(attendanceBook)
                .isNotNull()
                .isInstanceOf(AttendanceBook.class);
        assertThat(attendanceBook.countCrews()).isEqualTo(4);
        assertThat(attendanceBook.isCrew("쿠키")).isTrue();
        assertThat(attendanceBook.isCrew("빙봉")).isTrue();
        assertThat(attendanceBook.isCrew("빙티")).isTrue();
        assertThat(attendanceBook.isCrew("이든")).isTrue();
    }
}
