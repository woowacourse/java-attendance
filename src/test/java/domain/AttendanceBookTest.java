package domain;

import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @DisplayName("해당 닉네임의 크루가 존재하는지 확인한다")
    @Test
    void test2() {
        // given
        String nickname = "수양";
        AttendanceBook attendanceBook = new AttendanceBook(Map.of("수양", List.of(), "빙봉", List.of()));

        // when
        boolean isContain = attendanceBook.has(nickname);

        // then
        Assertions.assertThat(isContain).isTrue();
    }
}
