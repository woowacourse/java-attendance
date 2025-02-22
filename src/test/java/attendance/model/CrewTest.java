package attendance.model;

import static attendance.error.ErrorMessage.ERROR_NAME_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.TestUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void 크루의_이름이_4자_이하가_아니라면_예외가_발생한다() {
        assertThatThrownBy(() -> new Crew("멍멍멍멍멍"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NAME_LENGTH);
    }

    @Test
    void 크루의_이름이_공백인_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Crew(" "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_NAME_LENGTH);
    }

    @Test
    void 크루가_가지고_있는_출석_기록을_확인한다() {
        //given
        Crew crew = new Crew("멍구");
        crew.attend(TestUtil.creatAttendanceDetail(2024, 12, 10, 10, 0));

        //when
        long attendanceCount = crew.getAttendanceHistory().getAttendanceCount();

        //then
        assertThat(attendanceCount).isEqualTo(1);
    }

    @Test
    void 크루가_가지고_있는_기록을_수정한_후_반영되었는지_확인한다() {
        //given
        Crew crew = new Crew("멍구");
        LocalDate targetDate = LocalDate.of(2024, 12, 10);
        crew.attend(
                TestUtil.creatAttendanceDetail(2024, 12, 10, 10, 0)
        );

        //when
        crew.getAttendanceHistory()
                .findAttendanceDetail(targetDate)
                .modify(LocalTime.of(10, 6));

        //then
        AttendanceDetail attendanceDetail = crew.getAttendanceHistory()
                .findAttendanceDetail(targetDate);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendanceDetail.getAttendanceDateTime().toLocalTime()).isEqualTo(LocalTime.of(10, 6));
        softly.assertThat(attendanceDetail.getAttendance()).isEqualTo(Attendance.LATE);
        softly.assertAll();
    }

}
