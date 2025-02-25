package global;

import attendance.model.CustomLocalDateTime;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class Constant {

    public static CustomLocalDateTime customLocalDateTime = new CustomLocalDateTime(Clock.fixed(
            LocalDateTime.of(2024, 12, 16, 12, 0).toInstant(ZoneOffset.UTC),
            ZoneId.of("UTC")
    ));
}
