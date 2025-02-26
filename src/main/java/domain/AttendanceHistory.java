package domain;

import java.time.LocalDateTime;

public class AttendanceHistory {
    private final Crew crew;
    private final AttendanceDateTime dateTime;

    private AttendanceHistory(Crew crew, AttendanceDateTime dateTime) {
        this.crew = crew;
        this.dateTime = dateTime;
    }

    public static AttendanceHistory of(Crew crew, AttendanceDateTime dateTime) {
        return new AttendanceHistory(crew, dateTime);
    }

    public static AttendanceHistory of(Crew crew, LocalDateTime dateTime) {
        return new AttendanceHistory(crew, AttendanceDateTime.from(dateTime));
    }

    public boolean hasSameDate(LocalDateTime comparedDateTime) {
        if (dateTime.getDateTime().getYear() != comparedDateTime.getYear()) {
           return false;
        }
       if (dateTime.getDateTime().getMonth() != comparedDateTime.getMonth())  {
           return false;
       }
       if (dateTime.getDateTime().getDayOfMonth() != comparedDateTime.getDayOfMonth()) {
           return false;
       }

       return true;
    }

    public boolean hasSameCrew(Crew comparedCrew) {
       return crew.equals(comparedCrew);
    }

    public AttendanceType getAttendanceType() {
        return dateTime.getAttendanceType();
    }
}
