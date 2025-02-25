package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Attendances attendances;
    private final Statistic statistic;

    public Crew(String name) {
        this.name = name;
        this.attendances = new Attendances();
        this.statistic = new Statistic();
    }

    public void initCrewAttendances(List<List<String>> csvData) {
        attendances.initAttendances(name, csvData);
    }

    public boolean isName(String name) {
        return this.name.equals(name);
    }

    public void attendToday(LocalTime time) {
        attendances.attend(LocalDateTime.of(LocalDate.now(), time));
        statistic.updateStatistic(attendances);
    }

    public int getPresentCount() {
        return statistic.getPresentCount();
    }

    public int getLateCount() {
        return statistic.getLateCount();
    }

    public int getAbsentCount() {
        return statistic.getAbsentCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Crew crew = (Crew) o;
        return name.equals(crew.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}
