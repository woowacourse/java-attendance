package global;

import attendance.model.CustomLocalDateTime;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void setFixedClock() {
        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2024, 12, 16, 12, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );
        CustomLocalDateTime.setClock(fixedClock);
    }
}
