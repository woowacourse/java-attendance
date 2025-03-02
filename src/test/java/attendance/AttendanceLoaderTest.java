package attendance;

import static org.assertj.core.api.Assertions.assertThat;

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
}
