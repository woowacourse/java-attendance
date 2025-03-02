package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @DisplayName("캠퍼스 휴일 날짜의 출석 기록 생성 시 예외 발생")
    @Test
    void test1() {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, 25, 8, 0);

        assertThatThrownBy(() -> new Attendance(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 12월 %d일 %s은 등교일이 아닙니다.",
                        attendance.getDayOfMonth(),
                        attendance.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
                );
    }

    @DisplayName("캠퍼스 운영일의 출석 기록 생성 성공")
    @Test
    void test2() {
        LocalDateTime attendance = LocalDateTime.of(2024, 12, 24, 8, 0);

        assertThatCode(() -> new Attendance(attendance)).doesNotThrowAnyException();
        assertThat(new Attendance(attendance))
                .isNotNull()
                .isInstanceOf(Attendance.class);
    }
}
