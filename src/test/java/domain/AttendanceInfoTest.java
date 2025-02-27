package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        assertThat(attendanceInfo.getMonth()).isEqualTo(2);
        assertThat(attendanceInfo.getDay()).isEqualTo(27);
        assertThat(attendanceInfo.getHour()).isEqualTo(10);
        assertThat(attendanceInfo.getMinute()).isEqualTo(29);
        assertThat(attendanceInfo.getDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY);
    }

}
