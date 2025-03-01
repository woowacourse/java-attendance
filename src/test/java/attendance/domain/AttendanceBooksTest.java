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
public class AttendanceBooksTest {

    @Test
    void 출석_기록을_추가한다() {
        // given
        String name = "빙봉";
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.of(10, 8);
        AttendanceBooks attendanceBooks = new AttendanceBooks();

        // when
        attendanceBooks.addAttendance(name, new Attendance(attendanceDate, attendanceTime));

        // then
        assertThat(attendanceBooks.hasAttendance(name, attendanceDate, attendanceTime)).isTrue();
    }

    @Test
    void 기존_출석_기록_있다면_true_반환한다() {
        // given
        String name = "빙봉";
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime attendanceTime = LocalTime.of(10, 8);

        AttendanceBooks attendanceBooks = new AttendanceBooks();
        attendanceBooks.addAttendance(name, new Attendance(today, attendanceTime));

        // when & then
        assertThat(attendanceBooks.hasAttendance(name, today, attendanceTime)).isTrue();

    }

    @Test
    void 기존_출석_기록_없다면_false_반환한다() {
        // given
        String name = "빙봉";
        LocalDate today = LocalDate.of(2024, 12, 13);
        LocalTime absenceTime = LocalTime.of(10, 31);
        LocalTime lateTime = LocalTime.of(10, 6);

        AttendanceBooks attendanceBooks = new AttendanceBooks();
        attendanceBooks.addAttendance(name, new Attendance(today, absenceTime));
        attendanceBooks.addAttendance("빙티", new Attendance(today, lateTime));

        // when & then
        assertThat(attendanceBooks.hasAttendance("빙티", today, absenceTime)).isFalse();

    }

    @Test
    void 출석기록에_없는_이름인_경우_예외가_발생한다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        attendanceBooks.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)));

        // when & then
        assertThatThrownBy(() -> attendanceBooks.validateNameExists("철수"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 출석기록에_있는_이름인_경우_예외가_발생하지_않는다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        attendanceBooks.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)));

        // when & then
        assertThatCode(() -> attendanceBooks.validateNameExists("빙티"))
            .doesNotThrowAnyException();
    }

    @Test
    void 출석수정시_출석기록이_없었던_경우_null반환한다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        attendanceBooks.addAttendance(
            "빙티", new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(9, 50)));

        // when
        LocalDate monday = LocalDate.of(2024, 12, 2);
        LocalTime presenceTime = LocalTime.of(12, 50);
        Optional<LocalTime> beforeEditTime = attendanceBooks.editAttendance("빙티", monday, presenceTime);

        // then
        assertThat(beforeEditTime.isEmpty()).isTrue();
    }

    @Test
    void 출석수정시_출석기록이_있는_경우_수정전_출석시간을_반환한다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalTime presenceTime = LocalTime.of(12, 50);
        attendanceBooks.addAttendance(
            "빙티", new Attendance(monday, presenceTime));

        // when
        LocalTime absenceTime = LocalTime.of(13, 31);
        Optional<LocalTime> beforeEditTime = attendanceBooks.editAttendance("빙티", monday, absenceTime);

        // then
        assertThat(beforeEditTime.isPresent()).isTrue();
        assertThat(beforeEditTime.get()).isEqualTo(presenceTime);
    }

    @Test
    void 전날까지의_출석기록을_조회해온다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        LocalDate twodaysAgo = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendanceBooks.addAttendance(
            "빙티", new Attendance(twodaysAgo, LocalTime.of(10, 1)));
        attendanceBooks.addAttendance(
            "빙티", new Attendance(yesterday, LocalTime.of(10, 2)));
        attendanceBooks.addAttendance(
            "빙티", new Attendance(today, LocalTime.of(10, 3)));

        // when
        List<Attendance> attendancesUntilYesterday = attendanceBooks.findAttendanceUntilYesterday("빙티", today);

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
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        LocalDate twodaysAgo = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendanceBooks.addAttendance(
            "빙티", new Attendance(twodaysAgo, LocalTime.of(10, 1)));
        attendanceBooks.addAttendance(
            "빙티", new Attendance(yesterday, LocalTime.of(10, 6)));
        attendanceBooks.addAttendance(
            "빙티", new Attendance(today, LocalTime.of(10, 3)));

        // when
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendanceBooks.countAttendanceStatus("빙티", today);

        // then
        assertThat(attendanceStatusCount).isEqualTo(
            Map.of(
                AttendanceStatus.PRESENCE, 1,
                AttendanceStatus.LATE, 1,
                AttendanceStatus.ABSENCE, 1
            )
        );
    }

    @Test
    void 모든_크루_이름을_조회한다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        LocalDate twodaysAgo = LocalDate.of(2024, 12, 3);
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendanceBooks.addAttendance(
            "빙티", new Attendance(twodaysAgo, LocalTime.of(10, 1)));
        attendanceBooks.addAttendance(
            "빙봉", new Attendance(yesterday, LocalTime.of(10, 6)));
        attendanceBooks.addAttendance(
            "루디", new Attendance(today, LocalTime.of(10, 3)));

        // when
        List<String> crewNames = attendanceBooks.getAllCrewNames();

        // then
        assertThat(crewNames).isEqualTo(List.of("빙티", "빙봉", "루디"));
    }

    @Test
    void 출석을_원하는_날짜에_출석기록이_있으면_예외를_반환한다() {
        // given
        AttendanceBooks attendanceBooks = new AttendanceBooks();
        LocalDate yesterday = LocalDate.of(2024, 12, 4);
        LocalDate today = LocalDate.of(2024, 12, 5);
        attendanceBooks.addAttendance(
            "빙봉", new Attendance(yesterday, LocalTime.of(10, 6)));
        attendanceBooks.addAttendance(
            "루디", new Attendance(today, LocalTime.of(10, 3)));

        // when & then
        assertThatThrownBy(() -> attendanceBooks.hasAttendance("루디", today))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 이미 출석기록이 존재합니다. 출석 수정을 이용해주세요.");
    }
}
