package util;

import java.time.LocalDateTime;

public class SystemDateTimeStrategy implements DateTimeStrategy {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
