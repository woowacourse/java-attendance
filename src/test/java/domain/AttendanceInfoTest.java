package domain;

import static org.assertj.core.api.Assertions.assertThat;

import domain.attendance.AttendanceInfo;
import domain.attendance.constant.AttendanceStatus;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.DayOfWeek;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceInfoTest {

    @Test
    void 캠퍼스시간과_캠퍼스날짜를_입력받아_객체를_생성한다() {
        // given
        CampusTime campusTime = CampusTime.from("10:29");
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));

        // when
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(campusDate, campusTime);

        // then
        assertThat(attendanceInfo.getMonth()).isEqualTo(2);
        assertThat(attendanceInfo.getDay()).isEqualTo(27);
        assertThat(attendanceInfo.getHour()).isEqualTo(10);
        assertThat(attendanceInfo.getMinute()).isEqualTo(29);
        assertThat(attendanceInfo.getDayOfWeek()).isEqualTo(DayOfWeek.THURSDAY);
    }

    @Test
    void 시간_정보로_출석정보_시간을_수정한다() {
        // given
        CampusTime beforeCampusTime = CampusTime.from("10:29");
        CampusTime afterCampusTime = CampusTime.from("10:31");
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));
        AttendanceInfo beforeInfo = AttendanceInfo.fromDateAndTime(campusDate, beforeCampusTime);

        // when
        AttendanceInfo afterInfo = beforeInfo.modifyInfoByTime(afterCampusTime);

        // then
        assertThat(afterInfo.getHour()).isEqualTo(10);
        assertThat(afterInfo.getMinute()).isEqualTo(31);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "10:31, ABSENCE",
            "10:30, TARDINESS",
            "10:29, TARDINESS",
            "10:06, TARDINESS",
            "10:05, ATTENDANCE",
            "10:04, ATTENDANCE",
            "10:03, ATTENDANCE"
    })
    void 날짜와_시간을_입력하면_출석상태를_계산한다(String inputTime, AttendanceStatus status) {
        // given
        CampusTime campusTime = CampusTime.from(inputTime);
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));

        // when
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(campusDate, campusTime);

        // then
        assertThat(attendanceInfo.getAttendanceStatus()).isEqualTo(status);
    }

}
