import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AbsentPolicy {

    public void validateIsWeekend(LocalDate attendanceDate) {
        DayOfWeek attendanceDayOfWeek = attendanceDate.getDayOfWeek();

        if(attendanceDayOfWeek == DayOfWeek.SATURDAY || attendanceDayOfWeek == DayOfWeek.SUNDAY){
            throw new IllegalArgumentException("[ERROR} 주말에는 출석할 수 없습니다");
        }
    }

    public void validateIsHoliday(LocalDate attendanceDate) {
    }

    public String checkAttendanceStatus(LocalDateTime educationDateTime) {
        LocalTime educationTime = educationDateTime.toLocalTime();

        if(educationTime.isAfter(LocalTime.of(10,0).plusMinutes(5))){
            return "지각";
        }
        return "출석";
    }

}
