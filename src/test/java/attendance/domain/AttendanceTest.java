package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceTest {

    @Test
    void 출석시간이_캠퍼스_오픈전이면_예외가_발생한다() {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);
        LocalTime beforeOpen = LocalTime.of(7, 59);

        assertThatThrownBy(() -> new Attendance(friday, beforeOpen))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다. 운영시간은 08:00 ~ 23:00 입니다.");
    }

    @Test
    void 출석시간이_캠퍼스_종료후면_예외가_발생한다() {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);
        LocalTime afterClose = LocalTime.of(23, 1);

        assertThatThrownBy(() -> new Attendance(friday, afterClose))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다. 운영시간은 08:00 ~ 23:00 입니다.");
    }

    @Test
    void 출석시간이_캠퍼스_운영시간이면_예외가_발생하지_않는다() {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);
        LocalTime presenceTime = LocalTime.of(9, 50);

        assertThatCode(() -> new Attendance(friday, presenceTime))
            .doesNotThrowAnyException();
    }

    @Test
    void 해당_날짜에_출석기록이_존재하는지_확인한다() {
        //given
        LocalDate friday = LocalDate.of(2024, 12, 13);
        Attendance attendance = new Attendance(friday, LocalTime.of(10, 0));

        // when
        LocalDate thursday = LocalDate.of(2024, 12, 12);
        boolean existsAttendanceOnFriday = attendance.hasAttendDate(friday);
        boolean existsAttendanceOnThursday = attendance.hasAttendDate(thursday);

        // then
        assertThat(existsAttendanceOnFriday).isTrue();
        assertThat(existsAttendanceOnThursday).isFalse();
    }

    @Test
    void 이전_날짜인지_확인한다() {
        // given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2));

        // when
        boolean before = attendance.isBefore(LocalDate.of(2024, 12, 5));

        // then
        assertThat(before).isTrue();
    }
}
