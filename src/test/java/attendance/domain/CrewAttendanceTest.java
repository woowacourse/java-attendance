package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class CrewAttendanceTest {

    @Test
    void 생성시_출석중_특정_크루의_출석이_아니라면_예외가_발생한다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 3, 10, 1)),
                new Attendance("neo", LocalDateTime.of(2024, 12, 3, 10, 1))
        );

        //when & then
        assertThatThrownBy(() -> new CrewAttendance("pobi", attendances))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("pobi의 출석만 이용하여 생성가능합니다.");
    }

    @Test
    void 크루의_출결_상태를_알_수_있다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 3, 10, 1)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 4, 10, 1))
        );
        CrewAttendance crewAttendance = new CrewAttendance("pobi", attendances);
        AttendanceDate attendanceEndDate = new AttendanceDate(LocalDate.of(2024, 12, 5));

        //when
        AttendanceResult attendanceResult = crewAttendance.getAttendanceCount(attendanceEndDate);

        //then
        assertThat(attendanceResult)
                .isEqualTo(new AttendanceResult(
                        "pobi",
                        Map.of(
                                AttendanceStatus.ATTENDANCE, 2,
                                AttendanceStatus.ABSENT, 2
                        )
                ));
    }
}
