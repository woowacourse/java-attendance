public class TimePolicy {

    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 0;

    private static final int MAX_MINUTE = 59;
    private static final int MIN_MINUTE = 0;

    private static final String TIME_SEPARATOR = ":";
    private static final int TIME_FORMAT_LENGTH = 2;

    private static final String TIME_FORMAT_ERROR = "[ERROR] 시간 입력 형식이 잘못되었습니다.";

    public static void validateHour(String hour){
        if(Integer.parseInt(hour)> MAX_HOUR || Integer.parseInt(hour)< MIN_HOUR){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }

    public static void validateMinute(String minute) {
        if(Integer.parseInt(minute) > MAX_MINUTE || Integer.parseInt(minute) < MIN_MINUTE){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }

    public static void validateTimeFormat(String time) {
        String[] splitTime = time.split(TIME_SEPARATOR);

        if(splitTime.length != TIME_FORMAT_LENGTH){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }
}
