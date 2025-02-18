package domain;

import java.time.LocalDateTime;

public class CrewRecord {

    private final String name;
    private final LocalDateTime dateTime;

    public CrewRecord(String name, LocalDateTime dateTime) {
        this.name = name;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public boolean isAttend(String inputName, LocalDateTime inputDateAndTime) {
        return inputName.equals(name) && (inputDateAndTime.toLocalDate()
            .equals(dateTime.toLocalDate()));
    }
}
