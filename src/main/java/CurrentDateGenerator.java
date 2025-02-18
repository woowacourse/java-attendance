import java.time.LocalDate;

public class CurrentDateGenerator implements DateGenerator {
    @Override
    public LocalDate generate() {
        return LocalDate.now();
    }
}
