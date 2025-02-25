import domain.Attendance;
import domain.AttendanceSheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttendanceSheetTest {
    AttendanceSheet attendanceSheet;

    @BeforeEach
    void setUp() {
        attendanceSheet = new AttendanceSheet(
                new ArrayList<>(
                        List.of(new Attendance("링크", LocalDate.of(2024, 12, 10), LocalTime.of(10,0)),
                                new Attendance("링크", LocalDate.of(2024, 12, 11), LocalTime.of(11,0))
                        ))
        );
    }
    @Test
    @DisplayName("닉네임과 등교 날짜, 등교 시간을 입력하면 출석 기록을 추가할 수 있다")
    public void attendTest() {
        //given
        String nickname = "링크";
        LocalDate date = LocalDate.of(2024, 12, 12);
        LocalTime time = LocalTime.of(10,0);

        //when-then
        assertDoesNotThrow(() -> attendanceSheet.add(nickname, date, time));
    }

    @Test
    @DisplayName("이미 출석한 경우 다시 출석할 수 없으며 수정 기능을 이용하도록 안내하는 예외가 발생한다")
    public void alreadyAttendTest() {
        String nickname = "링크";
        LocalDate date = LocalDate.of(2024, 12, 11);

        //when-then
        assertThatThrownBy(() -> attendanceSheet.validateIsAlreadyAttendance(nickname, date))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
