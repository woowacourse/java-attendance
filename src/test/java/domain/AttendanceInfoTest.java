package domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceInfoTest {

    @Test
    void 캠퍼스시간과_캠퍼스날짜를_입력받아_객체를_생성한다() {
        // given
        CampusTime campusTime = CampusTime.from("10:29");
        CampusDate campusDate = CampusDate.fromNow(LocalDate.of(2025, 2, 27));

        // when
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(campusDate, campusTime);

        // then
        Assertions.assertThat(attendanceInfo.getMonth()).isEqualTo(2);
        Assertions.assertThat(attendanceInfo.getDay()).isEqualTo(27);
        Assertions.assertThat(attendanceInfo.getHour()).isEqualTo(10);
        Assertions.assertThat(attendanceInfo.getMinute()).isEqualTo(29);
    }

}
