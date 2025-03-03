import java.util.List;

public class AttendanceHistories {

    private final List<AttendanceHistory> attendanceHistories;


    public AttendanceHistories(List<AttendanceHistory> attendanceHistories) {
        this.attendanceHistories = attendanceHistories;
    }


    public List<AttendanceHistory> getAttendanceHistories() {
        return attendanceHistories;
    }

    public AttendanceHistory findByName(String name) {
        return attendanceHistories.stream()
                .filter(attendanceHistory -> attendanceHistory.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 이름을 가진 출석 이력이 없습니다."));
    }
}
