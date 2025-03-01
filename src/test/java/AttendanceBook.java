import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceBook {

    private final List<Attendance> crewsAttendanceRecords;

    public AttendanceBook(List<Attendance> crewsAttendanceRecords) {
        this.crewsAttendanceRecords = new ArrayList<>(crewsAttendanceRecords);
    }

    public Attendance check(String nickname, LocalDate date, LocalTime time) {
        Attendance attendance = new Attendance(nickname, date, time);
        if (isAlreadyAttend(attendance)) {
            throw new IllegalArgumentException();
        }

        crewsAttendanceRecords.add(attendance);
        return attendance;
    }

    private boolean isAlreadyAttend(Attendance attendance) {
        return crewsAttendanceRecords.stream()
                .anyMatch(crew -> crew.equals(attendance));
    }

    public Attendance findAttendance(String nickname, LocalDate date, LocalTime time) {
        return crewsAttendanceRecords.stream()
                .filter(attendance -> attendance.equals(new Attendance(nickname, date, time)))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private boolean isSameDateAttendance(String nickname, LocalDate updateDate) {
        return crewsAttendanceRecords.stream()
                .anyMatch(crew -> crew.isSameDate(nickname, updateDate));
    }

    public Attendance update(String updateNickname, LocalDate updateDate, LocalTime updateTime) {
        if (!isSameDateAttendance(updateNickname, updateDate)) {
            throw new IllegalArgumentException();
        }

        Attendance attendance = new Attendance(updateNickname, updateDate, updateTime);
        crewsAttendanceRecords.add(attendance);

        return attendance;
    }
}
