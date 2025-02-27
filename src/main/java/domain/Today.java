package domain;

import static domain.December.DEFAULT_MONTH;
import static domain.December.DEFAULT_YEAR;

import java.time.LocalDateTime;

public class Today {
    public static final LocalDateTime TODAY = LocalDateTime.of(
            DEFAULT_YEAR, DEFAULT_MONTH, 16,
            0, 0);
}
