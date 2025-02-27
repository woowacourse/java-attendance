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

    private static AttendanceInfo createAttendanceInfo(String inputTime, int year, int month, int day) {
        CampusTime time = CampusTime.from(inputTime);
        CampusDate date = CampusDate.fromNow(LocalDate.of(year, month, day));
        return AttendanceInfo.fromDateAndTime(date, time);
    }

}
