package attendance.domain;

public class AttendanceDate {

    private final int year;
    private final int month;
    private final int day;

    public AttendanceDate(
        final int year,
        final int month,
        final int day
    ) {
        validateDate(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private void validateDate(
        final int year,
        final int month,
        final int day
    ) {
        if (year != 2024 || month != 12 || day > 31) {
            throw new IllegalArgumentException("출석 날짜는 2024년 12월만 지원합니다.");
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

}
