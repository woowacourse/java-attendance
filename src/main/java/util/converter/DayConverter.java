package util.converter;

import exception.ErrorException;

public class DayConverter {

    private DayConverter() {
    }

    public static int convertDayToNumber(String day) {
        try {
            int number = Integer.parseInt(day);
            validateDayRange(number);
            return number;
        } catch (NumberFormatException e) {
            throw new ErrorException("숫자여야 합니다. 입력 값 : " + day);
        }
    }

    private static void validateDayRange(int day) throws ErrorException {
        if (day < 1 || day > 31) {
            throw new ErrorException("1 이상, 31 이하의 숫자여야 합니다. 입력 값 : " + day);
        }
    }
}
