package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryManagerTest {
    @DisplayName("동일_날짜에_중복_출석을_하면_예외를_던진다")
    @Test
    void should_ThrowException_WhenAttendanceDuplicated() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(10, 00);
        attendanceHistoryManager.doAttendance(attendanceDate, attendanceTime);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistoryManager.doAttendance(attendanceDate, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 이미 출석하셨습니다.");
    }

//    @Test
//    void modify_attendance_result() {
//        //given
//        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
//        LocalDate localDate = LocalDate.of(2024, 12, 26);
//        LocalTime localTime = LocalTime.of(11, 00);
//        AttendanceHistory attendanceHistory = new AttendanceHistory(LocalDateTime.of(localDate, localTime), ABSENCE);
//        attendanceHistoryManager.doAttendance(attendanceHistory);
//
//        LocalTime modifyTime = LocalTime.of(10, 00);
//
//        //when
//        AttendanceHistory result = attendanceHistoryManager.modifyAttendanceResult(attendanceHistory, modifyTime);
//
//        //then
//        assertThat(attendanceHistory).isEqualTo(result);
//        assertThat(result.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(localDate, modifyTime));
//        assertThat(attendanceHistory.getAttendanceType()).isEqualTo(ATTENDANCE);
//    }

    @DisplayName("주어진_날짜에_출석_기록이_존재하지_않으면_예외를_발생한다")
    @Test
    void should_ThrowException_WhenAttendanceNotExistByDate() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        LocalDate localDate = LocalDate.of(2024, 12, 26);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistoryManager.getAttendanceHistoryByDate(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재하지 않습니다.");
    }

    @DisplayName("주어진_날짜와_시간에_출석을_할_수_있다")
    @Test
    void doAttendance() {
        //given
        AttendanceHistoryManager attendanceHistoryManager = new AttendanceHistoryManager();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 26);
        LocalTime attendanceTime = LocalTime.of(11, 00);

        //when
        AttendanceHistory result = attendanceHistoryManager.doAttendance(attendanceDate, attendanceTime);

        //then
        assertThat(result).isEqualTo(attendanceHistoryManager.getAttendanceHistoryByDate(attendanceDate));
        assertThat(result.getAttendanceDateTime()).isEqualTo(LocalDateTime.of(attendanceDate, attendanceTime));
        assertThat(result.isAbsence()).isTrue();
    }
}
