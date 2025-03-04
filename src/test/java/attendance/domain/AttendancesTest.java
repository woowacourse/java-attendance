package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 객체 일급 컬렉션")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttendancesTest {

    @Test
    void 출석_객체가_정상적으로_생성된다() {
        Attendances attendances = new Attendances();

        assertThat(attendances).isNotNull();
    }

    @Test
    void 출석_정보를_정상적으로_저장한다() {
        Attendances attendances = new Attendances();

        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime time = LocalTime.of(10, 5);

        attendances.addAttendance(date, time);

        Attendance savedAttendance = attendances.getAttendances().get(date);
        assertThat(savedAttendance.time().getTime()).isEqualTo(time);
    }

    @Test
    void 해당_날짜에_출석_기록이_있으면_true를_반환한다() {
        Attendances attendances = new Attendances();
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime time = LocalTime.of(10, 5);
        attendances.addAttendance(date, time);

        assertThat(attendances.isAttendedDate(date)).isTrue();
    }


    @Test
    void 특정_날짜의_현재_출석_정보를_반환한다() {
        Attendances attendances = new Attendances();
        LocalDate date = LocalDate.of(2024, 12, 5);
        LocalTime time = LocalTime.of(10, 5);
        Attendance attendance = new Attendance(date, new AttendanceLocalTime(time));
        attendances.addAttendance(date, time);

        assertThat(attendances.getCurrentAttendance(date)).isEqualTo(attendance);
    }

}
