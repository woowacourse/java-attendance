package infrastructure;

import domain.DateProvider;
import java.time.LocalDate;

public class DecemberDateProvider implements DateProvider {

    @Override
    public LocalDate getDate() {
        return LocalDate.of(2024, 12, 13);
    }

}
