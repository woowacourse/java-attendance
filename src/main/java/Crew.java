import java.time.LocalDate;
import java.time.LocalTime;

public class Crew {
    private final String name;
    private LocalDate date;
    private LocalTime time;

    public Crew(String name, LocalDate date, LocalTime time) {
        this.name = name;
        this.date = date;
        this.time = time;
    }
}