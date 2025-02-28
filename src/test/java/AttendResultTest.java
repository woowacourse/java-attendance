import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendResultTest {

    @Test
    @DisplayName("이미 존재하는 attend를 다시 추가할 때 예외 처리")
    void throwExceptionWhenExistedAttend() {
        //given
        List<Attend> attend = List.of(
                new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                new Attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0))
        );
        AttendResult attendResult = new AttendResult(attend);
        Attend targetAttend = new Attend(LocalDate.of(2024, 12, 2));

        //when & then
        Assertions.assertThatThrownBy(() -> attendResult.addAttend(targetAttend))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
