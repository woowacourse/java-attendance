public class TimePolicy {
    public static void validateHour(String hour){
        if(Integer.parseInt(hour)>=24){
            throw new IllegalArgumentException("[ERROR] 시간 입력 형식이 잘못되었습니다.");
        }
    }
}
