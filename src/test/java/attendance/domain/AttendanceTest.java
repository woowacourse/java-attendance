package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    void 주말에_출석을_생성할_경우_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime saturday = LocalDateTime.of(2024, 12, 14, 10, 1);

        //when
        assertThatThrownBy(() -> new Attendance(nickname, saturday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    void 공휴일에_출석_객체를_생성하는_경우_예외가_발생한다() {
        //given
        String nickname = "pobi";
        LocalDateTime christmas = LocalDateTime.of(2024, 12, 25, 10, 1);

        //when then
        assertThatThrownBy(() -> new Attendance(nickname, christmas))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 출석의_상태를_확인할_수_있다() {
        //given
        String nickname = "pobi";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 6);
        Attendance attendance = new Attendance(nickname, attendanceDateTime);

        //when
        AttendanceStatus result = attendance.getAttendanceStatus();

        //then
        assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 시간을_수정할_수_있다() {
        //given
        Attendance attendance = new Attendance("포비", LocalDateTime.of(2024, 12, 13, 10, 6));

        //when
        attendance.updateAttendanceTime(LocalTime.of(10, 1));

        //then
        assertThat(attendance).isEqualTo(new Attendance("포비", LocalDateTime.of(2024, 12, 13, 10, 1)));
    }

    @Test
    void 출석_객체를_통해_이미_출석을_했는지_알_수_있다1() {
        //given
        Attendance attendance1 = new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 11, 1));
        Attendance attendance2 = new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 10, 1));

        //when
        boolean result = attendance1.isAlreadyAttend(attendance2);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 닉네임과_날짜만으로_이미_출석을_했는지_알_수_있다2() {
        //given
        Attendance attendance = new Attendance("pobi", LocalDateTime.of(2024, 12, 2, 11, 1));

        //when
        boolean result = attendance.isAlreadyAttend("pobi", new AttendanceDate(LocalDate.of(2024, 12, 2)));

        //then
        assertThat(result).isTrue();
    }
}
