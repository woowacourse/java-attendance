package attendance.utility;

import java.time.LocalDate;

public class CurrentDateGeneratorImpl implements DateGenerator {

    @Override
    public LocalDate generateNow() {
        return LocalDate.now();
    }
}
