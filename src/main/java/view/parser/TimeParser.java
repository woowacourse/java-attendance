package view.parser;

import java.time.LocalTime;

public class TimeParser {

    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 0;

    private static final int MAX_MINUTE = 59;
    private static final int MIN_MINUTE = 0;

    private static final String TIME_SEPARATOR = ":";
    private static final int TIME_FORMAT_LENGTH = 2;

    public static LocalTime validateTimeFormat(String time) {
        String[] splitTime = time.split(TIME_SEPARATOR);

        if(splitTime.length != TIME_FORMAT_LENGTH){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }

        int hour = validateHour(splitTime[0]);
        int minute = validateMinute(splitTime[1]);

        return LocalTime.of(hour, minute);
    }

    public static int validateHour(String inputHour){
        int hour = validateInteger(inputHour);
        if(hour > MAX_HOUR || hour < MIN_HOUR){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }
        return hour;
    }

    public static int validateMinute(String inputMinute) {
        int minute = validateInteger(inputMinute);
        if(minute > MAX_MINUTE || minute < MIN_MINUTE){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }
        return minute;
    }

    private static int validateInteger(String input) {
        try{
            return Integer.parseInt(input);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 정수로 입력해 주세요");
        }
    }
}
