import java.time.LocalDate;

public enum Holiday {

    CHRISTMAS(12, 25);

    private final int month;
    private final int day;

    Holiday(int month, int day) {
        this.month = month;
        this.day = day;
    }

    public static boolean isHoliday(LocalDate day) {
        return true;
    }
}
