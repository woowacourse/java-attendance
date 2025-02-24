package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import attendance.common.exception.AttendanceArgumentException;
import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.AttendanceStatus;
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

    // 해당 테스트는 attendance에 의해 출석 상태가 결정되기에, modifier의 핵심 기능과는 다소 거리가 있다.
    // 때문에, 기존의 단위 테스트와 다른 면이 있어,성공 테스트 케이스에 대해선 크게 세분화하진 않겠다.
    @ParameterizedTest
    @DisplayName("출석이 수정될 경우, 출석 상태도 수정한다")
    @CsvSource({
        "2,13,5, LATE",
        "2,13,30, ABSENCE",
        "10,10,5, LATE",
        "10,10,30, ABSENCE"
    })
    void test_modifyAttendanceState(int dayOfMonth, int hour, int addMinutes, AttendanceStatus expectedStatus) {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, dayOfMonth);
        var time = LocalTime.of(hour, 2);
        var attendance = new Attendance(LocalDateTime.of(date, time));

        assertThat(attendanceBook.findAttendance(nickname, attendance).attendanceStatus())
            .isEqualTo(AttendanceStatus.ATTENDANCE);

        var modifiedTime = time.plusMinutes(addMinutes);
        var modifiedAttendance = new Attendance(LocalDateTime.of(date, modifiedTime));
        attendanceModifier.manage(nickname, date, modifiedTime);

        assertThatThrownBy(() -> attendanceBook.findAttendance(nickname, attendance))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("출석 정보를 찾을 수 없습니다.");
        assertThat(attendanceBook.findAttendance(nickname, modifiedAttendance).attendanceStatus())
            .isEqualTo(expectedStatus);
    }

    @Test
    @DisplayName("수정 시간이 캠퍼스 운영 시간 이외일 경우, 예외를 발생한다.")
    void error_outOfRangeOnCampusSchedule() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 2);
        var modifiedTime = LocalTime.of(7, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, date, modifiedTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("수정 날짜가 주말일 경우, 예외를 발생한다.")
    void error_modifyOnWeekend() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 14);
        var modifiedTime = LocalTime.of(10, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, date, modifiedTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("수정하는 날짜가 공휴일인 경우, 예외를 발생한다.")
    void error_modifyOnHoliday() {
        var nickname = "이든";
        var date = LocalDate.of(2024, 12, 25);
        var modifiedTime = LocalTime.of(10, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, date, modifiedTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 25일 수요일은 등교일이 아닙니다.");
    }

    @Test
    @Disabled
    @DisplayName("미래 날짜에 대해 수정할 경우, 예외가 발생한다.")
    void error_modifyFutureAttendance() {
        //현재 날짜 설정은 일단 보류,,
    }
}
