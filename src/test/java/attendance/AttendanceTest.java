package attendance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 테스트")
public class AttendanceTest {

    @Test
    void 주말에_출석을_하면_예외가_발생한다() {
        LocalDate saturday = LocalDate.of(2024, 12, 14);
        LocalDate sunday = LocalDate.of(2024, 12, 15);

        assertAll(
                () -> assertThatThrownBy(() -> new Attendance(saturday))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] %d월 %d일 %s은(는) 등교일이 아닙니다"
                                .formatted(saturday.getMonthValue(), saturday.getDayOfMonth(),
                                        saturday.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN))),

                () -> assertThatThrownBy(() -> new Attendance(sunday))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("[ERROR] %d월 %d일 %s은(는) 등교일이 아닙니다"
                                .formatted(sunday.getMonthValue(), sunday.getDayOfMonth(),
                                        sunday.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)))
        );
    }
}
