package infrastructure;

import domain.DateProvider;
import java.time.LocalDate;

public class SystemDateProvider implements DateProvider {

    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
