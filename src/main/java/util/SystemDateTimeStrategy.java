package util;

import java.time.LocalDate;

public class SystemDateTimeStrategy implements DateTimeStrategy {

    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
