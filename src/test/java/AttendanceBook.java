import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.AttendanceDateTimeChecker;

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

    public Attendance findAttendance(String nickname, LocalDate date) {
        return crewsAttendanceRecords.stream()
                .filter(attendance -> attendance.isSameDate(nickname, date))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Attendance update(String updateNickname, LocalDate updateDate, LocalTime updateTime) {
        AttendanceDateTimeChecker checker = new AttendanceDateTimeChecker();
        checker.determine(updateDate, updateTime);

        Attendance findAttendance = findAttendance(updateNickname, updateDate);
        findAttendance.updateTime(updateTime);

        return findAttendance;
    }
}
