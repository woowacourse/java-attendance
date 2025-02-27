package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceWeekTest {

    @Test
    public void 월요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 24, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.MONDAY);
    }
    @Test
    public void 화요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 25, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.TUESDAY);
    }
    @Test
    public void 수요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 26, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.WEDNESDAY);
    }
    @Test
    public void 목요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.THURSDAY);
    }
    @Test
    public void 금요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 28, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.FRIDAY);
    }
    @Test
    public void 토요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 3, 1, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.SATURDAY);
    }
    @Test
    public void 일요일_판단() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 3, 2, 9,59);

        //when & then
        Assertions.assertThat(AttendanceWeek.of(localDateTime.getDayOfWeek())).isEqualTo(AttendanceWeek.SUNDAY);
    }
}
