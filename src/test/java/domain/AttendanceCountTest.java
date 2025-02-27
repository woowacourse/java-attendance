package domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import attendance.domain.AttendanceCount;
import attendance.domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {

    @Test
    void 출결을_계산할_수_있다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 3, 10, 6)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 4, 11, 7))
        );
        LocalDate endDate = LocalDate.of(2024, 12, 4);

        //when
        AttendanceCount attendanceCount = AttendanceCount.create("pobi", attendances, endDate);

        //then
        assertThat(attendanceCount).isEqualTo(new AttendanceCount(
                Map.of(
                        AttendanceStatus.ATTENDANCE, 1,
                        AttendanceStatus.LATE, 1,
                        AttendanceStatus.ABSENT, 1
                )
        ));
    }
}
