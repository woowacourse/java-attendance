package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.AttendanceFileReader;
import attendance.domain.attendance.Attendance;
import attendance.domain.attendance.AttendanceBook;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;

public class AttendancesTest {
    private final AttendanceFileReader attendanceFileReader = AttendanceFileReader.from("/attendances.csv");
    private final AttendanceBook attendanceBook = AttendanceBook.from(attendanceFileReader.getLines());

    public AttendancesTest() throws AttendanceFileException {
    }

    @Test
    @DisplayName("닉네임과 출석 정보을 입력하면, 출석 정보를 저장한다.")
    void test_attendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 11, 10, 1);

        var attendances = attendanceBook.getAttendances(nickname);
        var newAttendance = Attendance.from(dateTime);

        attendances.validateDuplicate(newAttendance);
        attendances.add(newAttendance);
    }

    @Test
    @DisplayName("다시 출석할 경우, 예외가 발생한다.")
    void error_retireAttendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        var attendances = attendanceBook.getAttendances(nickname);
        var newAttendance = Attendance.from(dateTime);

        assertThatThrownBy(() -> attendances.validateDuplicate(newAttendance))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("이미 출석되었습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    @DisplayName("닉네임과 날짜, 수정 시간을 입력한 후, 출석을 수정한다.")
    void test_modifyAttendance() {
        var nickname = "이든";
        var dateTime = LocalDateTime.of(2024, 12, 2, 10, 2);

        var attendances = attendanceBook.getAttendances(nickname);
        var newAttendance = Attendance.from(dateTime);
        Optional<Attendance> oldAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate());
        oldAttendance.ifPresent(attendances::remove);
        attendances.add(newAttendance);

        Attendance resultAttendance = attendanceBook.findAttendance(nickname, dateTime.toLocalDate())
            .orElseThrow(() -> new AssertionError("잘못된 테스트 입력값입니다."));
        assertAll(
            () -> assertThat(resultAttendance).isNotEqualTo(oldAttendance),
            () -> assertThat(resultAttendance).isEqualTo(newAttendance)
        );
    }
}
