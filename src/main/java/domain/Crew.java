package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> timeLogs;

    public Crew(String name) {
        this.name = name;
        this.timeLogs = new HashMap<>();
    }

    public void addNewTimeLog(LocalDate date, LocalTime time) {
        timeLogs.put(date, time);
    }

    public boolean isMyName(String value) {
        return Objects.equals(name, value);
    }

    public boolean isDateExisted(LocalDate date) {
        return timeLogs.containsKey(date);
    }

    public String getName() {
        return name;
    }

    public Map<LocalDate, LocalTime> getTimeLogs() {
        return timeLogs;
    }

    public void gratifyTimeLogs() {
        for (int recordingDay = 1; recordingDay <= 31; recordingDay++) {
            LocalDate recordingDate = LocalDate.of(2024, 12, recordingDay);

            if (!(DecemberCalendar.judgeWorkingDay(recordingDate).equals("근무일") ||
                    DecemberCalendar.judgeWorkingDay(recordingDate).equals("월요일"))) { // 근무일이 아닌 경우
                continue;
            }

            if (!timeLogs.containsKey(recordingDate)) { // 기록이 존재하지 않으면
                addNewTimeLog(recordingDate, null);
            }
        }
    }
}