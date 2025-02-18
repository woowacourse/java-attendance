import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.assertj.core.api.Assertions;
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
        crew.attendance(LocalDate.now(), LocalTime.of(10, 01));
        Assertions.assertThatThrownBy(() -> {
            crew.attendance(LocalDate.now(), LocalTime.of(10, 01));
        }).isInstanceOf(AlreadyAttendanceException.class);
    }
}
