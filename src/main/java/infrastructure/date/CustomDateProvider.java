package infrastructure.date;

import domain.DateProvider;
import java.time.LocalDate;

public class CustomDateProvider implements DateProvider {

    @Override
    public LocalDate getDate() {
        return LocalDate.of(2024, 12, 13);
    }

}
