package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @Nested
    class ValidCases {

        @ParameterizedTest
        @CsvSource(
            value = {"null, null",
                "13, 31"}
            ,
            nullValues = {"null"}
        )
        void 출석시간을_기준으로_30분초과면_결석이다(
            Integer hour,
            Integer minute
        ) {
            // given
            AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                new AttendanceTime(hour, minute));

            // when
            AttendanceStatus status = AttendanceStatus.from(attendanceDateTime);

            // then
            assertThat(status).isEqualTo(AttendanceStatus.ABSENCE);
        }

        @Test
        void 출석시간을_기즌으로_5분초과면_지각이다() {
            // given
            AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                new AttendanceTime(13, 6));

            // when
            AttendanceStatus status = AttendanceStatus.from(attendanceDateTime);

            // then
            assertThat(status).isEqualTo(AttendanceStatus.LATE);
        }

        @ParameterizedTest
        @CsvSource(
            {
                "8, 0",
                "13, 0",
                "13, 5"
            }
        )
        void 출석시간을_기준으로_기준을_지킨다면_출석이다(
            Integer hour,
            Integer minute
        ) {
            // given
            AttendanceDateTime attendanceDateTime = new AttendanceDateTime(
                new AttendanceDate(2024, 12, 2),
                new AttendanceTime(hour, minute));

            // when
            AttendanceStatus status = AttendanceStatus.from(attendanceDateTime);

            // then
            assertThat(status).isEqualTo(AttendanceStatus.ATTENDANCE);
        }
    }
}
