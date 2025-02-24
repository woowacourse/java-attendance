package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.Test;

class ManageTest {

    @Test
    void 결석_2회_미만인_경우_아무_관리_대상자가_아니다() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06)); // LATE
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 05)); // ATTENDANCE
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 31)); // ABSENT

        // then
        Map<AttendanceStatus, Integer> attendanceStatusCounter = crew.getAttendanceStatusCounter(
            LocalDate.of(2025, 02, 6));
        assertThat(Manage.of(attendanceStatusCounter)).isEqualTo(Manage.NONE);
    }

    @Test
    void 결석_2회_이상인_경우_경고_대상자가_된다() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06)); // LATE
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 06)); // LATE
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 30)); // LATE
        // 6일 -> ABSENT

        // when
        Map<AttendanceStatus, Integer> attendanceStatusCounter =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 7));

        // then
        assertThat(Manage.of(attendanceStatusCounter)).isEqualTo(Manage.WARNING);
    }

    @Test
    void 결석_3회_이상인_경우_면담_대상자가_된다() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 10)); // LATE
        // 4, 5, 6일 ABSENT

        // when
        Map<AttendanceStatus, Integer> attendanceStatusCounter =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 7));

        // then
        assertThat(Manage.of(attendanceStatusCounter)).isEqualTo(Manage.INTERVIEW);
    }

    @Test
    void 결석_6회_이상인_경우_제적_대상자가_된다() {
        // given
        Crew crew = new Crew("pobi");
        // 3, 4, 5, 6, 7, 10일 ABSENT

        // when
        Map<AttendanceStatus, Integer> attendanceStatusCounter =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 11));

        // then
        assertThat(Manage.of(attendanceStatusCounter)).isEqualTo(Manage.EXPELLED);
    }
}
