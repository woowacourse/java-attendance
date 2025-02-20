package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class ManageTest {

    @Test
    void 결석_2회_미만인_경우_아무_관리_대상자가_아니다() {
        Crew crew = new Crew("pobi");
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06));
        // 출석
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 05));
        // 결석
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 31));
        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 6));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.NONE);
    }

    @Test
    void 결석_2회_이상인_경우_경고_대상자가_된다() {
        Crew crew = new Crew("pobi");
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06));
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 06));
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 30));
        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 7));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.WARNING);
    }

    @Test
    void 결석_3회_이상인_경우_면담_대상자가_된다() {
        Crew crew = new Crew("pobi");
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 10));
        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 7));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.INTERVIEW);
    }

    @Test
    void 결석_6회_이상인_경우_제적_대상자가_된다() {
        Crew crew = new Crew("pobi");
        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusCounter(LocalDate.of(2025, 02, 11));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.EXPELLED);
    }
}
