import domain.OperationTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

public enum Current {
    TODAY(13);

    private final int day;
    private final LocalDate date;

    Current(final int day) {
        this.day = day;
        this.date = LocalDate.of(2024, 12, this.day);
    }

    public static List<LocalDate> getEducationDateUntilCurrent() {
        return IntStream.range(1, TODAY.day)
                .mapToObj(day -> LocalDate.of(TODAY.getYear(), TODAY.getMonth(), day))
                .filter(OperationTime::isOperationDate)
                .toList();
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getMonth() {
        return this.date.getMonth().getValue();
    }

    public int getDay() {
        return this.day;
    }
}
