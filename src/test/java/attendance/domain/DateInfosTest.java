package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.domain.constant.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DateInfosTest {


    @Test
    void 당일까지의_통계_초기화() {
        //given
        LocalDate now = LocalDate.of(2025, 2, 19);
        DateInfos dateInfos = DateInfos.fromDefaultValue(now);

        //when //then
        Assertions.assertThat(dateInfos.getDateInfos()).hasSize(13);
    }

    @Test
    void 결석_숫자_개수_새기() {
        //given
        LocalDate now = LocalDate.of(2025, 2, 19);
        DateInfos dateInfos = DateInfos.fromDefaultValue(now);

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
        DateInfos dateInfos = DateInfos.fromDefaultValue(now);

        //when
        DateInfo dateInfo = dateInfos.findByDate(19);

        //then
        Assertions.assertThat(dateInfo.getMonth()).isEqualTo("02");
        Assertions.assertThat(dateInfo.getDay()).isEqualTo("19");
    }

    @Test
    void 출결상태_통계_계산() {
        //given

        Time lateTime = Time.from("10:10");
        Time normalTime = Time.from("09:00");
        Time absentTime = Time.from("15:30");

        DayOfWeek tuesday = DayOfWeek.from(2);
        DayOfWeek wednesday = DayOfWeek.from(3);

        DateInfo dateInfo1 = DateInfo.of(2, 18, tuesday, normalTime);
        DateInfo dateInfo2 = DateInfo.of(2, 18, tuesday, lateTime);
        DateInfo dateInfo3 = DateInfo.of(2, 19, wednesday, normalTime);
        DateInfo dateInfo4 = DateInfo.of(2, 19, wednesday, absentTime);

        DateInfos dateInfos = DateInfos.from(List.of(dateInfo1, dateInfo2, dateInfo3, dateInfo4));

        //when
        dateInfos.calculateAttendanceHistory();
        int attendance = dateInfos.getAttendance();
        int late = dateInfos.getLate();
        int absence = dateInfos.getAbsence();

        //then
        Assertions.assertThat(attendance).isEqualTo(2);
        Assertions.assertThat(late).isEqualTo(1);
        Assertions.assertThat(absence).isEqualTo(1);
    }
}