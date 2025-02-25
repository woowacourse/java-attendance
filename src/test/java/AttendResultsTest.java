import domain.Attend;
import domain.AttendCount;
import domain.AttendanceBook;
import domain.AttendanceResults;
import domain.Current;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendResultsTest {

    @Test
    @DisplayName("출석, 지각, 결석 횟수를 파악하는 기능")
    void countAttendResult() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        List<Attend> attends = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31))
        );
        attendanceBook.registerName(name);
        for (Attend attend : attends) {
            attendanceBook.attend(name, attend);
        }
        AttendanceResults attendanceResults = attendanceBook.checkAttendance(name, Current.TODAY.getAttendUntilDay());

        //when
        AttendCount attendCount = attendanceResults.countAttendStatus();

        //then
        AttendCount expected = new AttendCount(1, 1, 7);
        Assertions.assertThat(attendCount).isEqualTo(expected);
    }
}
