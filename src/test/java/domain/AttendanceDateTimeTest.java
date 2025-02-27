package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("출석 날짜와 시간 객체에 대한 테스트")
public class AttendanceDateTimeTest {

    @Test
    void 날짜와_시간을_통해서_출석_가능한_시간에_출석시간을_생성한다() {
        var date = LocalDate.of(2025, 2, 26);
        var time = LocalTime.of(10, 0);

        AttendanceDateTime attendance = new AttendanceDateTime(LocalDateTime.of(date, time));

        assertThat(attendance).isNotNull();
    }

    @Test
    void 출석시간은_생성_시_주어진_날짜와_시간을_가진다() {
        var date = LocalDate.of(2025, 2, 26);
        var time = LocalTime.of(10, 0);

        AttendanceDateTime attendance = new AttendanceDateTime(LocalDateTime.of(date, time));

        assertThat(attendance.getDate()).isEqualTo(date);
        assertThat(attendance.getTime()).isEqualTo(time);
    }

    @Test
    void 문자열을_파싱하여_출석시간을_생성할_수_있다() {
        String dateTimeString = "2025-02-26T10:00";

        AttendanceDateTime result = AttendanceDateTime.parse(dateTimeString);

        assertThat(result.getDate()).isEqualTo(LocalDate.of(2025, 2, 26));
        assertThat(result.getTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void _평일8시_전에_출석시간을_생성하려하면_예외가_발생한다() {
        var weekday_0759 = LocalDateTime.of(2025, 2, 26, 7, 59);
        assertThatThrownBy(() -> new AttendanceDateTime(weekday_0759))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _평일23시_후에_출석시간을_생성하려하면_예외가_발생한다() {
        var weekday_2301 = LocalDateTime.of(2025, 2, 26, 23, 1);
        assertThatThrownBy(() -> new AttendanceDateTime(weekday_2301))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 주말날짜로_출석시간을_생성하려하면_예외가_발생한다() {
        var weekend = LocalDateTime.of(2025, 2, 23, 10, 0);
        assertThatThrownBy(() -> new AttendanceDateTime(weekend))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일에_출석하려고_하면_예외가_발생한다() {
        var weekend = LocalDateTime.of(2025, 5, 5, 10, 0);
        assertThatThrownBy(() -> new AttendanceDateTime(weekend))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 다른_출석시간과_같은_날짜인지_확인할_수_있다() {
        var var1 = AttendanceDateTime.parse("2025-02-26T10:00");
        var var2 = AttendanceDateTime.parse("2025-02-26T11:00");

        boolean result = var1.isSameDay(var2);

        assertThat(result).isTrue();
    }

    @Test
    void 다른_출석시간과_같은_날짜가_아닌지_확인할_수_있다() {
        var var1 = AttendanceDateTime.parse("2025-02-25T10:00");
        var var2 = AttendanceDateTime.parse("2025-02-26T11:00");

        boolean result = var1.isSameDay(var2);

        assertThat(result).isFalse();
    }
}
