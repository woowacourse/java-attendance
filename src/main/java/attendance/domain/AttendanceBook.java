package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceBook {
    private final String crewName;
    private final Map<LocalDate, Attendance> timestamps;

    public AttendanceBook(String crewName) {
        this.crewName = crewName;
        this.timestamps = new HashMap<>();
    }

    public LocalDateTime attend(LocalDate date, LocalTime time){
        if(isAttendedDate(date)){
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
        }
        AttendanceChecker.checkCampusOpen(date, time);

        Attendance attendance = new Attendance(time, date);
        timestamps.put(date, attendance);
        return LocalDateTime.of(date, time);
    }

    public boolean isNameMatched(String crewName) {
        return this.crewName.equals(crewName);
    }

    private boolean isAttendedDate(LocalDate attendDate){
        return timestamps.keySet()
                .stream()
                .anyMatch(date -> date.equals(attendDate));
    }

}
