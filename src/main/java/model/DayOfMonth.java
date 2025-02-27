package model;

public class DayOfMonth {

    private static final int DECEMBER_LENGTH = 31;

    private final int value;

    private DayOfMonth(final int value) {
        validateRange(value);
        this.value = value;
    }

    public static DayOfMonth of(final String valueInput) {
        int value = parseInt(valueInput);
        return new DayOfMonth(value);
    }

    private static int parseInt(final String valueInput) {
        try {
            return Integer.parseInt(valueInput);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("입력값은 숫자 여아합니다.");
        }
    }

    private void validateRange(final int value) {
        if (value < 1 || value > DECEMBER_LENGTH) {
            throw new IllegalArgumentException("1부터 31일 사이의 숫자만 들어올 수 있습니다.");
        }
    }

    public int getValue() {
        return value;
    }
}
