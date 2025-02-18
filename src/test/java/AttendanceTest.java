import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 닉네임과_등교_시간으로_출석한다() {
        Crews crews = new Crews();
        crews.add(new Crew("pobi"));
        Crew crew = crews.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 1));
    }

    @Test
    void 이미_출석한_경우_예외를_던진다() {
        Crew crew = new Crew("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.now(), LocalTime.of(10, 01));
        }).isInstanceOf(AlreadyAttendanceException.class);
    }

    @Test
    void 닉네임과_수정날짜와_등교시간으로_기록을_수정한다() {
        Crews crews = new Crews();
        crews.add(new Crew("pobi"));
        Crew crew = crews.get("pobi");
        crew.attendance(LocalDate.now(), LocalTime.of(10, 00));
        crew.modifyAttendance(LocalDate.now(), LocalTime.of(10, 10));
        assertThat(crew.getAttendanceTimeByDate(LocalDate.now())).isEqualTo(LocalTime.of(10, 10));
    }

    @Test
    void 날짜와_시간으로_출석_상태를_계산한다() {
        Crews crews = new Crews();
        crews.add(new Crew("pobi"));
        Crew crew = crews.get("pobi");
        crew.attendance(LocalDate.of(2025, 02, 17), LocalTime.of(13, 06));
        crew.attendance(LocalDate.of(2025, 02, 18), LocalTime.of(10, 05));
        crew.attendance(LocalDate.of(2025, 02, 19), LocalTime.of(10, 31));

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 01))).isEqualTo(AttendanceStatus.ABSENT);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 17))).isEqualTo(AttendanceStatus.LATE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 18))).isEqualTo(AttendanceStatus.ATTENDANCE);
            softly.assertThat(crew.getAttendanceStatusByDate(LocalDate.of(2025, 02, 19))).isEqualTo(AttendanceStatus.ABSENT);
        });
    }

    @Test
    void 출석_상태별_횟수를_계산한다() {
        Crew crew = new Crew("pobi");
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 06));
        // 출석
        crew.attendance(LocalDate.of(2025, 02, 4), LocalTime.of(10, 05));
        // 결석
        crew.attendance(LocalDate.of(2025, 02, 5), LocalTime.of(10, 31));

        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 10));
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(attendanceStatusStatistics.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(attendanceStatusStatistics.get(AttendanceStatus.LATE)).isEqualTo(1);
            softly.assertThat(attendanceStatusStatistics.get(AttendanceStatus.ABSENT)).isEqualTo(3);
        });
    }

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
            crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 6));

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
            crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 7));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.WARNING);
    }

    @Test
    void 결석_3회_이상인_경우_면담_대상자가_된다() {
        Crew crew = new Crew("pobi");
        // 지각
        crew.attendance(LocalDate.of(2025, 02, 3), LocalTime.of(13, 10));

        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 7));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.INTERVIEW);
    }

    @Test
    void 결석_6회_이상인_경우_제적_대상자가_된다() {
        Crew crew = new Crew("pobi");        Map<AttendanceStatus, Integer> attendanceStatusStatistics =
            crew.getAttendanceStatusStatistics(LocalDate.of(2025, 02, 11));

        Manage manage = Manage.of(attendanceStatusStatistics);
        assertThat(manage).isEqualTo(Manage.EXPELLED);
    }
}
