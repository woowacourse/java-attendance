package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

public class AttendanceDetailTest {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE은 등교일이 아닙니다.");

    @Test
    void 등교일이_아닐때_출석상세_생성시_예외가_발생한다() {
        //given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 1, 10, 0);

        //when & then
        assertThatThrownBy(() -> new AttendanceDetail(attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(attendanceDateTime.format(formatter));
    }

    @Test
    void 같은_출석상태일때_true를_반환한다() {
        //given
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));

        //when
        boolean sameAs = attendanceDetail.isSameAs(Attendance.PRESENT);

        //then
        assertThat(sameAs).isTrue();
    }

    @Test
    void 다른_출석상태일때_false를_반환한다() {
        //given
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 2));

        //when
        boolean sameAs = attendanceDetail.isSameAs(Attendance.LATE);

        //then
        assertThat(sameAs).isFalse();
    }

    @Test
    void 등교시간_수정후_결과가_반영된다() {
        //given
        LocalDate targetDate = LocalDate.of(2024, 12, 10);
        AttendanceDetail attendanceDetail = new AttendanceDetail(LocalDateTime.of(targetDate, LocalTime.of(10, 2)));

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
