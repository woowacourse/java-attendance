package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CustomClock {

    private final LocalDateTime dateTime;

    public CustomClock(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public LocalDate nowDate() {
        return dateTime.toLocalDate();
    }

    public LocalDate createDateFromDay(int day) {
        return LocalDate.of(dateTime.getYear(), dateTime.getMonthValue(), day);
    }

}
