package domain;

import java.time.LocalTime;

public class TimeAndStatus {
    private LocalTime time;
    private String status;

    public TimeAndStatus(LocalTime time, String dayOfWeek){
        this.time = time;
        this.status = checkStatus(dayOfWeek);
    }

    private String checkStatus(String dayOfWeek){
        if(dayOfWeek.equals("월")){
            if(time.isAfter(LocalTime.of(13, 5))) {
                return "지각";
            }
            if(time.isAfter(LocalTime.of(13, 30))) {
                return "결석";
            }
            return "출석";
        }
        if(time.isAfter(LocalTime.of(10, 5))) {
            return "지각";
        }
        if(time.isAfter(LocalTime.of(10, 30))) {
            return "결석";
        }
        return "출석";
    }
}
