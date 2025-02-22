package testUtil;

import util.dataProvider.DateProvider;

import java.time.LocalDate;

public class DateProviderStub implements DateProvider {
    
    private final LocalDate localDate;
    
    public DateProviderStub(LocalDate localDate) {
        this.localDate = localDate;
    }
    
    @Override
    public LocalDate getDate() {
        return localDate;
    }
}
