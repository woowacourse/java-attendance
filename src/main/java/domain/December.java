package domain;

import java.util.List;

public enum December {
    MONDAY(List.of(2, 9, 16, 23, 30)),
    TUESDAY(List.of(3, 10, 17, 24, 31)),
    WEDNESDAY(List.of(4, 11, 18, 25)),
    THURSDAY(List.of(5, 12, 19, 26)),
    FRIDAY(List.of(6, 13, 20, 27)),
    SATURDAY(List.of(7, 14, 21, 28)),
    SUNDAY(List.of(8, 15, 22, 29)),
    HOLIDAY(List.of(25));
    private List<Integer> dates;

    December(List<Integer> dates) {
        this.dates = dates;
    }

    public static December findDayOfWeek(int dayOfMonth) {
        for(December day:December.values()){
            if(day.dates.contains(dayOfMonth)){
                return day;
            }
        }
        throw new IllegalArgumentException();
    }
}
