package util.dataTimeProvider;

import java.time.LocalDate;

public class DefaultDateProvider implements DateProvider {
    
    @Override
    public LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
