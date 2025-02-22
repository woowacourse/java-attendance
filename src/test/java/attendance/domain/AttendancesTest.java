package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendancesTest {

    private Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = new Attendances();
    }

    @Test
    void 크루와_출석정보로_출석체크를_할수있다() {
        Crew crew = new Crew("크루");
        Attendance attendance = Attendance.of(LocalDateTime.of(2025, 2, 21, 10, 6));
        attendances.addAttendance(crew, attendance);

        Attendance saved = attendances.getAttendance(crew, LocalDate.of(2025, 2, 21));
        assertThat(saved).isEqualTo(attendance);
    }

    @Test
    void 크루의_특정날짜에_대한_출석기록을_얻을수있다() {
        Crew crew = new Crew("크루");
        Attendance attendance = attendances.getAttendance(crew, LocalDate.of(2025, 2, 21));
        assertThat(attendance.getStatus()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 크루와_날짜로_해당크루의_출석기록들을_얻을수있다() {
        Crew crew = new Crew("크루");
        Attendance attendance1 = Attendance.of(LocalDateTime.of(2025, 2, 19, 10, 0));
        Attendance attendance2 = Attendance.of(LocalDateTime.of(2025, 2, 20, 10, 0));
        attendances.addAttendance(crew, attendance1);
        attendances.addAttendance(crew, attendance2);

        List<Attendance> savedAttendances = attendances.getAttendances(crew,
            LocalDate.of(2025, 2, 21));
        assertThat(savedAttendances).contains(attendance1, attendance2);
    }

    @Test
    void 특정날짜_전날까지_크루의_결석을_포함하여_조회한다() {
        Crew crew = new Crew("크루");
        LocalDate _20250207Friday = LocalDate.of(2025, 2, 7);

        // 2025.02.03월 ~ 2025.02.06목 : 4일
        List<Attendance> savedAttendances = attendances.getAttendances(crew, _20250207Friday);
        assertThat(savedAttendances).hasSize(4);
    }

    @ParameterizedTest(name = "[전] {0} -> [후] {1} ({2})")
    @CsvSource({
        "2025-02-17T13:31:00,13:00:00,CHECKIN",
        "2025-02-18T10:31:00,10:06:00,LATE",
        "2025-02-19T10:06:00,10:00:00,CHECKIN",
        "2025-02-20T10:06:00,10:31:00,ABSENCE",
        "2025-02-21T10:00:00,10:06:00,LATE",
    })
    void 출석시간을_수정할수있다(LocalDateTime beforeAttendedTime, LocalTime timeToModify, AttendanceStatus expected) {
        Crew crew = new Crew("크루");
        Attendance attendance = Attendance.of(beforeAttendedTime);
        attendances.addAttendance(crew, attendance);

        LocalDate dateToBeModified = beforeAttendedTime.toLocalDate();
        attendances.modifyAttendance(crew, dateToBeModified, timeToModify);
        Attendance afterAttendance = attendances.getAttendance(crew, dateToBeModified);

        assertThat(afterAttendance.getAttendedTime().toLocalTime()).isEqualTo(timeToModify);
        assertThat(afterAttendance.getStatus()).isEqualTo(expected);
    }

    @Test
    void 크루의_특정날짜까지_출석상태를_헤아릴수_있다() {
        Crew crew = new Crew("크루");
        LocalDate _20250207Friday = LocalDate.of(2025, 2, 7);

        Attendance monday_checkIn = Attendance.of(LocalDateTime.of(2025, 2, 3, 13, 0));
        Attendance tuesday_late = Attendance.of(LocalDateTime.of(2025, 2, 4, 10, 6));
        attendances.addAttendance(crew, monday_checkIn);
        attendances.addAttendance(crew, tuesday_late);

        Map<AttendanceStatus, Integer> countsOfStatus =
            attendances.countAttendanceStatus(crew, _20250207Friday);

        int checkInCount = countsOfStatus.get(AttendanceStatus.CHECKIN);
        int lateCount = countsOfStatus.get(AttendanceStatus.LATE);
        int absenceCount = countsOfStatus.get(AttendanceStatus.ABSENCE);
        assertAll(
            () -> assertThat(checkInCount).isEqualTo(1),
            () -> assertThat(lateCount).isEqualTo(1),
            () -> assertThat(absenceCount).isEqualTo(2)
        );
    }
}
