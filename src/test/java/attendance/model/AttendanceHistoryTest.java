package attendance.model;

import static attendance.error.ErrorMessage.ERROR_ATTENDANCE_DETAIL_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    void 출석상세가_출석기록에_정상적으로_추가된다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        //when
        attendanceHistory.addAttendanceDetail(
                TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0)
        );

        //then
        assertThat(attendanceHistory.stream()).hasSize(1);
    }

    @Test
    void 출석_수정_후_기록에_결과가_반영된다() {
        // given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceDetail originalAttendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0);
        attendanceHistory.addAttendanceDetail(originalAttendanceDetail);

        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 9, 58));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 2));

        LocalDate modifyDate = LocalDate.of(2024, 12, 2);

        // when
        AttendanceDetail targetAttendanceDetail = attendanceHistory.findAttendanceDetail(modifyDate);
        targetAttendanceDetail.modify(LocalTime.of(13, 10));

        // then
        assertThat(targetAttendanceDetail.getAttendanceDateTime().toLocalTime()).isEqualTo(LocalTime.of(13, 10));
        assertThat(targetAttendanceDetail.getAttendance()).isEqualTo(Attendance.LATE);
        assertThat(targetAttendanceDetail).isEqualTo(originalAttendanceDetail);
    }

    @Test
    void 출석기록에서_전체출석횟수를_계산한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        //when
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 3, 9, 58));
        attendanceHistory.addAttendanceDetail(TestUtil.creatAttendanceDetail(2024, 12, 4, 10, 2));

        //then
        assertThat(attendanceHistory.getAttendanceCount()).isEqualTo(3);
    }

    @Test
    void 해당_날짜의_출석기록이_존재하면_true를_반환한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate targetDate = LocalDate.of(2024, 12, 2);

        attendanceHistory.addAttendanceDetail(
                TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0)
        );

        //when
        boolean containsDate = attendanceHistory.containsDate(targetDate);
        assertThat(containsDate).isTrue();
    }

    @Test
    void 해당_날짜의_출석기록이_존재하지않으면_false를_반환한다() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate targetDate = LocalDate.of(2024, 12, 2);

        attendanceHistory.addAttendanceDetail(
                TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0)
        );

        //when
        LocalDate otherDate = LocalDate.of(2024, 12, 3);
        boolean containsDate = attendanceHistory.containsDate(otherDate);
        assertThat(containsDate).isFalse();
    }

    @Test
    void 특정_날짜에_해당하는_출석_상세를_찾을_수_있다() {
        // given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceDetail attendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0);
        attendanceHistory.addAttendanceDetail(attendanceDetail);

        LocalDate wantDate = LocalDate.of(2024, 12, 2);

        // when
        AttendanceDetail find = attendanceHistory.findAttendanceDetail(wantDate);

        // then
        assertThat(find).isEqualTo(attendanceDetail);
    }

    @Test
    void 출석기록에_없는_날짜로_출석상세를_찾으려고할때_예외가_발생한다() {
        // given
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        AttendanceDetail attendanceDetail = TestUtil.creatAttendanceDetail(2024, 12, 2, 13, 0);
        attendanceHistory.addAttendanceDetail(attendanceDetail);

        LocalDate wantDate = LocalDate.of(2024, 12, 1);

        // when & then
        assertThatThrownBy(() -> attendanceHistory.findAttendanceDetail(wantDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_ATTENDANCE_DETAIL_NOT_FOUND);
    }

}
