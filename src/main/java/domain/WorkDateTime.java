package domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class WorkDateTime implements Comparable<WorkDateTime> {
    private final WorkDate workDate;
    private final WorkTime workTime;

    public WorkDateTime(WorkDate workDate, WorkTime workTime) {
        this.workDate = workDate;
        this.workTime = workTime;
    }

    public static WorkDateTime from(LocalDateTime localDateTime) {
        return new WorkDateTime(
                new WorkDate(localDateTime.toLocalDate().getYear(), localDateTime.toLocalDate().getMonthValue(),
                        localDateTime.toLocalDate().getDayOfMonth()),
                new WorkTime(localDateTime.toLocalTime().getHour(), localDateTime.toLocalTime().getMinute()));
    }

    public WorkDate getDate() {
        return workDate;
    }

    public WorkTime getTime() {
        return workTime;
    }

    @Override
    public int compareTo(WorkDateTime other) {
        int dateDiff = this.workDate.compareTo(other.workDate);
        if (dateDiff != 0) {
            return dateDiff;
        }

        return this.workTime.compareTo(other.workTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkDateTime workDateTime = (WorkDateTime) o;
        return Objects.equals(workDate, workDateTime.workDate) && Objects.equals(workTime, workDateTime.workTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDate, workTime);
    }
}
