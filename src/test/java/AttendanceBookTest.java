import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 평일_출석_저장_테스트() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var time = "09:59";
        Attend attend = Attend.of(time);

        //when
        attendanceBook.attend(name, attend);

        //then
        Assertions.assertThat(attendanceBook.findByName(name).attends).hasSize(1);
    }

    @Test
    void 주말_출석_저장_시도하면_예외() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var date = "14";
        var time = "09:59";
        Attend attend = Attend.of(date, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 공휴일_출석_저장_시도하면_예외() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        var name = "플린트";
        var date = "25";
        var time = "09:59";
        Attend attend = Attend.of(date, time);

        //when & then
        assertThatThrownBy(() -> attendanceBook.attend(name, attend))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
