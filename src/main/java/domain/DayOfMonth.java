package domain;

import exception.DayOfMonthException;
import util.Constants;

public class DayOfMonth {

    private final int displayDay;

    public DayOfMonth(final int displayDay) {
        validateRange(displayDay);
        this.displayDay = displayDay;
    }

    private void validateRange(final int displayDay) {
        if (displayDay < 1 || displayDay > Constants.LENGTH_OF_MONTH) {
            throw new IllegalArgumentException(
                    String.format(DayOfMonthException.INVALID_DAY_RANGE.getMessage(Constants.LENGTH_OF_MONTH)));
        }
    }

    public int getDisplayDay() {
        return displayDay;
    }
}
