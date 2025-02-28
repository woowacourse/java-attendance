import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("닉네임, 출석 시간을 입력하면 출석할 수 있다.")
    @Test
    void attend() {
        // given
        String nickname = "율무";
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(10, 0);

        // when
        Attendance attendance = new Attendance(nickname, date, time);

        // then
        Assertions.assertThat(attendance)
                .isEqualTo(new Attendance(nickname, date, time));
    }
}
