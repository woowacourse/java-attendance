package attendance.model;

import static attendance.model.AttendanceTestFixtures.createAttendanceInRawDateTime;
import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.AttendanceTimeline.AttendanceLog;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 기록 테스트")
class AttendanceTimelineTest {

    @DisplayName("크루의 전날까지 출석 기록을 생성할 수 있다.")
    @Test
    void createTimelineUntilNowTest() {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        Set<Attendance> attendances = Set.of(
                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-03 10:06")
        );
        LocalDate now = LocalDate.of(2024, 12, 5);

        // when
        final var attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(attendances, now);

        // then
        assertThat(attendanceTimeline)
                .extracting("attendanceLogs")
                .isEqualTo(List.of(
                        new AttendanceLog(
                                LocalDate.of(2024, 12, 2),
                                LocalTime.of(10, 1),
                                AttendanceType.OK
                        ),
                        new AttendanceLog(
                                LocalDate.of(2024, 12, 3),
                                LocalTime.of(10, 6),
                                AttendanceType.LATE
                        ),
                        new AttendanceLog(
                                LocalDate.of(2024, 12, 4),
                                null,
                                AttendanceType.ABSENCE
                        )
                ));
    }

    @DisplayName("전날까지 크루의 각 출석 유형에 따른 횟수를 계산할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "OK, 1",
            "LATE, 1",
            "ABSENCE, 1"
    })
    void countAttendanceTypeTest(AttendanceType attendanceType, int expected) {
        // given
        Crew pobi = AttendanceTestFixtures.POBI;
        Set<Attendance> attendances = Set.of(
                createAttendanceInRawDateTime(pobi, "2024-12-02 10:01"),
                createAttendanceInRawDateTime(pobi, "2024-12-03 10:06")
        );
        LocalDate now = LocalDate.of(2024, 12, 5);

        // when
        final var attendanceTimeline = AttendanceTimeline.generateAttendanceTimelineUntilDate(attendances, now);
        int count = attendanceTimeline.countByAttendanceType(attendanceType);

        // then
        assertThat(count)
                .isEqualTo(expected);
    }
}
