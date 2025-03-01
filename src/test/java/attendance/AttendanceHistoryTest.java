package attendance;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    AttendanceHistory attendanceHistory;
    AttendanceTimes attendanceTimes;

    @BeforeEach
    void init() {
        attendanceTimes = AttendanceTimes.create();
        attendanceHistory = AttendanceHistory.create();
    }

    @DisplayName("닉네임과 출석 기록이 주어졌을 때, 저장할 수 있어야 한다.")
    @Test
    void given_nickname_and_attendance_then_save() {
        //given
        String nickname = "젠슨";
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));

        //when
        attendanceHistory.add(nickname, attendanceTime);

        //then
        Map<String, AttendanceTimes> attendanceHistory1 = attendanceHistory.getAttendanceHistory();
        assertThat(attendanceHistory1.get(nickname).getAttendanceTimes()).contains(attendanceTime);
    }

    @DisplayName("해당 크루의 닉네임이 이미 존재한다면, 기존 출석에 기록을 추가한다")
    @Test
    void nickname_already_exist_then_add_exising_attendance() {
        //given
        String nickname = "젠슨";
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));
        attendanceHistory.add(nickname, attendanceTime);
        AttendanceTime anotherAttendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 4, 10, 0));

        // when
        attendanceHistory.add(nickname, anotherAttendanceTime);

        //then
        Map<String, AttendanceTimes> attendanceHistory1 = attendanceHistory.getAttendanceHistory();
        assertThat(attendanceHistory1.get(nickname).getAttendanceTimes().size()).isEqualTo(2);
        assertThat(attendanceHistory1.get(nickname).getAttendanceTimes()).containsExactlyInAnyOrder(attendanceTime, anotherAttendanceTime);
    }

    @DisplayName("이미 해당 날짜에 출석 기록이 존재한다면, 예외를 던진다")
    @Test
    void already_attendance_history_in_date_then_throw_exception() {
        //given
        String nickname = "젠슨";
        AttendanceTime attendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));
        attendanceHistory.add(nickname, attendanceTime);
        AttendanceTime duplicateAttendanceTime = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 11, 0));

        //when, then
        assertThatThrownBy(() -> attendanceHistory.add(nickname, duplicateAttendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 날짜에 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }
}
