package policy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AbsentPolicy {

    private static final LocalDate CHRISTMAS = LocalDate.of(2024, 12, 25);

    public void validateIsWeekend(DayOfWeek attendanceDayOfWeek) {
        if(attendanceDayOfWeek == DayOfWeek.SATURDAY || attendanceDayOfWeek == DayOfWeek.SUNDAY){
            throw new IllegalArgumentException("[ERROR] 주말에는 출석할 수 없습니다");
        }
    }

    public void validateIsHoliday(LocalDate attendanceDate) {
        if(attendanceDate.equals(CHRISTMAS)){
            throw new IllegalArgumentException("[ERROR] 공휴일에는 출석할 수 없습니다");
        }
    }

    public String checkAttendanceStatus(LocalDateTime educationDateTime) {
        LocalDate educationDate = educationDateTime.toLocalDate();
        LocalTime educationTime = educationDateTime.toLocalTime();
        DayOfWeek attendanceDayOfWeek = educationDate.getDayOfWeek();

        validateIsWeekend(attendanceDayOfWeek);
        validateIsHoliday(educationDate);

        LocalTime attendanceTime = calculateAttendanceTime(attendanceDayOfWeek);

        return calculateAttendanceStatus(educationTime, attendanceTime);
    }

    private static String calculateAttendanceStatus(LocalTime educationTime, LocalTime attendanceTime) {
        if(educationTime.isBefore(attendanceTime.plusMinutes(6))){
            return "출석";
        }

        if(educationTime.isBefore(attendanceTime.plusMinutes(31))){
            return "지각";
        }

        return "결석";
    }

    private static LocalTime calculateAttendanceTime(DayOfWeek attendanceDayOfWeek) {
        if(attendanceDayOfWeek == DayOfWeek.MONDAY){
            return LocalTime.of(13, 0);
        }
        return LocalTime.of(10, 0);
    }

}
