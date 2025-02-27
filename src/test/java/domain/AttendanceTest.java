package domain;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    void 닉네임과_등교_시간을_입력시_출석된다() {
        // given
        String name = "fora";
        LocalTime localTime = LocalTime.of(9, 55);
        String state = "출석";

        // when
        Attendance attendance = new Attendance(name, localTime);

        // then
        Assertions.assertThat(attendance.getName().equals(name));
        Assertions.assertThat(attendance.getLocalTime().equals(localTime));
    }
}
