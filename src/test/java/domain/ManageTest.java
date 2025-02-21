package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ManageTest {

    @Test
    @DisplayName("결석 2회 미만인 경우 어떤 관리 대상자도 아니다")
    void ofTest_NONE() {
        // given
        Crew crew = new Crew("pobi");
        // LATE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 3), LocalTime.of(13, 6));
        // ATTENDANCE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 4), LocalTime.of(10, 5));
        // ABSENT_LATE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 5), LocalTime.of(10, 31));
        AttendanceStatusStatistics attendanceStatusStatistics =
                crew.getAttendanceStatusStatistics(LocalDate.of(2025, 2, 6));

        // when
        Manage manage = Manage.of(attendanceStatusStatistics);

        // then
        assertThat(manage).isEqualTo(Manage.NONE);
    }

    @Test
    @DisplayName("결석 2회 이상인 경우 경고 대상자가 된다")
    void ofTest_WARNING() {
        // given
        Crew crew = new Crew("pobi");
        // LATE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 3), LocalTime.of(13, 6));
        // LATE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 4), LocalTime.of(10, 6));
        // LATE
        crew.addAttendanceTime(LocalDate.of(2025, 2, 5), LocalTime.of(10, 30));
        AttendanceStatusStatistics attendanceStatusStatistics =
                crew.getAttendanceStatusStatistics(LocalDate.of(2025, 2, 7));

        // when
        Manage manage = Manage.of(attendanceStatusStatistics);

        // then
        assertThat(manage).isEqualTo(Manage.WARNING);
    }

    @Test
    @DisplayName("결석 3회 이상인 경우 면담 대상자가 된다")
    void ofTest_INTERVIEW() {
        // given
        Crew crew = new Crew("pobi");
        // LATE
        crew.addAttendanceTime(LocalDate.of(2025, 02, 3), LocalTime.of(13, 10));
        AttendanceStatusStatistics attendanceStatusStatistics =
                crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 7));

        // when
        Manage manage = Manage.of(attendanceStatusStatistics);

        // then
        assertThat(manage).isEqualTo(Manage.INTERVIEW);
    }

    @Test
    @DisplayName("결석 6회 이상인 경우 제적 대상자가 된다 ")
    void ofTest_EXPELLED() {
        // given
        Crew crew = new Crew("pobi");
        AttendanceStatusStatistics attendanceStatusStatistics =
                crew.getAttendanceStatusStatistics(LocalDate.of(2025, 2, 11));

        // when
        Manage manage = Manage.of(attendanceStatusStatistics);

        // then
        assertThat(manage).isEqualTo(Manage.EXPELLED);
    }
}
