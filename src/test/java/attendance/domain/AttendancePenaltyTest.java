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

        @ParameterizedTest
        @CsvSource(
            {
                "3, 2",
                "6, 2"
            }
        )
        void 지각3회를_결석1회로_간주하여_3회이상일시_면담으로_간주한다(
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
            assertThat(penalty).isEqualTo(AttendancePenalty.INTERVIEW);
        }

        @ParameterizedTest
        @CsvSource(
            {
                "3, 1",
                "6, 0"
            }
        )
        void 지각3회를_결석1회로_간주하여_2회이상일시_경고로_간주한다(
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
            assertThat(penalty).isEqualTo(AttendancePenalty.WARNING);
        }

        @ParameterizedTest
        @CsvSource(
            {
                "3, 0",
                "1, 0"
            }
        )
        void 지각3회를_결석1회로_간주하여_1회이상일시_패널티없음으로_간주한다(
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
            assertThat(penalty.isNoPenalty()).isTrue();
        }
    }
}
