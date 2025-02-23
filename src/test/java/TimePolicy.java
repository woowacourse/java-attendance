public class TimePolicy {

    private static final int MAX_HOUR = 23;
    private static final String TIME_FORMAT_ERROR = "[ERROR] 시간 입력 형식이 잘못되었습니다.";

    public static void validateHour(String hour){
        if(Integer.parseInt(hour)> MAX_HOUR || Integer.parseInt(hour)<0){
            throw new IllegalArgumentException(TIME_FORMAT_ERROR);
        }
    }
}
