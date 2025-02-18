import domain.Attendance;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 닉네임과_등교_시간을_입력하면_출석() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,3,10,30);

        // when
        Attendance attendance = new Attendance(inputName,localDateTime);

        // then
        Assertions.assertThat(attendance).extracting("name").isEqualTo("Lemon");
        Assertions.assertThat(attendance).extracting("attendanceTime").isEqualTo(LocalDateTime.of(2024,12,3,10,30));
    }

    @Test
    void 주말에_출석하면_예외가_발생한다() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,8,10,30);

        // expected
        Assertions.assertThatThrownBy(() -> new Attendance(inputName,localDateTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 가능한 날짜가 아닙니다.");
    }

    @Test
    void _8시와_23시_사이가_아니면_예외가_발생한다() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,3,7,59);

        // expected
        Assertions.assertThatThrownBy(() -> new Attendance(inputName,localDateTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 가능한 시간이 아닙니다.");
    }
}
