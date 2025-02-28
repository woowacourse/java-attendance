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
        if (isAlreadyAttend(nickname, attendance)) {
            throw new IllegalArgumentException();
        }

        return attendance;
    }

    private boolean isAlreadyAttend(String nickname, Attendance attendance) {
        return crewsAttendanceRecords.stream()
                .anyMatch(crew -> crew.equals(attendance));
    }

    public Attendance findAttendance(String nickname, LocalDate date, LocalTime time) {
        return crewsAttendanceRecords.stream()
                .filter(attendance -> attendance.equals(new Attendance(nickname, date, time)))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
