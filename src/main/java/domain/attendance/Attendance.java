package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static domain.attendance.TimeTable.*;

public class Attendance {
    private final Map<LocalDate, AttendanceDate> attendanceDates;

    public Attendance() {
        this.attendanceDates = new HashMap<>();
    }

    public boolean has(LocalDate findDate){
        return attendanceDates.containsKey(findDate);
    }

    public void addAttendance(LocalDateTime  attendanceDateTime) {
        LocalDate attendanceDate = LocalDate.from(attendanceDateTime);
        if(has(attendanceDate)){
            throw new IllegalArgumentException("[ERROR] 해당 LocalDate 이미 존재합니다.");
        }
        if(isAttendanceDay(LocalDate.from(attendanceDateTime))){
            attendanceDates.put(attendanceDate,new AttendanceDate(attendanceDateTime));
        }
    }

    public AttendanceDate findByLocalDate(LocalDate findLocalDate) {
        if(!has(findLocalDate)){
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 LocalDate 입니다.");
        }
        return attendanceDates.get(findLocalDate);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
