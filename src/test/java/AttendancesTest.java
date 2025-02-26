import domain.AttendanceTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    void 출석목록에_출석기록을_추가한다() {
        //given
        Crew crew = new Crew("쿠키");
        AttendanceTime time = new AttendanceTime(LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance = new Attendance(crew, time);
        Attendances attendances = new Attendances(new ArrayList<>());
        Attendances expected = new Attendances(List.of(attendance));
        //when
        attendances.add(attendance);
        //then
        Assertions.assertThat(expected).isEqualTo(attendances);
    }
}
