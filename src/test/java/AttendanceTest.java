import domain.Attendance;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 닉네임과_등교_시간을_입력하면_출석() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,1,10,30);

        // when
        Attendance attendance = new Attendance(inputName,localDateTime);

        // then
        Assertions.assertThat(attendance).extracting("name").isEqualTo("Lemon");
        Assertions.assertThat(attendance).extracting("attendanceTime").isEqualTo(LocalDateTime.of(2024,12,1,10,30));
    }
}
