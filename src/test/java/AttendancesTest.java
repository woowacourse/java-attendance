import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceTime;
import domain.Attendances;
import domain.Crew;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        assertThat(expected).isEqualTo(attendances);
    }

    @Test
    void 출석기록을_추가할_때_오늘의_출석이_존재하면_예외를_발생시킨다() {
        //given
        Crew crew = new Crew("쿠키");
        AttendanceTime time = new AttendanceTime(LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance = new Attendance(crew, time);
        Attendances expected = new Attendances(List.of(attendance));
        //when
        assertThatThrownBy(() -> expected.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석이 이미 존재합니다.");

        //then
    }
}
