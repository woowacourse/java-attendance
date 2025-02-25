package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

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
}
