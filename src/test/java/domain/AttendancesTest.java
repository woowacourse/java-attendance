package domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    private static Attendances attendances;

    @BeforeEach
    void setUp() {
        // given (출석 : 2, 지각 : 1, 결석: 3)
        List<Attendance> attendanceList = List.of(
                fromString("2024-12-13 10:05"),
                fromString("2024-12-16 13:05"),
                fromString("2024-12-17 10:08"),
                fromString("2024-12-18 10:31"),
                fromString("2024-12-19 10:31"),
                fromString("2024-12-20 10:31")
        );
        attendances = new Attendances(new LinkedList<>(attendanceList));
    }

    static Attendance fromString(final String input) {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(input);
        return new Attendance(attendanceDateTime);
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
        Attendance findAttendance = new Attendance(AttendanceDateTime.of("2024-12-16 10:00"));
        LocalTime updateTime = LocalTime.of(10, 10);
        Attendance expectedAttendance = new Attendance(AttendanceDateTime.of("2024-12-16 10:10"));

        // when
        attendances.updateTime(findAttendance, updateTime);
        Attendance updatedAttendance = attendances.findAttendance(findAttendance.getDateOfMonth());

        // then
        LocalDateTime expected = expectedAttendance.getLocalDateTime();
        LocalDateTime dateTime = updatedAttendance.getLocalDateTime();
        Assertions.assertThat(dateTime).isEqualTo(expected);
    }
}
