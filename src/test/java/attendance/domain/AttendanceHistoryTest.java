package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static attendance.exception.ErrorMessage.DUPLICATED_ATTENDANCE;
import static attendance.exception.ErrorMessage.NO_ATTENDANCE_TO_MODIFY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {
    @DisplayName("주어진_날짜의_출석을_찾아_반환할_수_있다")
    @Test
    void findAttendance() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 26);
        LocalTime time = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(date, time, LATE);
        attendanceHistory.addAttendance(attendance);

        //when
        Optional<Attendance> result = attendanceHistory.findAttendance(date);

        //then
        assertThat(result.get()).isEqualTo(attendance);
    }

    @DisplayName("특정_날짜에_이미_출석이_있으면_예외를_던진다")
    @Test
    void should_ThrowException_WhenAddDuplicatedAttendance() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 26);
        LocalTime time = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(date, time, LATE);
        attendanceHistory.addAttendance(attendance);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistory.addAttendance(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DUPLICATED_ATTENDANCE.getMessage());
    }

    @DisplayName("특정_날짜에_이미_출석이_있으면_예외를_던진다")
    @Test
    void should_ThrowException_WhenAttendanceIsExists() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 26);
        LocalTime time = LocalTime.of(10, 0);
        Attendance attendance = new Attendance(date, time, LATE);
        attendanceHistory.addAttendance(attendance);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistory.validateDuplicatedAttendance(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(DUPLICATED_ATTENDANCE.getMessage());
    }

    @DisplayName("주어진_날짜의_출석이_없으면_Empty_를_반환할_수_있다")
    @Test
    void should_ReturnEmpty_WhenSameDateNotExists() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 26);

        //when
        Optional<Attendance> result = attendanceHistory.findAttendance(date);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("출석을_수정하고_수정된_출석을_반환할_수_있다")
    @Test
    void modifyAttendance() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        LocalDate date = LocalDate.of(2024, 12, 26);
        LocalTime time = LocalTime.of(10, 7);
        attendanceHistory.addAttendance(new Attendance(date, time, LATE));

        LocalTime modificationTime = LocalTime.of(10, 0);
        AttendanceStatus modificationStatus = ATTENDANCE;
        Attendance modifiedAttendance = new Attendance(date, modificationTime, modificationStatus);

        //when
        Attendance result = attendanceHistory.modifyAttendance(modifiedAttendance);

        //then
        assertAll(
                () -> assertThat(result.isDateEquals(date)).isTrue(),
                () -> assertThat(result.getTime()).isEqualTo(modificationTime),
                () -> assertThat(result.getStatus()).isEqualTo(modificationStatus),
                () -> {
                    Optional<Attendance> foundedAttendance = attendanceHistory.findAttendance(date);
                    assertThat(result).isEqualTo(foundedAttendance.get());
                }
        );
    }

    @DisplayName("출석을_수정할_날짜에_출석_기록이_없으면_예외를_던진다")
    @Test
    void should_ThrowException_WhenNoAttendanceToModify() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        Attendance modifiedAttendance = new Attendance(LocalDate.of(2024, 12, 26), LocalTime.of(10, 0), ATTENDANCE);

        //when
        //then
        assertThatThrownBy(() -> attendanceHistory.modifyAttendance(modifiedAttendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_ATTENDANCE_TO_MODIFY.getMessage());
    }

    @DisplayName("오늘_이전까지의_출석_통계를_반환할_수_있다")
    @Test
    void getAttendanceStatistics() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.addAttendance(new Attendance(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(13, 7),
                LATE)
        );
        attendanceHistory.addAttendance(new Attendance(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 0),
                ATTENDANCE)
        );
        attendanceHistory.addAttendance(new Attendance(
                LocalDate.of(2024, 12, 5),
                LocalTime.of(10, 32),
                ABSENCE)
        );
        LocalDate today = LocalDate.of(2024, 12, 8);

        //when
        AttendanceStatistics result = attendanceHistory.getAttendanceStatistics(today);

        //then
        assertAll(
                () -> assertThat(result).extracting("attendanceCount").isEqualTo(1),
                () -> assertThat(result).extracting("lateCount").isEqualTo(1),
                () -> assertThat(result).extracting("absenceCount").isEqualTo(3)
        );
    }
}
