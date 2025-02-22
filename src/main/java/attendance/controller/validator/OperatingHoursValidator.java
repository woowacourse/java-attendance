package attendance.controller.validator;

import static attendance.constant.ErrorMessage.NOT_OPERATING_HOURS;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class OperatingHoursValidator {
    public static final LocalTime START_TIME = LocalTime.of(8, 0);
    public static final LocalTime END_TIME = LocalTime.of(23, 0);

    private OperatingHoursValidator() {
    }

    public static void validate(LocalDateTime attendDateTime) {
        LocalTime attendTime = attendDateTime.toLocalTime();
        if (attendTime.isAfter(END_TIME) || attendTime.isBefore(START_TIME)) {
            throw new IllegalArgumentException(NOT_OPERATING_HOURS.getMessage());
        }
    }
}
