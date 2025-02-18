package domain;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("학생 한 명의 12월 1일부터 오늘까지의 출석부를 생성한다")
    @Test
    void test1() {
        // given
        List<LocalDateTime> localDateTimes = List.of(LocalDateTime.of(2024, 12, 2, 10, 0));

        // when
        Attendance attendance = new Attendance(localDateTimes);

        // then
        Assertions.assertThat(attendance).isInstanceOf(Attendance.class);

    }
}
