package attendance.model.attendance;

import java.util.List;

public class AttendanceLogs {

    private final List<AttendanceLog> values;

    public AttendanceLogs(final List<AttendanceLog> values) {
        this.values = values;
    }

    // 동작
    //LocalDate from 부터 LocalDate to 내에 등교일인데 출석하지 않은 날에 대한 AttendanceLog 들과 함께 AttendanceLog 리스트를 반환한다.
    //LocalDate from 부터 LocalDate to 내에 크루 출석 데이터를 종합하여 Map<AttendanceStatus, Integer> 를 반환한다.
    //Map<AttendanceStatus, Integer> 에는 각 AttendanceStatus 가 몇 개 있는지 저장한다.
}
