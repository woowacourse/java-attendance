package util;

import java.time.LocalDateTime;
import model.AttendanceDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LocalDateTimePrintFormatterTest {

    @Test
    @DisplayName("출력 양식을 만드는 메서드에 대한 테스트")
    void test1() {
        org.assertj.core.api.Assertions.assertThat(
                LocalDateTimePrintFormatter.createAttendanceResultMessage(new AttendanceDateTime(LocalDateTime.of(2024,12,2,13,0))
                        )
        ).isEqualTo("12월 02일 월요일 13:00");
    }

    @Test
    @DisplayName("출력 양식을 만드는 메서드에 대한 테스트")
    void test2() {
        org.assertj.core.api.Assertions.assertThat(
                LocalDateTimePrintFormatter.createAttendanceResultMessage(
                        new AttendanceDateTime(
                                LocalDateTime.of(2024,12,2,0,0)
                        )
                        )
        ).isEqualTo("12월 02일 월요일 --:--");
    }

}