import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class AttendanceTimeFactoryTest {

    @Test
    void test1() {
        String nickname = "빙티";
        DateTimeFormatter dateTimeFormatter1 =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime attendanceTime = LocalDateTime.parse("2024-12-06 12:02", dateTimeFormatter1);

        System.out.println(attendanceTime.toString());
    }

}
