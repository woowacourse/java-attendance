package attendance.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.AttendanceBook;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceBookRepositoryTest {

    @Nested
    class InvalidCases {

        @Test
        void 출석부_목록은_출석부들을_가지고_있어야_한다() {
            // when & then
            assertThatThrownBy(() -> new AttendanceBookRepository(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부 목록은 출석부들을 가지고 있어야 합니다.");
        }

        @Test
        void 출석부_목록은_크루의_닉네임과_출석부를_가지고_있어야_한다() {
            // given
            Map<String, AttendanceBook> attendanceBooks = new HashMap<>();
            attendanceBooks.put(null, null);

            // when & then
            assertThatThrownBy(
                () -> new AttendanceBookRepository(attendanceBooks))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석부 목록은 크루의 닉네임과 출석부를 가지고 있어야 합니다.");
        }
    }
}
