package attendance.util;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.List;

public class Parser {

    private static final String DELIMITER = ":";
    public static int convertToNumber(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException numberFormatException) {
            throw CustomException.from(ErrorMessage.NUMBER_FORMAT_ERROR);
        }
    }

    public static List<String> convertToGroup(String time) {
        return List.of(time.split(DELIMITER));
    }

}
