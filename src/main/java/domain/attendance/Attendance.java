package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

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
        validateAttendanceTime(attendanceDateTime);
        if(isAttendanceDay(attendanceDate) &&
                isOnCampusOperatingTime(LocalTime.of(attendanceDateTime.getHour(),attendanceDateTime.getMinute()))){
            attendanceDates.put(attendanceDate,new AttendanceDate(attendanceDateTime));
        }
    }

    private void validateAttendanceTime(LocalDateTime attendanceDateTime){
        LocalDate attendanceDate = LocalDate.from(attendanceDateTime);
        if(has(attendanceDate)){
            throw new IllegalArgumentException("[ERROR] 출석 기록이 이미 존재합니다.");
        }
        if(attendanceDateTime.isAfter(LocalDateTime.now())){
            throw new IllegalArgumentException("[ERROR] 출석 시간이 옳바르지 않습니다.");
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

    public int getAttendanceCount(){
        return Math.toIntExact(attendanceDates.entrySet().stream()
                .filter(localDateAttendanceDateEntry -> localDateAttendanceDateEntry.getValue().isAttendance())
                .count());
    }

    public int getTardyCount(){
        return Math.toIntExact(attendanceDates.entrySet().stream()
                .filter(localDateAttendanceDateEntry -> localDateAttendanceDateEntry.getValue().isTardy())
                .count());
    }

    public int getAbsenceCount(){
        return getExistAbsenceCount() + getMissingAttendanceCount();
    }

    private int getExistAbsenceCount(){
        return Math.toIntExact(attendanceDates.entrySet().stream()
                .filter(localDateAttendanceDateEntry -> localDateAttendanceDateEntry.getValue().isAbsence())
                .count());
    }

    private int getMissingAttendanceCount(){
        return Math.toIntExact(IntStream.range(1,LocalDate.now().getDayOfMonth())
                .mapToObj(day -> LocalDate.of(2025,2,day))
                .filter(date -> !has(date) && isAttendanceDay(date))
                .count());
    }

    public int getAbsenceIncludingTardyCount() {
        return getAbsenceCount() + getTardyCount() / 3;
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
