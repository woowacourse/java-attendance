import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceBook {

    private List<Attendance> crewsAttendanceRecords;

    public AttendanceBook(List<Attendance> crewsAttendanceRecords) {
        this.crewsAttendanceRecords = crewsAttendanceRecords;
    }

    public Attendance check(String nickname, LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(nickname, date, time);

        return attendance;
    }
}
