package util.dataProvider;

import java.time.LocalDate;

public class SystemDefaultZoneCurrentDateProvider implements DateProvider {
    
    @Override
    public LocalDate getDate() {
        return LocalDate.now();
    }
}
