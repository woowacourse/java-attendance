package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceDetailTest {

    @Test
    void 같은_출석상태일때_true를_반환한다() {
        //given
        AttendanceDetail attendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 10, 10, 2);

        //when
        boolean sameAs = attendanceDetail.isSameAs(Attendance.PRESENT);

        //then
        assertThat(sameAs).isTrue();
    }

    @Test
    void 다른_출석상태일때_false를_반환한다() {
        //given
        AttendanceDetail attendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 10, 10, 2);

        //when
        boolean sameAs = attendanceDetail.isSameAs(Attendance.LATE);

        //then
        assertThat(sameAs).isFalse();
    }

    @Test
    void 등교시간_수정후_결과가_반영된다() {
        //given
        LocalDate targetDate = LocalDate.of(2024, 12, 10);
        AttendanceDetail attendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 10, 10, 2);

        //when
        LocalTime modifyTime = LocalTime.of(9, 58);
        attendanceDetail.modify(modifyTime);

        //then
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendanceDetail.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(targetDate, modifyTime));
        softly.assertThat(attendanceDetail.getAttendance()).isEqualTo(Attendance.PRESENT);
        softly.assertAll();
    }

}
