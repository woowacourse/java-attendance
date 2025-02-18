import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;

public class Student {

    LinkedHashMap<LocalDateTime, AttendanceStatus> record = new LinkedHashMap<>();
    String name;
    int absent;
    int attendance;
    int late;

    public Student(String name) {
        this.name = name;
    }

    public void isStartTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(23,0)) || localTime.isBefore(LocalTime.of(8,0))){
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
        }
    }

    public void updateState(LocalDateTime localDateTime) {
        DayOfWeek dayOfWeek = localDateTime.getDayOfWeek();
        int day = dayOfWeek.getValue();
        if (day == 6 || day == 7) {
            return;
        }

        AttendanceStatus attendanceStatus = AttendanceCalculatorByDay.
                attendanceCalculator(day, LocalTime.from(localDateTime));

        record.putIfAbsent(localDateTime, attendanceStatus);

        assert attendanceStatus != null;
        if (attendanceStatus.equals(AttendanceStatus.ATTENDANCE)){
            attendance++;
            return;
        }
        if (attendanceStatus.equals(AttendanceStatus.LATE)){
            late++;
            return;
        }
        absent++;

    }
}
