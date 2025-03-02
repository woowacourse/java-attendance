import java.time.LocalDate;

@FunctionalInterface
public interface DateProvider {

    LocalDate getDate();
}
