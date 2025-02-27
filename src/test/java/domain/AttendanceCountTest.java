package domain;

import attendance.domain.Attendance;
import attendance.domain.AttendanceCount;
import attendance.domain.AttendanceStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {

    @Test
    void 출석_지각_결석_횟수를_계산할_수_있다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 3, 10, 6)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 4, 11, 7))
        );

        //when
        AttendanceCount attendanceCount = AttendanceCount.create(attendances);

        //then
        Assertions.assertThat(attendanceCount).isEqualTo(new AttendanceCount(
                Map.of(
                        AttendanceStatus.ATTENDANCE, 1,
                        AttendanceStatus.LATE, 1,
                        AttendanceStatus.ABSENT, 1
                )
        ));
    }
}
