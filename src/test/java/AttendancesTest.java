import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @DisplayName("크루들의 출석 기록을 가질 수 있다.")
    @Test
    void create() {
        //given
        Attendance attendances1 = createAttendance("도기");
        Attendance attendances2 = createAttendance("포비");

        //when
        Attendances attendances = new Attendances();
        attendances.add(attendances1);
        attendances.add(attendances2);

        //then
        assertThat(attendances.getAttendances()).hasSize(2);
    }

    private Attendance createAttendance(final String name) {
        Crew crew = Crew.of(name);

        List<LocalDateTime> attendanceTime = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 11, 11);

        attendanceTime.add(localDateTime);
        attendanceTime.add(localDateTime1);

        return new Attendance(crew, attendanceTime);
    }


}
