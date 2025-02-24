package domain;

import java.util.LinkedList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AttendanceCounterTest {

    private static AttendanceCounter attendanceCounter;

    @BeforeAll
    static void beforeAll() {
        // given (출석 : 2, 지각 : 1, 결석: 3)
        List<Attendance> attendanceList = List.of(
                fromString("2024-12-13 10:05"),
                fromString("2024-12-16 13:05"),
                fromString("2024-12-17 10:08"),
                fromString("2024-12-18 10:31"),
                fromString("2024-12-19 10:31"),
                fromString("2024-12-20 10:31")
        );
        Attendances attendances = new Attendances(new LinkedList<>(attendanceList));
        attendanceCounter = AttendanceCounter.of(attendances);
    }

    static Attendance fromString(final String input) {
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(input);
        return new Attendance(attendanceDateTime);
    }

    @Test
    void getAttendanceCount() {
        // given
        int expectedAttendance = 2;

        // when
        int attendanceCount = attendanceCounter.getAttendanceCount();

        // then
        Assertions.assertThat(attendanceCount).isEqualTo(expectedAttendance);
    }

    @Test
    void getTardiness() {
        // given
        int expectedTardiness = 1;

        // when
        int tardinessCount = attendanceCounter.getTardinessCount();

        // then
        Assertions.assertThat(tardinessCount).isEqualTo(expectedTardiness);
    }

    @Test
    void getAbsence() {
        // given
        int expectedAbsence = 3;

        // when
        int attendanceCount = attendanceCounter.getAbsenceCount();

        // then
        Assertions.assertThat(attendanceCount).isEqualTo(expectedAbsence);
    }
}