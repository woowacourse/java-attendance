package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendancePenaltyTest {

    @Nested
    class ValidCases {

        @ParameterizedTest
        @CsvSource(
            {
                "3, 5",
                "6, 4"
            }
        )
        void 지각3회를_결석1회로_간주하여_5회초과일시_제적으로_간주한다(
            int lateCount,
            int absenceCount
        ) {
            // given
            Map<AttendanceStatus, Integer> attendanceStatusCount = Map.of(
                AttendanceStatus.LATE, lateCount,
                AttendanceStatus.ABSENCE, absenceCount
            );

            // when
            AttendancePenalty penalty = AttendancePenalty.from(
                attendanceStatusCount);

            // then
            assertThat(penalty).isEqualTo(AttendancePenalty.WEEDING);
        }
}
