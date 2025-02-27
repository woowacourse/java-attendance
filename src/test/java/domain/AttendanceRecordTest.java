package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceRecordTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("출석 상태를 계산하여 반환한다.")
        @ParameterizedTest
        @MethodSource("provideDateTimeAndAttendanceStatus")
        public void calculateAttendanceStatus(final LocalDateTime info, final AttendanceStatus expected)
                throws Exception {
            // given
            final AttendanceRecord attendanceRecord = new AttendanceRecord(info);

            // when
            final AttendanceStatus actual = attendanceRecord.calculateAttendanceStatus();

            // then
            assertThat(actual).isEqualByComparingTo(expected);
        }

        private static Stream<Arguments> provideDateTimeAndAttendanceStatus() {
            return Stream.of(
                    Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 5), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 30), AttendanceStatus.LATE),
                    Arguments.of(LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceStatus.ABSENCE)
            );
        }

        @DisplayName("record가 비어있다면, ABSENCE를 반환한다.")
        @Test
        public void calculateAttendanceStatusByEmpty() throws Exception {
            // given
            final AttendanceRecord empty = AttendanceRecord.empty(LocalDate.of(2024, 12, 2));

            // when
            final AttendanceStatus actual = empty.calculateAttendanceStatus();

            // then
            assertThat(actual).isEqualByComparingTo(AttendanceStatus.ABSENCE);
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }
}
