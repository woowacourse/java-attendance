package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
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
        AttendanceChecker attendanceChecker = dateInfos.findByDay(now.atTime(9,59));

        //then
        Assertions.assertThat(attendanceChecker.getLocalDateTime().getMonthValue()).isEqualTo(2);
        Assertions.assertThat(attendanceChecker.getLocalDateTime().getDayOfMonth()).isEqualTo(19);
    }


    @Test
    void 출결상태_통계_계산() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 19, 10, 31);
        LocalDateTime localDateTime2 = LocalDateTime.of(2025, 2, 17, 13, 31);
        LocalDateTime localDateTime3= LocalDateTime.of(2025, 2, 20, 9, 59);
        AttendanceChecker attendanceChecker = new AttendanceChecker(localDateTime);
        AttendanceChecker attendanceChecker2 = new AttendanceChecker(localDateTime2);
        AttendanceChecker attendanceChecker3 = new AttendanceChecker(localDateTime3);

        AttendanceRegistry attendanceRegistry = AttendanceRegistry.from(List.of(attendanceChecker, attendanceChecker2, attendanceChecker3));

        //when
        attendanceRegistry.calculateAttendanceHistory();
        List<Integer> attendanceTraces = attendanceRegistry.getAttendanceTraces();
        int attendance = attendanceTraces.getLast();
        int late = attendanceTraces.get(1);
        int absence = attendanceTraces.getFirst();

        //then
        Assertions.assertThat(attendance).isEqualTo(1);
        Assertions.assertThat(late).isEqualTo(0);
        Assertions.assertThat(absence).isEqualTo(2);
    }
}