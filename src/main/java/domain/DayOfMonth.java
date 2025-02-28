package domain;

public record DayOfMonth(int dayOfMonth) {

    public DayOfMonth {
        isInvalidDayOfMonth(dayOfMonth);
    }

    private void isInvalidDayOfMonth(int dayOfMonth) {
        if (dayOfMonth <= 0 || dayOfMonth > 31) {
            throw new IllegalArgumentException("잘못된 날짜입니다.");
        }
    }
}
