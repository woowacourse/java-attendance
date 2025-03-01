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
        var nickname = "이든";
        var dateTime = LocalDateTime.now();
        attendanceBook.add("이든", dateTime);
        var nowAttendanceBook = attendanceBook.attendancesBook();
        var attendance = new Attendance(dateTime);

        assertThat(nowAttendanceBook.get(nickname, dateTime)).isEqualTo(attendance);
    }
}
