package attendance.domain.constant;

import attendance.domain.CampusTime;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,23,09:59,HOLIDAY",
            "2025,2,24,13:01,ATTENDANCE",
            "2025,2,24,13:06,LATE",
            "2025,2,25,10:29,LATE",
            "2025,2,26,10:31,ABSENCE",
    })
    void 날짜와_시간으로_출석_상태_계산(int year, int month, int day, String hourMinute, AttendanceStatus status) {
        // given
        LocalDate date = LocalDate.of(year, month, day);
        CampusTime campusTime = CampusTime.fromHourColonMinute(hourMinute);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateAttendanceStatus(date, campusTime);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(status);
    }

}
