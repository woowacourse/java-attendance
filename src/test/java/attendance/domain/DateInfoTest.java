package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.DayOfWeek;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateInfoTest {

    @ParameterizedTest
    @CsvSource(value = {"2,19,3,10:31,결석",
    "2,20,4,10:05,출석",
    "2,21,5,10:06,지각"})
    void 결석_출석_지각_확인(int month, int day, int dayNumber, String timeNumber, String expectedStatus) {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayNumber);
        Time time = Time.from(timeNumber);
        DateInfo dateInfo = DateInfo.of(month, day, dayOfWeek, time);

        //when
        String attendanceStatus = dateInfo.getAttendanceStatus();

        //then
        Assertions.assertThat(attendanceStatus).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,19,3,10:31,09:59,출석",
    "2,19,3,10:31,10:11,지각",
    "2,19,3,10:11,10:31,결석",
    "2,19,3,10:11,09:50,출석",
    "2,19,3,09:59,10:11,지각",
    "2,19,3,09:59,10:31,결석"})
    void 결석_출석으로_수정_확인(int month, int day, int dayNumber, String beforeTime, String afterTime, String expectedStatus) {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayNumber);
        Time prevTime = Time.from(beforeTime);
        DateInfo dateInfo = DateInfo.of(month, day, dayOfWeek, prevTime);
        Time modifyTime = Time.from(afterTime);
        //when
        dateInfo.modifyAttendanceTime(modifyTime);

        //then
        Assertions.assertThat(dateInfo.getAttendanceStatus()).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,19,3,10:31,1", "2,19,3,09:59,0", "2,19,3,10:11,0"})
    void 결석_개수_반환(int month, int day, int dayNumber, String timeNumber, int expectedStatus) {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayNumber);
        Time time = Time.from(timeNumber);
        DateInfo dateInfo = DateInfo.of(month, day, dayOfWeek, time);

        //when & then
        Assertions.assertThat(dateInfo.checkAbsenceStatus()).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,19,3,10:11,1", "2,19,3,09:59,0", "2,19,3,10:31,0"})
    void 지각_개수_반환(int month, int day, int dayNumber, String timeNumber, int expectedStatus) {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayNumber);
        Time time = Time.from(timeNumber);
        DateInfo dateInfo = DateInfo.of(month, day, dayOfWeek, time);

        //when & then
        Assertions.assertThat(dateInfo.checkLateStatus()).isEqualTo(expectedStatus);
    }

    @ParameterizedTest
    @CsvSource(value = {"2,19,3,10:11,0", "2,19,3,09:59,1", "2,19,3,10:31,0"})
    void 출석_개수_반환(int month, int day, int dayNumber, String timeNumber, int expectedStatus) {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.from(dayNumber);
        Time time = Time.from(timeNumber);
        DateInfo dateInfo = DateInfo.of(month, day, dayOfWeek, time);

        //when & then
        Assertions.assertThat(dateInfo.checkAttendanceStatus()).isEqualTo(expectedStatus);
    }
}