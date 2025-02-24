package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.DateTimeParser;

public class AttendanceTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {
        @Test
        @DisplayName("출석 객체 생성 테스트")
        void test1() {
            //given
            final String time = "2024-12-13 10:08";
            final LocalDateTime expectedTime = LocalDateTime.parse(time,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            //when
            final Attendance attendance = new Attendance(DateTimeParser.parseToLocalDateTime(time));

            //then
            assertThat(attendance.getDateTime()).isEqualTo(expectedTime);

        }

        @Test
        @DisplayName("출석 상태 계산 테스트")
        void test2() {
            //given
            final String time = "2024-12-13 10:31";
            final Attendance attendance = new Attendance(DateTimeParser.parseToLocalDateTime(time));

            //when
            final AttendanceStatus actual = attendance.calculateStatus();

            //then
            assertThat(actual).isEqualTo(AttendanceStatus.ABSENCE);
        }

    }
}
