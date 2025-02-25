package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateInfoTest {

    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:31,결석",
            "2025,2,20,10:05,출석",
            "2025,2,21,10:06,지각"})
    void 결석_출석_지각_확인(int year, int month, int day, String time, String expectedStatus) {
        //given
        LocalDate now = LocalDate.of(year, month, day);
        CampusTime campusTime = CampusTime.fromHourColonMinute(time);
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);

        //when
        AttendanceStatus attendanceStatus = dateInfo.getAttendanceStatus();

        //then
        Assertions.assertThat(attendanceStatus.getStatus()).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,10:31,09:59,출석",
            "2025,2,19,10:31,10:11,지각",
            "2025,2,19,10:11,10:31,결석",
            "2025,2,19,10:11,09:50,출석",
            "2025,2,19,09:59,10:11,지각",
            "2025,2,19,09:59,10:31,결석"})
    void 결석_출석으로_수정_확인(int year, int month, int day, String beforeTime, String afterTime, String expectedStatus) {
        //given
        LocalDate now = LocalDate.of(year, month, day);
        CampusTime beforeCampusTime = CampusTime.fromHourColonMinute(beforeTime);
        DateInfo beforeDateInfo = DateInfo.fromCampusTime(now, beforeCampusTime);
        CampusTime modifyCampusTime = CampusTime.fromHourColonMinute(afterTime);

        //when
        beforeDateInfo.modifyAttendanceTime(modifyCampusTime);

        //then
        Assertions.assertThat(beforeDateInfo.getAttendanceStatus().getStatus()).isEqualTo(expectedStatus);
    }

    @Test
    void 주말_정보가_들어오면_예외_반환() {
        // given
        LocalDate now = LocalDate.of(2025, 2, 23);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");

        // when // then
        Assertions.assertThatThrownBy(() -> DateInfo.fromCampusTime(now, campusTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NOT_WEEKEND.getMessage());
    }

    @Test
    void 해당_정보가_존재하면_true_반환() {
        // given
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);

        // when // then
        Assertions.assertThat(dateInfo.isAttendanceDay(25)).isTrue();
    }

    @Test
    void 해당_정보가_존재하면_false_반환() {
        // given
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);

        // when // then
        Assertions.assertThat(dateInfo.isAttendanceDay(24)).isFalse();
    }

}

