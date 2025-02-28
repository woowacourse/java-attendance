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
}
