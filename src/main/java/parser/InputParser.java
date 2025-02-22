package parser;

import java.time.LocalTime;

public class InputParser {

    public static final String TIME_DELIMITER = ":";

    public static LocalTime timeParser(String time) {
        int hour = Integer.parseInt(time.split(TIME_DELIMITER)[0]);
        int minute = Integer.parseInt(time.split(TIME_DELIMITER)[1]);
        return LocalTime.of(hour,minute);
    }

}
