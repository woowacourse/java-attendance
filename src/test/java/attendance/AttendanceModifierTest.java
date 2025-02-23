package attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.common.exception.AttendanceFileException;
import attendance.domain.AttendanceFileReader;
import attendance.domain.attendanceBook.AttendanceBook;
import attendance.domain.attendanceManager.AttendanceManager;
import attendance.domain.attendanceManager.AttendanceModifier;

public class AttendanceModifierTest {
    private AttendanceManager attendanceManager;
    private static final String TEST_FILE = "/attendances.csv";

    @BeforeEach
    void setUp() throws AttendanceFileException {
        var repository = new AttendanceFileReader(TEST_FILE);
        var lines = repository.getLines();
        var attendanceBook = AttendanceBook.from(lines);

        attendanceManager = new AttendanceModifier(attendanceBook);
    }

    @Test
    @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
    void test_modifyAttendance() {

    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력할 경우, 예외가 발생한다.")
    void error_notRegisteredNickname() {

    }

    @Test
    @DisplayName("잘못된 날짜 형식을 입력할 경우, 예외가 발생한다")
    void error_wrongDateFormat() {

    }

    @Test
    @DisplayName("잘못된 시간 형식을 입력할 경우, 예외가 발생한다.")
    void error_wrongTimeFormat() {

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
