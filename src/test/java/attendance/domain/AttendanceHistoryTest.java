package attendance.domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {

    @Test
    void 출석_통계_계산() {
        // given
        String crewName = "제프리";
        LocalDate now = LocalDate.of(2025, 2, 5);

        DateInfos dateInfos = DateInfos.initInfos();
        DateInfo dateInfo1 = getDateInfo(2025, 2, 3, "13:04");
        DateInfo dateInfo2 = getDateInfo(2025, 2, 4, "10:06");
        DateInfo dateInfo3 = getDateInfo(2025, 2, 5, "10:31");

        dateInfos.addDateInfo(dateInfo1);
        dateInfos.addDateInfo(dateInfo2);
        dateInfos.addDateInfo(dateInfo3);

        // when
        AttendanceHistory history = AttendanceHistory.fromDateInfos(crewName, now, dateInfos);

        // then
        Assertions.assertThat(history.getCrewName()).isEqualTo(crewName);
        Assertions.assertThat(history.getAbsenceCount()).isEqualTo(1);
        Assertions.assertThat(history.getLateCount()).isEqualTo(1);
        Assertions.assertThat(history.getAttendanceCount()).isEqualTo(1);
    }

    private static DateInfo getDateInfo(int year, int month, int day, String hourMinute) {
        LocalDate date1 = LocalDate.of(year, month, day);
        CampusTime campusTime1 = CampusTime.fromHourColonMinute(hourMinute);
        return DateInfo.fromCampusTime(date1, campusTime1);
    }

}
