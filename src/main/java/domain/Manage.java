package domain;

import java.util.Arrays;
import java.util.Comparator;

public enum Manage {
    NONE(0, "아무 대상자가 아님"),
    WARNING(2, "경고"),
    INTERVIEW(3, "면담"),
    EXPELLED(6, "제적"),
    ;

    private final int absentLimit;
    private final String description;

    Manage(int absentLimit, String description) {
        this.absentLimit = absentLimit;
        this.description = description;
    }

    public static Manage of(AttendanceStatusStatistics attendanceStatusStatistics) {
        int count = attendanceStatusStatistics.calculateTotalAbsentCountForManage();

        return Arrays.stream(values())
                .filter(manage -> manage.absentLimit <= count)
                .max(Comparator.comparing(Manage::getAbsentLimit))
                .orElse(NONE);
    }

    public int getAbsentLimit() {
        return absentLimit;
    }

    public String getDescription() {
        return description;
    }
}
