package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {
    @Test
    @DisplayName("날짜와 시간으로 알맞는 출석 상태를 반환한다")
    void ofTest() {
        // given
        LocalDate monday = LocalDate.of(2025, 2, 3);
        LocalTime mondayAttendanceTime = LocalTime.of(13, 1);
        LocalTime mondayLateTime1 = LocalTime.of(13, 6);
        LocalTime mondayLateTime2 = LocalTime.of(13, 30);
        LocalTime mondayAbsentLateTime = LocalTime.of(13, 31);

        // when & then
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(AttendanceStatus.of(monday, mondayAttendanceTime))
                    .isEqualByComparingTo(AttendanceStatus.ATTENDANCE);
            softAssertions.assertThat(AttendanceStatus.of(monday, mondayLateTime1))
                    .isEqualByComparingTo(AttendanceStatus.LATE);
            softAssertions.assertThat(AttendanceStatus.of(monday, mondayLateTime2))
                    .isEqualByComparingTo(AttendanceStatus.LATE);
            softAssertions.assertThat(AttendanceStatus.of(monday, mondayAbsentLateTime))
                    .isEqualByComparingTo(AttendanceStatus.ABSENT_LATE);
        });
    }
}