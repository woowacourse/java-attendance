package view.Policy;

import java.time.LocalTime;

public class TimePolicy {

    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 0;

    private static final int MAX_MINUTE = 59;
    private static final int MIN_MINUTE = 0;

    private static final String TIME_SEPARATOR = ":";
    private static final int TIME_FORMAT_LENGTH = 2;

    private static final LocalTime OPERATING_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATING_END_TIME = LocalTime.of(23, 0);

    public int validateHour(String inputHour){
        int hour = validateInteger(inputHour);
        if(hour > MAX_HOUR || hour < MIN_HOUR){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }
        return hour;
    }

    public int validateMinute(String inputMinute) {
        int minute = validateInteger(inputMinute);
        if(minute > MAX_MINUTE || minute < MIN_MINUTE){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }
        return minute;
    }

    public String[] validateTimeFormat(String time) {
        String[] splitTime = time.split(TIME_SEPARATOR);

        if(splitTime.length != TIME_FORMAT_LENGTH){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다");
        }

        return splitTime;
    }

    public LocalTime validateOperatingTime(String time) {
        String[] splitTime = validateTimeFormat(time);

        int hour = validateHour(splitTime[0]);
        int minute = validateMinute(splitTime[1]);

        if(LocalTime.of(hour,minute).isBefore(OPERATING_START_TIME)
        || LocalTime.of(hour,minute).isAfter(OPERATING_END_TIME))
        {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다");
        }

        return LocalTime.of(hour, minute);
    }

    private int validateInteger(String input) {
        try{
            return Integer.parseInt(input);
        }catch (NumberFormatException e){
            throw new IllegalArgumentException("[ERROR] 정수로 입력해 주세요");
        }
    }
}
