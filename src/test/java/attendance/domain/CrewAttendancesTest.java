package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewAttendancesTest {

    @Test
    void 크루들의_전체_출석_결과를_알_수_있다() {
        //given
        CrewAttendances crewAttendances = new CrewAttendances(
                new CrewAttendance(
                        "pobi",
                        new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 11, 1))
                ),
                new CrewAttendance(
                        "neo",
                        new Attendance("neo", LocalDateTime.of(2024, 12, 2, 10, 1))
                )
        );

        //when
        List<AttendanceResult> result = crewAttendances.createAllAttendanceResult(LocalDate.of(2024, 12, 3));

        //then
        Assertions.assertThat(result).isEqualTo(
                List.of(
                        new AttendanceResult("pobi",
                                Map.of(
                                        AttendanceStatus.ABSENT, 2
                                )),
                        new AttendanceResult("neo",
                                Map.of(
                                        AttendanceStatus.ATTENDANCE, 1,
                                        AttendanceStatus.ABSENT, 1
                                ))
                )
        );
    }
}
