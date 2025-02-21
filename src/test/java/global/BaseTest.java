package global;

import attendance.model.CustomLocalDateTime;
import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;

public class BaseTest {

    @BeforeEach
    void setFixedClock() throws NoSuchFieldException, IllegalAccessException {
        Clock fixedClock = Clock.fixed(
                LocalDateTime.of(2024, 12, 16, 12, 0).toInstant(ZoneOffset.UTC),
                ZoneId.of("UTC")
        );
        Field clockField = CustomLocalDateTime.class.getDeclaredField("clock");
        clockField.setAccessible(true);
        clockField.set(null, fixedClock);
    }
}
