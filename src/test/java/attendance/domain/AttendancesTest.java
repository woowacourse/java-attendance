package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendancesTest {

    @Test
    void 출석_기록을_추가한다() {
        // given
        String name = "빙봉";
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.of(10, 8);
        Attendances attendances = new Attendances();

        // when
        attendances.addAttendance(name, new Attendance(attendanceDate, attendanceTime));

        // then
        assertThat(attendances.hasAttendance(name, attendanceDate, attendanceTime)).isTrue();
    }

    @Test
    void 기존_출석_기록_있다면_true_반환한다() {
        // given
        String name = "빙봉";
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.of(10, 8);

        Attendances attendances = new Attendances();
        attendances.addAttendance(name, new Attendance(today, attendanceTime));

        // when & then
        assertThat(attendances.hasAttendance(name, today, attendanceTime)).isTrue();

    }

    @Test
    void 기존_출석_기록_없다면_false_반환한다() {
        // given
        String name = "빙봉";
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime absenceTime = LocalTime.of(10, 31);
        LocalTime lateTime = LocalTime.of(10, 6);

        Attendances attendances = new Attendances();
        attendances.addAttendance(name, new Attendance(today, absenceTime));
        attendances.addAttendance("빙티", new Attendance(today, lateTime));

        // when & then
        assertThat(attendances.hasAttendance("빙티", today, absenceTime)).isFalse();

    }

    @Test
    void 출석기록에_없는_이름인_경우_예외가_발생한다() {
        // given
        Attendances attendances = new Attendances();
        attendances.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)));

        // when & then
        assertThatThrownBy(() -> attendances.validateNameExists("철수"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 출석기록에_있는_이름인_경우_예외가_발생하지_않는다() {
        // given
        Attendances attendances = new Attendances();
        attendances.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)));

        // when & then
        assertThatCode(() -> attendances.validateNameExists("빙티"))
            .doesNotThrowAnyException();
    }

    @Test
    void 출석수정시_출석기록이_없었던_경우_null반환한다() {
        // given
        Attendances attendances = new Attendances();
        attendances.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(9, 50)));

        // when
        LocalDate monday = LocalDate.of(2024, 12, 2);
        LocalTime presenceTime = LocalTime.of(12, 50);
        Optional<LocalTime> beforeEditTime = attendances.editAttendance("빙티", monday, presenceTime);

        // then
        assertThat(beforeEditTime.isEmpty()).isTrue();
    }

    @Test
    void 출석수정시_출석기록이_있는_경우_수정전_출석시간을_반환한다() {
        // given
        Attendances attendances = new Attendances();
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalTime presenceTime = LocalTime.of(12, 50);
        attendances.addAttendance(
            "빙티", new Attendance(monday, presenceTime));

        // when
        LocalTime absenceTime = LocalTime.of(13, 31);
        Optional<LocalTime> beforeEditTime = attendances.editAttendance("빙티", monday, absenceTime);

        // then
        assertThat(beforeEditTime.isPresent()).isTrue();
        assertThat(beforeEditTime.get()).isEqualTo(presenceTime);
    }

    @Test
    void 전날까지의_출석기록을_조회해온다() {
        // given
        Attendances attendances = new Attendances();
        LocalDate twodaysAgo = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendances.addAttendance(
            "빙티", new Attendance(twodaysAgo, LocalTime.of(10, 1)));
        attendances.addAttendance(
            "빙티", new Attendance(yesterday, LocalTime.of(10, 2)));
        attendances.addAttendance(
            "빙티", new Attendance(today, LocalTime.of(10, 3)));

        // when
        List<Attendance> attendancesUntilYesterday = attendances.findAttendanceUntilYesterday("빙티", today);

        // then
        assertThat(attendancesUntilYesterday).containsExactlyElementsOf(
            List.of(
                new Attendance(LocalDate.of(2024, 12, 2), null),
                new Attendance(twodaysAgo, LocalTime.of(10, 1)),
                new Attendance(yesterday, LocalTime.of(10, 2))
            )
        );
    }

    @Test
    void 크루의_출결상태를_집계한다() {
        // given
        Attendances attendances = new Attendances();
        LocalDate twodaysAgo = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendances.addAttendance(
            "빙티", new Attendance(twodaysAgo, LocalTime.of(10, 1)));
        attendances.addAttendance(
            "빙티", new Attendance(yesterday, LocalTime.of(10, 6)));
        attendances.addAttendance(
            "빙티", new Attendance(today, LocalTime.of(10, 3)));

        // when
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendances.countAttendanceStatus("빙티", today);

        // then
        assertThat(attendanceStatusCount).isEqualTo(
            Map.of(
                AttendanceStatus.PRESENCE, 1,
                AttendanceStatus.LATE, 1,
                AttendanceStatus.ABSENCE, 1
            )
        );
    }
}
