package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Attendance;
import attendance.model.AttendanceDetail;
import attendance.model.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void 크루의_이름이_5자_이하가_아니라면_예외가_발생한다() {
        assertThatThrownBy(() -> new Crew("멍멍멍멍멍"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루의_이름이_공백인_경우_예외가_발생한다() {
        assertThatThrownBy(() -> new Crew(" "))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 크루가_가지고_있는_출석_기록을_확인한다() {
        Crew crew = new Crew("멍구");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 0)));
        assertThat(crew.getAttendanceHistory().getAttendanceCount()).isEqualTo(1);
    }

    @Test
    void 크루가_가지고_있는_기록을_수정한_후_반영되었는지_확인한다() {
        Crew crew = new Crew("멍구");
        crew.attend(new AttendanceDetail(LocalDateTime.of(2024, 12, 10, 10, 0)));
        crew.getAttendanceHistory()
                .findAttendanceDetail(LocalDate.of(2024, 12, 10))
                .modify(LocalTime.of(10, 6));
        AttendanceDetail attendanceDetail = crew.getAttendanceHistory()
                .findAttendanceDetail(LocalDate.of(2024, 12, 10));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(attendanceDetail.getAttendanceDateTime().toLocalTime()).isEqualTo(LocalTime.of(10, 6));
        softly.assertThat(attendanceDetail.getAttendance()).isEqualTo(Attendance.지각);
        softly.assertAll();
    }

}
