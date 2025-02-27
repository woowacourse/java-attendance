package attendance.utils;

import java.util.List;

public class Parser {

    private static final String COLON = ":";
    public static List<String> divideByColon(String inputTime) {
        List<String> dividedInputTime = List.of(inputTime.split(COLON));
        if (dividedInputTime.size() != 2) {
            throw new IllegalArgumentException();
        }
        return dividedInputTime;
    }
}
