package attendance.domain;

import attendance.domain.constant.AttendanceStatus;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DateInfosTest {

    @Test
    void 초기_객체_생성() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();

        // when // then
        Assertions.assertThat(dateInfos).isNotNull();
    }

    @Test
    void 출석_정보_추가() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);

        // when
        dateInfos.addDateInfo(dateInfo);

        // then
        Assertions.assertThat(dateInfos.getDateInfos()).hasSize(1);
    }

    @Test
    void 날짜로_출석_정보_찾기() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when
        DateInfo dateInfoByDay = dateInfos.findDateInfoByDay(25);

        // then
        Assertions.assertThat(dateInfoByDay.getDay()).isEqualTo(25);
    }

    @Test
    void 날짜로_출석_정보를_찾을_수_없는경우_예외() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when // then
        Assertions.assertThatThrownBy(() -> dateInfos.findDateInfoByDay(24))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.NICKNAME_NOT_PRESENCE.getMessage());
    }

    @Test
    void 일자로_출석정보_찾기() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when
        DateInfo findDateInfo = dateInfos.findOrCreateDateInfoByDate(now, campusTime);

        // then
        Assertions.assertThat(findDateInfo.getDay()).isEqualTo(25);
    }

    @Test
    void 일자로_출석정보를_찾을_수_없는경우_새로생성() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();

        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");

        // when
        DateInfo findDateInfo = dateInfos.findOrCreateDateInfoByDate(now, campusTime);

        // then
        Assertions.assertThat(findDateInfo.getDay()).isEqualTo(25);
    }

    @Test
    void 출석_정보를_가지고있으면_true_반환() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when // then
        Assertions.assertThat(dateInfos.hasDateInfo(now.getDayOfMonth())).isTrue();
    }

    @Test
    void 출석_정보를_가지고있지_않으면_false_반환() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:31");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when // then
        Assertions.assertThat(dateInfos.hasDateInfo(26)).isFalse();
    }

    @Test
    void 일자로_출석_상태를_반환() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:06");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when
        AttendanceStatus attendanceStatusByDay = dateInfos.findAttendanceStatusByDay(25);

        // then
        Assertions.assertThat(attendanceStatusByDay).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 일자로_출석_상태를_찾지_못하면_결석_반환() {
        // given
        DateInfos dateInfos = DateInfos.initInfos();
        LocalDate now = LocalDate.of(2025, 2, 25);
        CampusTime campusTime = CampusTime.fromHourColonMinute("10:06");
        DateInfo dateInfo = DateInfo.fromCampusTime(now, campusTime);
        dateInfos.addDateInfo(dateInfo);

        // when
        AttendanceStatus attendanceStatusByDay = dateInfos.findAttendanceStatusByDay(26);

        // then
        Assertions.assertThat(attendanceStatusByDay).isEqualTo(AttendanceStatus.ABSENCE);
    }

}
