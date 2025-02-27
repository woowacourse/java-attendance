package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("출석 시간이 기준 시간을 5분 초과, 30분 이하라면 지각처리된다.")
        @Test
        public void calculateStatus() throws Exception {
            // given
            final DayOfWeek tuesday = DayOfWeek.TUESDAY;
            final LocalTime lateTime1 = LocalTime.of(10, 6);
            final LocalTime lateTime2 = LocalTime.of(10, 30);

            // when
            final AttendanceStatus actual1 = AttendanceStatus.calculateStatus(lateTime1, tuesday);
            final AttendanceStatus actual2 = AttendanceStatus.calculateStatus(lateTime2, tuesday);

            // then
            assertThat(actual1).isEqualByComparingTo(AttendanceStatus.LATE);
            assertThat(actual2).isEqualByComparingTo(AttendanceStatus.LATE);
        }

        @DisplayName("출석 시간이 기준 시간을 30분 초과라면, 결석처리된다.")
        @Test
        public void calculateStatus2() throws Exception {
            // given
            final DayOfWeek tuesday = DayOfWeek.TUESDAY;
            final LocalTime absenceTime = LocalTime.of(10, 31);

            // when
            final AttendanceStatus actual = AttendanceStatus.calculateStatus(absenceTime, tuesday);

            // then
            assertThat(actual).isEqualByComparingTo(AttendanceStatus.ABSENCE);
        }

        @DisplayName("출석 시간이 기준 시간 + 5분 이하라면, 출석처리된다.")
        @Test
        public void calculateStatus3() throws Exception {
            // given
            final DayOfWeek tuesday = DayOfWeek.TUESDAY;
            final LocalTime attendanceTime = LocalTime.of(10, 5);

            // when
            final AttendanceStatus actual = AttendanceStatus.calculateStatus(attendanceTime, tuesday);

            // then
            assertThat(actual).isEqualByComparingTo(AttendanceStatus.ATTENDANCE);
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }
}
