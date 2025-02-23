package domain.constants;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceStatusTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("주어진 시간에 대해서 주어진 출석 경계 시간이 어떤 상태인지를 반환한다. (출석)")
        @ParameterizedTest
        @MethodSource("provideAttendanceStatus")
        public void of(final LocalTime attendanceTime, final AttendanceStatus expected) throws Exception {
            // given
            final int boundaryHour = 10;
            final int boundaryMinute = 0;

            // when
            final AttendanceStatus actual = AttendanceStatus.of(attendanceTime, boundaryHour, boundaryMinute);

            // then
            assertThat(actual).isSameAs(expected);
        }

        private static Stream<Arguments> provideAttendanceStatus() {
            return Stream.of(
                    Arguments.of(LocalTime.of(10, 0), AttendanceStatus.ATTENDANCE),
                    Arguments.of(LocalTime.of(10, 6), AttendanceStatus.LATE),
                    Arguments.of(LocalTime.of(10, 31), AttendanceStatus.ABSENCE)
            );
        }

        @DisplayName("MatchTimeMinuteBoundary를 기준으로 오름차순 정렬한 Status를 반환한다.")
        @Test
        public void sortedStatus() throws Exception {
            // given
            final List<AttendanceStatus> expected = List.of(AttendanceStatus.ATTENDANCE, AttendanceStatus.LATE,
                    AttendanceStatus.ABSENCE);

            // given & when
            final List<AttendanceStatus> actual = AttendanceStatus.sortedStatus();

            // then
            assertThat(actual).isEqualTo(expected);
        }

    }

}
