package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;

class AttendanceInfosTest {

    @Test
    void 기본_출석_정보들을_생성한다() {
        // when
        AttendanceInfos attendanceInfos = AttendanceInfos.initInfos();

        // then
        assertThat(attendanceInfos.getAttendanceInfos()).hasSize(0);
    }

    @Test
    void 출석_정보들을_인자로_받아_객체를_생성한다() {
        // given
        AttendanceInfo attendanceInfo1 = createAttendanceInfo("10:31", 2025, 2, 27);
        AttendanceInfo attendanceInfo2 = createAttendanceInfo("10:31", 2025, 2, 28);
        AttendanceInfo attendanceInfo3 = createAttendanceInfo("10:31", 2025, 3, 3);

        // when
        AttendanceInfos attendanceInfos = AttendanceInfos.from(
                List.of(attendanceInfo1, attendanceInfo2, attendanceInfo3));
        // then
        assertThat(attendanceInfos.getAttendanceInfos()).hasSize(3);
        assertThat(attendanceInfos.getAttendanceInfos().getFirst().getHour()).isEqualTo(10);
        assertThat(attendanceInfos.getAttendanceInfos().getFirst().getMinute()).isEqualTo(31);
        assertThat(attendanceInfos.getAttendanceInfos().getLast().getMonth()).isEqualTo(3);
        assertThat(attendanceInfos.getAttendanceInfos().getLast().getDay()).isEqualTo(3);
    }

    @Test
    void 출석_정보를_더한다() {
        // given
        AttendanceInfos attendanceInfos = AttendanceInfos.initInfos();
        AttendanceInfo attendanceInfo1 = createAttendanceInfo("10:31", 2025, 2, 27);
        AttendanceInfo attendanceInfo2 = createAttendanceInfo("10:31", 2025, 2, 28);

        // when
        attendanceInfos.addInfo(attendanceInfo1);
        attendanceInfos.addInfo(attendanceInfo2);

        // then
        assertThat(attendanceInfos.getAttendanceInfos()).hasSize(2);
    }

    @Test
    void 캠퍼스_날짜와_시간_정보를_받아_출석_정보를_더한다() {
        // given
        AttendanceInfos attendanceInfos = AttendanceInfos.initInfos();
        CampusTime campusTime = CampusTime.from("10:31");
        LocalDate date = LocalDate.of(2025, 2, 27);
        CampusDate campusDate1 = CampusDate.ofDateAndDay(date, 27);
        CampusDate campusDate2 = CampusDate.ofDateAndDay(date, 28);

        // when
        attendanceInfos.addInfoByDateAndTime(campusDate1, campusTime);
        attendanceInfos.addInfoByDateAndTime(campusDate2, campusTime);

        // then
        assertThat(attendanceInfos.getAttendanceInfos()).hasSize(2);
    }

    @Test
    void 날짜가_주어지면_해당되는_출석_정보를_찾아낸다() {
        // given
        AttendanceInfo attendanceInfo1 = createAttendanceInfo("10:31", 2025, 2, 27);
        AttendanceInfo attendanceInfo2 = createAttendanceInfo("10:31", 2025, 2, 28);
        AttendanceInfos attendanceInfos = AttendanceInfos.from(List.of(attendanceInfo1, attendanceInfo2));

        // when
        AttendanceInfo infoByDay = attendanceInfos.findInfoByDate(
                CampusDate.ofDateAndDay(LocalDate.of(2025, 2, 3), 27));

        // then
        assertThat(infoByDay.getMonth()).isEqualTo(2);
        assertThat(infoByDay.getDay()).isEqualTo(27);
    }

    @Test
    void 날짜가_주어지면_해당되는_출석_정보의_존재_여부를_판별한다() {
        // given
        AttendanceInfo attendanceInfo1 = createAttendanceInfo("10:31", 2025, 2, 27);
        AttendanceInfo attendanceInfo2 = createAttendanceInfo("10:31", 2025, 2, 28);
        AttendanceInfos attendanceInfos = AttendanceInfos.from(List.of(attendanceInfo1, attendanceInfo2));

        // when
        boolean result1 = attendanceInfos.hasInfoByDate(
                CampusDate.ofDateAndDay(LocalDate.of(2025, 2, 3), 27));
        boolean result2 = attendanceInfos.hasInfoByDate(
                CampusDate.ofDateAndDay(LocalDate.of(2025, 2, 3), 25));

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void 날짜와_시간으로_출석_정보를_수정하여_출석정보들을_반환한다() {
        // given
        AttendanceInfo attendanceInfo1 = createAttendanceInfo("10:31", 2025, 2, 27);
        AttendanceInfo attendanceInfo2 = createAttendanceInfo("10:31", 2025, 2, 28);
        AttendanceInfos attendanceInfos = AttendanceInfos.from(List.of(attendanceInfo1, attendanceInfo2));

        CampusDate modifyDate = CampusDate.ofDateAndDay(LocalDate.of(2025, 2, 3), 27);
        CampusDate unModifyDate = CampusDate.ofDateAndDay(LocalDate.of(2025, 2, 3), 28);

        // when
        AttendanceInfos modifiedInfos = attendanceInfos.modifyInfoByDateAndTime(modifyDate, CampusTime.from("10:29"));

        // then
        assertThat(modifiedInfos.getAttendanceInfos()).hasSize(2);
        assertThat(modifiedInfos.findInfoByDate(modifyDate).getHour()).isEqualTo(10);
        assertThat(modifiedInfos.findInfoByDate(modifyDate).getMinute()).isEqualTo(29);
        assertThat(modifiedInfos.findInfoByDate(unModifyDate).getHour()).isEqualTo(10);
        assertThat(modifiedInfos.findInfoByDate(unModifyDate).getMinute()).isEqualTo(31);
    }

    private static AttendanceInfo createAttendanceInfo(String inputTime, int year, int month, int day) {
        CampusTime time = CampusTime.from(inputTime);
        CampusDate date = CampusDate.fromDate(LocalDate.of(year, month, day));
        return AttendanceInfo.fromDateAndTime(date, time);
    }

}
