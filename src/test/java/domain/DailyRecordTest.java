package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.parser.DateTimeParser;

@Nested
public class DailyRecordTest {

    @Nested
    @DisplayName("시간 문자열 테스트")
    class TimeFormatTest {

        @Test
        @DisplayName("시간을 형식에 맞에 문자열로 반환할 수 있다.")
        void formatTime() {
            DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
            LocalTime time = DateTimeParser.parseStringToTime("10:00");

            DailyRecord record = new DailyRecord(dayOfWeek, time);
            assertThat(record.getFormattedTime()).isEqualTo("10:00");
        }

        @Test
        @DisplayName("빈 시간을 형식에 맞에 문자열로 반환할 수 있다.")
        void formatNullTime() {
            DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
            LocalTime time = null;

            DailyRecord record = new DailyRecord(dayOfWeek, time);
            assertThat(record.getFormattedTime()).isEqualTo("--:--");
        }
    }
}