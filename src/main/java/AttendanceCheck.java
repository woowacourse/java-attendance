import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceCheck {

    public static String checkAttendanceStatus(LocalTime standardTime, LocalTime localTime) {
        long betweenMinutes = Duration.between(standardTime, localTime).toMinutes();
        if (betweenMinutes <= 5) {
            return "출석";
        }

        if (betweenMinutes > 5 && betweenMinutes <= 30) {
            return "지각";
        }

        return "결석";
    }

    public static String convertKorean(LocalDate localDate) {
        if (localDate.getDayOfWeek().getValue() == 1) {
            return "월요일";
        }
        if (localDate.getDayOfWeek().getValue() == 2) {
            return "화요일";
        }
        if (localDate.getDayOfWeek().getValue() == 3) {
            return "수요일";
        }
        if (localDate.getDayOfWeek().getValue() == 4) {
            return "목요일";
        }

        if (localDate.getDayOfWeek().getValue() == 5) {
            return "금요일";
        }

        if (localDate.getDayOfWeek().getValue() == 6) {
            return "토요일";
        }

        return "일요일";

    }

    public static LocalTime convertToLocalTime(String timeInput) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            return LocalTime.parse(timeInput, dateTimeFormatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 입력된 시간 형식이 적절하지 않습니다.");
        }
    }

    public static LocalTime getStandardTime(LocalDate date) {
        if (date.getDayOfWeek().getValue() == 1) {
            return LocalTime.of(13, 0);
        }
        if (date.getDayOfWeek().getValue() == 6 || date.getDayOfWeek().getValue() == 7) {
            throw new IllegalArgumentException("[ERROR] 오늘은 등교일이 아닙니다.");
        }
        return LocalTime.of(10, 0);
    }
}
