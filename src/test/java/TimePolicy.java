public class TimePolicy {

    private static final int MAX_HOUR = 23;
    private static final int MIN_HOUR = 0;

    private static final String TIME_FORMAT_ERROR = "[ERROR] 시간 입력 형식이 잘못되었습니다.";
    private static final int MAX_MINUTE = 59;

    public static void validateHour(String hour){
        if(Integer.parseInt(hour)> MAX_HOUR || Integer.parseInt(hour)< MIN_HOUR){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }

    public static void validateMinute(String minute) {
        if(Integer.parseInt(minute) > MAX_MINUTE || Integer.parseInt(minute) < 0){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }
}
