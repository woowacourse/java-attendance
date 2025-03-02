package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceManagerTest {

    @Test
    void 출석_데이터를_읽어온다() {
        // given
        AttendanceBooks expectedAttendanceBooks = new AttendanceBooks();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        expectedAttendanceBooks.addAttendance("쿠키", new Attendance(attendanceDate, LocalTime.of(10, 8)));
        expectedAttendanceBooks.addAttendance("빙봉", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendanceBooks.addAttendance("빙티", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendanceBooks.addAttendance("이든", new Attendance(attendanceDate, LocalTime.of(10, 7)));

        // when
        AttendanceManager attendanceManager = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );

        // then
        assertThat(attendanceManager).extracting("attendanceBooks").isEqualTo(expectedAttendanceBooks);
    }

    @Test
    void 출석_성공시_Attendance_객체를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 16);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when
        Attendance result = service.remarkAttendance("빙봉", attendanceDate, attendanceTime);

        // then
        Attendance expectedAttendance = new Attendance(attendanceDate, attendanceTime);
        assertThat(result).isEqualTo(expectedAttendance);
    }

    @Test
    void 출석_날짜가_캠퍼스_운영일이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 14);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 출석_시간이_캠퍼스_운영시간이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024, 12, 16);
        LocalTime attendanceTime = LocalTime.of(23, 59);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다. 운영시간은 08:00 ~ 23:00 입니다.");
    }

    @Test
    void 출석_수정이_성공하면_변경전_Attendance_객체를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 13);
        LocalTime editTime = LocalTime.of(12, 50);
        LocalTime beforeEditTime = LocalTime.of(10, 7);

        // when
        Attendance beforeEditAttendance = service.editAttendance("빙봉", editDate, editTime);

        // then
        Attendance expectedAttendance = new Attendance(editDate, beforeEditTime);
        assertThat(beforeEditAttendance).isEqualTo(expectedAttendance);
    }

    @Test
    void 출석_수정_날짜가_캠퍼스_운영일이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 14);
        LocalTime editTime = LocalTime.of(12, 50);

        // when & then
        assertThatThrownBy(() -> service.editAttendance("빙봉", editDate, editTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 출석_수정_시간이_캠퍼스_운영시간이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate editDate = LocalDate.of(2024, 12, 16);
        LocalTime editTime = LocalTime.of(23, 59);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", editDate, editTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다. 운영시간은 08:00 ~ 23:00 입니다.");
    }

    @Test
    void 출석_조회시_AttendanceCheckResult_를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate second = LocalDate.of(2024, 12, 2);
        LocalDate today = LocalDate.of(2024, 12, 3);

        // when
        AttendanceCheckResult result = service.checkAttendance("빙티", today);

        // then
        AttendanceCheckResult expected = new AttendanceCheckResult("빙티",
            List.of(new Attendance(second, null)),
            Map.of(
                AttendanceStatus.LATE, 0,
                AttendanceStatus.PRESENCE, 0,
                AttendanceStatus.ABSENCE, 1
            ),
            AttendancePenalty.NONE);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void 제적_위험_크루_조회시_dto를_반환한다() {
        // given
        AttendanceManager service = new AttendanceManager(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate today = LocalDate.of(2024, 12, 5);

        // when
        List<PenaltyCrew> result = service.findPenaltyCrews(today);

        // then
        List<PenaltyCrew> expected = List.of(
            new PenaltyCrew("빙티", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0)),
            new PenaltyCrew("이든", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0)),
            new PenaltyCrew("빙봉", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0)),
            new PenaltyCrew("쿠키", Map.of(AttendanceStatus.PRESENCE, 0, AttendanceStatus.ABSENCE, 3, AttendanceStatus.LATE, 0))
        );
        assertThat(result).isEqualTo(expected);
    }
}
