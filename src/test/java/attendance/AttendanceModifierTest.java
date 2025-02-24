package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendanceBook.Attendance;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceManager.AttendanceManager;
import attendance.domain.attendanceManager.AttendanceModifier;

public class AttendanceModifierTest {
    private static final String TEST_FILE = "/attendances.csv";

    private AttendanceManager attendanceModifier;
    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = new AttendanceFileReader(TEST_FILE);
        var lines = repository.getLines();
        attendanceBook = AttendanceBook.from(lines);
        attendanceModifier = new AttendanceModifier(attendanceBook);
    }

    @Test
    @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
    void test_modifyAttendance() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 2);
        var time = LocalTime.of(13, 2);
        var attendance = new Attendance(LocalDateTime.of(date, time));

        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);

        time = LocalTime.of(10, 1);
        var modifiedAttendance = new Attendance(LocalDateTime.of(date, time));
        attendanceModifier.manage(nickname, date, time);

        assertThatThrownBy(() -> attendanceBook.findAttendance(nickname, attendance))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("출석 정보를 찾을 수 없습니다.");
        assertThat(attendanceBook.findAttendance(nickname, modifiedAttendance)).isNotEqualTo(attendance);
        assertThat(attendanceBook.findAttendance(nickname, modifiedAttendance)).isEqualTo(modifiedAttendance);
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력할 경우, 예외가 발생한다.")
    void error_notRegisteredNickname() {
        var nickname = "고든";
        var date = LocalDate.of(2024, 12, 2);
        var time = LocalTime.of(10, 1);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, date, time))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    @Test
    @DisplayName("출석하지 않은 날에 대한 출석을 수정한다.")
    void test_modifyNonAttendanceDay() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 24);
        var time = LocalTime.of(10, 1);

        var attendance = new Attendance(LocalDateTime.of(date, time));
        assertThatThrownBy(() -> attendanceBook.findAttendance(nickname, attendance))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("출석 정보를 찾을 수 없습니다.");

        attendanceModifier.manage(nickname, date, time);
        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("출석 상태를, 지각으로 수정한다.")
    void test_modifyStateToLate() {

    }

    @Test
    @DisplayName("출석 상태를, 결석으로 수정한다.")
    void test_modifyStateToAbsence() {

    }

    @Test
    @DisplayName("수정 시간이 캠퍼스 운영 시간 이외일 경우, 예외를 발생한다.")
    void error_outOfRangeOnCampusSchedule() {

    }

    @Test
    @DisplayName("수정 날짜가 주말일 경우, 예외를 발생한다.")
    void error_modifyOnWeekend() {

    }

    @Test
    @DisplayName("미래 날짜에 대해 수정할 경우, 예외가 발생한다.")
    void error_modifyFutureAttendance() {

    }

    @Test
    @DisplayName("월요일의 출석 상태를, 지각으로 수정한다.")
    void error_modifyToLateOnMonday() {

    }

    @Test
    @DisplayName("월요일의 출석 상태를, 결석으로 수정한다.")
    void error_modifyToAbsenceOnMonday() {

    }

    @Test
    @DisplayName("수정하는 날짜가 공휴일인 경우, 예외를 발생한다.")
    void error_modifyOnHoliday() {

    }
}
