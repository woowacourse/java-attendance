package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.Weekday;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateInfosTest {

    @Test
    void 당일까지의_통계_초기화() {
        //given
        LocalDate now = LocalDate.of(2025, 2, 19);
        AttendanceRegistry dateInfos = AttendanceRegistry.fromDefaultValue(now);

        //when //then
        Assertions.assertThat(dateInfos.getDateInfos()).hasSize(13);
    }

    @Test
    void 결석_숫자_개수_새기() {
        //given
        LocalDate now = LocalDate.of(2025, 2, 19);
        AttendanceRegistry dateInfos = AttendanceRegistry.fromDefaultValue(now);

        //when
        int absentCount = dateInfos.findStatusCounts(AttendanceStatus.ABSENCE);
        int expectedCount = 13;

        //then
        Assertions.assertThat(absentCount).isEqualTo(expectedCount);
    }

    @Test
    void 날짜로_해당날짜정보_반환() {
        //given
        LocalDate now = LocalDate.of(2025, 2, 19);
        AttendanceRegistry dateInfos = AttendanceRegistry.fromDefaultValue(now);

        //when
        DateInfo dateInfo = dateInfos.findByDate(19);

        //then
        Assertions.assertThat(dateInfo.getLocalDateTime().getMonthValue()).isEqualTo(2);
        Assertions.assertThat(dateInfo.getLocalDateTime().getDayOfMonth()).isEqualTo(19);
    }

    @ParameterizedTest
    @CsvSource(value = {"2025,2,19,09:59,1,0,0",
            "2025,2,20,10:06,0,1,0",
            "2025,2,21,10:31,0,0,1"})
    void 출결상태_통계_계산(int year, int month, int day, String time, int expectedAttendance, int expectedLate, int expectedAbsence) {
        //given
        List<String> timeNumbers = List.of(time.split(":"));
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, Integer.parseInt(timeNumbers.get(0)), Integer.parseInt(timeNumbers.get(1)));

        DateInfo dateInfo = DateInfo.of(localDateTime);

        AttendanceRegistry attendanceRegistry = AttendanceRegistry.from(List.of(dateInfo));

        //when
        attendanceRegistry.calculateAttendanceHistory();
        int attendance = attendanceRegistry.getAttendance();
        int late = attendanceRegistry.getLate();
        int absence = attendanceRegistry.getAbsence();

        //then
        Assertions.assertThat(attendance).isEqualTo(expectedAttendance);
        Assertions.assertThat(late).isEqualTo(expectedLate);
        Assertions.assertThat(absence).isEqualTo(expectedAbsence);
    }
}