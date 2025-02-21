package domain;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 출석_상태_갯수_측정() {
        // given (출석 : 2, 지각 : 1, 결석: 3)
        List<Attendance> attendanceList = List.of(
                Attendance.of("2024-12-13 10:05"),
                Attendance.of("2024-12-16 13:05"),
                Attendance.of("2024-12-17 10:08"),
                Attendance.of("2024-12-18 10:31"),
                Attendance.of("2024-12-19 10:31"),
                Attendance.of("2024-12-20 10:31")
        );
        Attendances attendances = new Attendances(attendanceList);

        // when
        AttendanceDto attendanceDto = attendances.calculateAttendanceCount();
        int expectedAttendance = 2;
        int expectedTardiness = 1;
        int expectedAbsence = 3;

        AttendanceDto expectedDto = new AttendanceDto(expectedAttendance, expectedTardiness, expectedAbsence);

        // then
        Assertions.assertThat(attendanceDto).isEqualTo(expectedDto);
    }

}
