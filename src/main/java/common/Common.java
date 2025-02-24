package common;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class Common {
    public static final TextStyle textStyle = TextStyle.FULL;
    public static final Locale locale = Locale.of("ko", "KR");
    public static final LocalDate nowDate = LocalDate.of(2024, 12, 13);

    public static final DateTimeFormatter monthDateDayFormatter = DateTimeFormatter.ofPattern(
            "MM월 dd일 E요일")
            .withLocale(Locale.forLanguageTag("ko"));
    public static final DateTimeFormatter hourMinuteFormatter = DateTimeFormatter.ofPattern("HH:mm");

    public static final LocalTime noneAttendanceTime = LocalTime.of(0, 0);

}
