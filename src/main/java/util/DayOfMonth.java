package util;

import exception.DayOfMonthException;

public class DayOfMonth {

    private final int displayDay;

    public DayOfMonth(int displayDay) {
        validateRange(displayDay);
        this.displayDay = displayDay;
    }

    private void validateRange(int displayDay) {
        if (displayDay < 1 || displayDay > Constants.LENGTH_OF_MONTH) {
            throw new IllegalArgumentException(
                    String.format(DayOfMonthException.INVALID_DAY_RANGE.getMessage(Constants.LENGTH_OF_MONTH)));
        }
    }

    public int getDisplayDay() {
        return displayDay;
    }
}
