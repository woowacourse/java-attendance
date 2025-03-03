package domain;

import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource(value =
            {
                    "24,13:31,ABSENCE",
                    "24,13:30,TARDINESS",
                    "24,13:05,ATTENDANCE",
                    "25,10:31,ABSENCE",
                    "25,10:06,TARDINESS",
                    "25,10:05,ATTENDANCE",
            }
    )
    void 날짜와_시간이_주어지면_출석상태를_계산한다(int day, String time, AttendanceStatus status) {
        // given
        LocalDate now = LocalDate.of(2025, 2, 27);
        CampusDate campusDate = CampusDate.ofDateWithDay(now, day);
        CampusTime campusTime = CampusTime.from(time);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateByDateAndTime(campusDate, campusTime);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(status);
    }
}
