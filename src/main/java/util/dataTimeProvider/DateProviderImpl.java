package util.dataTimeProvider;

import java.time.LocalDate;

public class DateProviderImpl implements DateProvider {
    
    @Override
    public LocalDate now() {
        return LocalDate.now();
    }
}
