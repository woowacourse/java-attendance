package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRepositoryTest {

    @DisplayName("출결 기록을 추가한다.")
    @Test
    void 출결_기록을_추가한다() {

        // given
        Attendance attendance = new Attendance("피글렛", LocalDateTime.now());
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());

        // when
        attendanceRepository.add(attendance);

        // then
        assertThat(attendanceRepository.findAttendanceByName().size()).isEqualTo(1);
    }

    @DisplayName("해당 날짜에 출결 기록이 있는 크루를 추가할 시 예외가 발생한다.")
    @Test
    void 해당_날짜에_출결_기록이_있는_크루를_추가할_시_예외가_발생한다() {

        // given
        Attendance attendance = new Attendance("체체", LocalDateTime.now());
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());
        attendanceRepository.add(attendance);

        // when & then
        assertThatThrownBy(() -> attendanceRepository.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
    }

    @DisplayName("출석 기록을 수정한다.")
    @Test
    void 출석_기록을_수정한다() {

        // given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 19, 10, 0, 0);
        Attendance attendance = new Attendance("체체", localDateTime);
        AttendanceRepository attendanceRepository = new AttendanceRepository(new ArrayList<>());
        attendanceRepository.add(attendance);

        //when
        Attendance resultAttendance = attendanceRepository.findAttendanceByNameAndDateTime("체체", 19);

        //then
        assertThat(attendance).isEqualTo(resultAttendance);


    }

}