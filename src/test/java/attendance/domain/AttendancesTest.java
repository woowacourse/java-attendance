package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {


    @DisplayName("출석을 추가한다.")
    @Test
    void 출석을_추가한다() {

        // given
        Attendance attendance1 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 27, 10, 0)));
        Attendances attendances = new Attendances(new ArrayList<>());
        attendances.add(attendance1);
        Attendance attendance2 = new Attendance("체체", new Time(LocalDateTime.of(2025, 2, 28, 10, 0)));

        // when
        attendances.add(attendance2);

        // then
        assertThat(attendances.contains(attendance2)).isTrue();
    }
}
