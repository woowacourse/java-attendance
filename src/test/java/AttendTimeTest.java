import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendTimeTest {

    @DisplayName("해당 날짜의 출석 데이터를 수정한다.")
    @Test
    void test1() {
        AttendTime attendTime = new AttendTime("2024-12-13 09:59");

        attendTime.modifyAttendTime("10:01");

        assertThat(attendTime.getAttendTime().getHour()).isEqualTo(10);
        assertThat(attendTime.getAttendTime().getMinute()).isEqualTo(1);
    }
}
