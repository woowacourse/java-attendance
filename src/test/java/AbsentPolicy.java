import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AbsentPolicy {

    public void validateIsWeekend(LocalDate attendanceDate) {
        return;
    }


    public String checkAttendanceStatus(LocalDateTime educationDateTime) {
        LocalTime educationTime = educationDateTime.toLocalTime();

        if(educationTime.isAfter(LocalTime.of(10,0).plusMinutes(5))){
            return "지각";
        }
        return "출석";
    }

}
