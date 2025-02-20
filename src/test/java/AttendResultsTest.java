import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendResultsTest {

    @Test
    @DisplayName("출석, 지각, 결석 횟수를 파악하는 기능")
    void test() throws Exception {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        int day = 5;
        List<Attend> attends = List.of(
                Attend.of("2", "10:00"),
                Attend.of("3", "10:06"),
                Attend.of("4", "10:31")
        );
        for (Attend attend : attends) {
            attendanceBook.attend(name, attend);
        }
        List<AttendanceResult> attendanceResult = attendanceBook.checkAttendance(name, DateUtil.getAttendUntilDay(day));
        AttendanceResults attendanceResults = new AttendanceResults(attendanceResult);

        //when
        AttendCount attendCount = attendanceResults.countAttendStatus();

        //then
        AttendCount expected = new AttendCount(1, 1, 2);
        Assertions.assertThat(attendCount).isEqualTo(expected);
    }
}
