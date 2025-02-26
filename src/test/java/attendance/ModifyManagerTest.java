package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import attendance.domain.AttendanceFileReader;
import attendance.domain.AttendanceStatus;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.domain.attendanceManager.ModifyManager;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;

public class ModifyManagerTest {
    private final AttendanceFileReader attendanceFileReader = AttendanceFileReader.from("/attendances.csv");
    private final AttendanceBook attendanceBook = AttendanceBook.from(attendanceFileReader.getLines());
    private final ModifyManager attendanceModifier = new ModifyManager(attendanceBook);

    public ModifyManagerTest() throws AttendanceFileException {
    }

    @Test
    @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
    void test_modifyAttendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 2, 13, 2);
        var attendance = Attendance.from(dateTime);

        assertThat(attendanceBook.findAttendance(nickname, attendance)).isEqualTo(attendance);

        var modifiedTime = dateTime.minusHours(3);
        var modifiedAttendance = Attendance.from(modifiedTime);
        attendanceModifier.manage(nickname, modifiedTime);

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
        var dateTime = LocalDateTime.of(2024, 12, 2, 10, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    @Test
    @DisplayName("출석하지 않은 날에 대한 출석을 수정한다.")
    void test_modifyNonAttendanceDay() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 24, 10, 1);
        var attendance = Attendance.from(dateTime);

        assertThatThrownBy(() -> attendanceBook.findAttendance(nickname, attendance))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("출석 정보를 찾을 수 없습니다.");

        attendanceModifier.manage(nickname, dateTime);
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

        var dateTime = LocalDateTime.of(2024, 12, dayOfMonth, hour, 2);
        var attendance = Attendance.from(dateTime);

        assertThat(attendanceBook.findAttendance(nickname, attendance).attendanceStatus())
            .isEqualTo(AttendanceStatus.ATTENDANCE);

        var modifiedTime = dateTime.plusMinutes(addMinutes);
        var modifiedAttendance = Attendance.from(modifiedTime);
        attendanceModifier.manage(nickname, modifiedTime);

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
        var dateTime = LocalDateTime.of(2024, 12, 2, 7, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("수정 날짜가 주말일 경우, 예외를 발생한다.")
    void error_modifyOnWeekend() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 14, 10, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("수정하는 날짜가 공휴일인 경우, 예외를 발생한다.")
    void error_modifyOnHoliday() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 25, 10, 2);

        assertThatThrownBy(() -> attendanceModifier.manage(nickname, dateTime))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("12월 25일 수요일은 등교일이 아닙니다.");
    }
}
