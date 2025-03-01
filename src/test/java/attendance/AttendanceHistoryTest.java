package attendance;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    String nicknameJenson = "젠슨";
    AttendanceHistory attendanceHistory;
    AttendanceTimes attendanceTimes;
    AttendanceTime attendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));

    @BeforeEach
    void init() {
        attendanceTimes = AttendanceTimes.create();
        attendanceHistory = AttendanceHistory.create(List.of());
        attendanceHistory.add(nicknameJenson, attendanceTime1);
    }

    @DisplayName("닉네임과 출석 기록이 주어졌을 때, 저장할 수 있어야 한다.")
    @Test
    void given_nickname_and_attendance_then_save() {
        //given
        String nickname = "포비";
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
        AttendanceTime anotherAttendanceTime = AttendanceTime.from(
            LocalDateTime.of(2024, 12, 4, 10, 0));

        // when
        attendanceHistory.add(nicknameJenson, anotherAttendanceTime);

        //then
        Map<String, AttendanceTimes> attendanceHistory1 = attendanceHistory.getAttendanceHistory();
        assertThat(attendanceHistory1.get(nicknameJenson).getAttendanceTimes().size()).isEqualTo(2);
        assertThat(
            attendanceHistory1.get(nicknameJenson).getAttendanceTimes()).containsExactlyInAnyOrder(
            attendanceTime1, anotherAttendanceTime);
    }

    @DisplayName("이미 해당 날짜에 출석 기록이 존재한다면, 예외를 던진다")
    @Test
    void already_attendance_history_in_date_then_throw_exception() {
        //given
        AttendanceTime duplicateAttendanceTime = AttendanceTime.from(
            LocalDateTime.of(2024, 12, 3, 11, 0));

        //when, then
        assertThatThrownBy(() -> attendanceHistory.add(nicknameJenson, duplicateAttendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 날짜에 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }

    @DisplayName("날짜를 입력받고, 해당 날짜의 크루에 대한 출석 기록이 존재하지 않는다면, 예외를 발생시켜야 한다.")
    @Test
    void given_date_and_find_attendance_but_not_exist_then_throw_exception() {
        int findDate = 4;
        assertThatThrownBy(
            () -> attendanceHistory.getAttendanceTimeByDate(nicknameJenson, findDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("해당 날짜에는 출석 기록이 없습니다.");
    }

    @DisplayName("크루의 닉네임과 날짜(date)를 입력받았을 때, 해당하는 출석 기록을 가져온다")
    @Test
    void given_nickname_and_date_then_return_attendance_time() {
        int findDate = 3;
        LocalDate expectedAttendanceDate = LocalDate.of(2024, 12, 3);
        AttendanceTime foundAttendanceTime = attendanceHistory.getAttendanceTimeByDate(
            nicknameJenson, findDate);
        assertThat(foundAttendanceTime.getDate()).isEqualTo(expectedAttendanceDate);
    }

    @DisplayName("기존 시간과 수정 시간이 주어졌을 때, 기존 출석 기록을 제거 후, 새로운 출석 기록을 삽입한다")
    @Test
    void remove_before_attendance_and_insert_new_attendance() {
        int findDate = 3;
        LocalDateTime modifyTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        AttendanceTime modifyAttendanceTime = attendanceHistory.modifyAttendance(nicknameJenson,
            attendanceTime1, modifyTime);
        AttendanceTime attendanceTimeByDate = attendanceHistory.getAttendanceTimeByDate(
            nicknameJenson, findDate);
        assertThat(modifyAttendanceTime.getAttendanceDateTime().toLocalTime()).isEqualTo(
            LocalTime.of(10, 0));
        assertThat(attendanceTimeByDate.getAttendanceDateTime()).isEqualTo(modifyTime);
    }
}
