import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceTypeCounter {

    // TODO: 추후 MAP 래핑하기
    public static Map<AttendanceType, Integer> count(List<AttendanceHistory> attendanceHistories) {
        return attendanceHistories.stream()
                .collect(Collectors.toMap(
                        attendanceHistory -> AttendanceType.findAttendanceTypeByDateTime(
                                attendanceHistory.getAttendAt()),
                        attendanceHistory -> 1,
                        Integer::sum
                ));
    }

    public void temp() {

    }
}
