package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    private static Attendances attendances;
    @BeforeAll
    static void beforeAll() {
        // given (출석 : 2, 지각 : 1, 결석: 3)
        List<Attendance> attendanceList = List.of(
                Attendance.of("2024-12-13 10:05"),
                Attendance.of("2024-12-16 13:05"),
                Attendance.of("2024-12-17 10:08"),
                Attendance.of("2024-12-18 10:31"),
                Attendance.of("2024-12-19 10:31"),
                Attendance.of("2024-12-20 10:31")
        );
        attendances = new Attendances(new LinkedList<>(attendanceList));
    }

    @Test
    void 출석_상태_갯수_측정() {
        // when
        AttendanceDto attendanceDto = attendances.calculateAttendanceCount();
        int expectedAttendance = 2;
        int expectedTardiness = 1;
        int expectedAbsence = 3;

        AttendanceDto expectedDto = new AttendanceDto(expectedAttendance, expectedTardiness, expectedAbsence);

        // then
        Assertions.assertThat(attendanceDto).isEqualTo(expectedDto);
    }

    @Test
    void 출석_수정_로직_확인() {
        // given
        Attendance findAttendance = Attendance.of("2024-12-16 10:00");
        LocalTime updateTime = LocalTime.of(10, 10);
        Attendance expectedAttendance = Attendance.of("2024-12-16 10:10");

        // when
        attendances.updateTime(findAttendance, updateTime);
        Attendance updatedAttendance = attendances.findAttendance(findAttendance.getDateOfMonth());

        // then
        LocalDateTime expected = expectedAttendance.getLocalDateTime();
        LocalDateTime dateTime = updatedAttendance.getLocalDateTime();
        Assertions.assertThat(dateTime).isEqualTo(expected);
    }
}
