package domain;

import java.util.TreeSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 출석_상태_갯수_측정() {
        // given (출석 : 2, 지각 : 1, 결석: 3)
        TreeSet<Attendance> attendanceList = new TreeSet<>();
        attendanceList.add(Attendance.from("2024-12-13 10:05"));
        attendanceList.add(Attendance.from("2024-12-16 13:05"));
        attendanceList.add(Attendance.from("2024-12-17 10:08"));
        attendanceList.add(Attendance.from("2024-12-18 10:31"));
        attendanceList.add(Attendance.from("2024-12-19 10:31"));
        attendanceList.add(Attendance.from("2024-12-20 10:31"));
        Attendances attendances = new Attendances(attendanceList);

        // when

        AttendanceStatusCounts attendanceStatusCounts = attendances.calculateAttendanceCount();
        int expectedAttendance = 2;
        int expectedTardiness = 1;
        int expectedAbsence = 3;

        AttendanceStatusCounts expectedDto = new AttendanceStatusCounts(expectedAttendance, expectedTardiness,
                expectedAbsence);

        // then
        Assertions.assertThat(attendanceStatusCounts).isEqualTo(expectedDto);
    }

}
