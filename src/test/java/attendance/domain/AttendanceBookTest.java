package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {
    private final AttendanceReader attendanceReader = new AttendanceReader("attendances.csv");
    private final AttendanceBook attendanceBook = attendanceReader.load();

    AttendanceBookTest() throws FileNotFoundException {
    }

    @Test
    @DisplayName("닉네임을 입력하면, 현재 시간으로 출석한다.")
    void test_SaveAttendanceWhenEnterNickname() {
        var nickname = new Nickname("이든");
        var dateTime = LocalDateTime.now();
        attendanceBook.add(nickname, dateTime);

        var attendance = new Attendance(dateTime);
        assertThat(attendanceBook.getAttendance(nickname, dateTime.toLocalDate())).isEqualTo(attendance);
    }
}
