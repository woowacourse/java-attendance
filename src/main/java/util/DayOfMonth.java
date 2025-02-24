package util;

import error.CustomIllegalArgumentException;

public class DayOfMonth {

    private final int displayDay;

    public DayOfMonth(int displayDay) {
        validateRange(displayDay);
        this.displayDay = displayDay;
    }

    private void validateRange(int displayDay) {
        if (displayDay < 1 || displayDay > Constants.LENGTH_OF_MONTH) {
            throw new CustomIllegalArgumentException(
                    String.format("일은 1부터 %d사이의 숫자만 가능합니다.", Constants.LENGTH_OF_MONTH));
        }
    }

    public int getDisplayDay() {
        return displayDay;
    }
}
