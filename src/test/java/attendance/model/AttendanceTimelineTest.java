package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceTimeline.AttendanceLog;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("출석 기록 테스트")
class AttendanceTimelineTest {

    @DisplayName("크루의 오늘까지 출석 기록을 생성할 수 있다.")
    @Test
    void name() {
        Crew crew = new Crew("포비");
        Set<Attendance> attendances = Set.of(
                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 10, 6))
        );
        LocalDate now = LocalDate.of(2024, 12, 5);
        final var attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(attendances, now);

        assertThat(attendanceTimeline)
                .extracting("attendanceLogs")
                .isEqualTo(List.of(
                        new AttendanceLog(LocalDate.of(2024, 12, 2),
                                LocalTime.of(10, 1),
                                AttendanceType.OK
                        ),
                        new AttendanceLog(LocalDate.of(2024, 12, 3),
                                LocalTime.of(10, 6),
                                AttendanceType.LATE
                        ),
                        new AttendanceLog(LocalDate.of(2024, 12, 4),
                                null,
                                AttendanceType.ABSENCE
                        ),
                        new AttendanceLog(LocalDate.of(2024, 12, 5),
                                null,
                                AttendanceType.ABSENCE
                        )
                ));
    }
}