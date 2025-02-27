package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public void editAttendance(LocalDateTime editLocalDateTime){
        LocalDate editDate = LocalDate.from(editLocalDateTime);
        if(!has(editDate)){
            throw new IllegalArgumentException("[ERROR] 수정하려는 날짜가 존재하지 않습니다.");
        }
        findByLocalDate(editDate).editLocalDate(editLocalDateTime);
    }

    public void addAttendance(LocalDateTime  attendanceDateTime) {
        LocalDate attendanceDate = LocalDate.from(attendanceDateTime);
        if(has(attendanceDate)){
            throw new IllegalArgumentException("[ERROR] 출석 기록이 이미 존재합니다.");
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

    public boolean has(LocalDate findDate){
        return attendanceDates.containsKey(findDate);
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
