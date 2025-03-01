import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("크루의 출석 정보를 저장할 수 있다.")
    @Test
    void add() {
        //given
        Attendance attendance = createAttendance();
        LocalDateTime addTime = LocalDateTime.of(2024, 12, 12, 10, 0);

        //when
        attendance.add(addTime);

        //then
        assertThat(attendance.getAttendanceTime()).hasSize(3);
    }

    @DisplayName("특정 날짜의 출석 정보가 없다면 예외가 발생한다.")
    @Test
    void notFoundAttendance() {
        //given
        Attendance attendance = createAttendance();
        LocalDate date = LocalDate.of(2024, 12, 4);

        //when //then
        assertThatThrownBy(() -> attendance.getAttendanceBy(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날자(일)에 해당하는 출석 기록이 없습니다.");
    }

    @DisplayName("특정 날짜의 출석 정보를 반환한다.")
    @Test
    void getAttendanceBy() {
        //given
        Attendance attendance = createAttendance();
        LocalDate time = LocalDate.of(2024, 12, 2);

        //when
        LocalDateTime actual = attendance.getAttendanceBy(time);

        //then
        assertThat(actual).isEqualTo(LocalDateTime.of(2024, 12, 2, 11, 11));
    }

    @DisplayName("특정 날짜의 출석 시간을 수정한다.")
    @Test
    void update() {
        //given
        Attendance attendance = createAttendance();
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 10, 0);

        //when
        attendance.update(time);

        //then
        LocalDateTime attendanceBy = attendance.getAttendanceBy(time.toLocalDate());
        assertThat(attendanceBy).isEqualTo(LocalDateTime.of(2024, 12, 2, 10, 0));
    }

    private Attendance createAttendance() {
        Crew crew = Crew.of("도기");

        List<LocalDateTime> attendanceTime = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 11, 11);

        attendanceTime.add(localDateTime);
        attendanceTime.add(localDateTime1);

        return new Attendance(crew, attendanceTime);
    }

}
