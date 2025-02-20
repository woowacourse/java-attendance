package attendance.controller.validator;

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
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }
}
