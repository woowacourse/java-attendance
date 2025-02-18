import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
