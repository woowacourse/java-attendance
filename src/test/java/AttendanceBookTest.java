import domain.AttendanceBook;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @DisplayName("중복된 크루가 있다면 예외를 뱉는다")
    @Test
    void test() {
        Assertions.assertThatThrownBy(() -> new AttendanceBook(List.of("수양", "수양")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("중복된 닉네임의 크루는 존재할 수 없습니다");
    }

    @DisplayName("해당 닉네임의 크루가 존재하는지 확인한다")
    @Test
    void test2() {
        // given
        AttendanceBook attendanceBook = new AttendanceBook(List.of("수양", "빙봉", "쿠키"));
        String findNickname = "수양";

        // when
        boolean isContain = attendanceBook.has(findNickname);

        // then
        Assertions.assertThat(isContain).isTrue();
    }
}
