import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoriesTest {

    @Test
    @DisplayName("이름을 기준으로 출석 기록 찾기")
    public void findAttendanceHistoriesByName() {
        // given
        String name = "쿠키";
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 18, 10, 1);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 19, 10, 2);
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance(attendanceTime1));
        attendances.add(new Attendance(attendanceTime2));
        attendances.add(new Attendance(attendanceTime3));
        AttendanceHistory attendanceHistory = new AttendanceHistory(name, attendances);

        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceHistories.add(attendanceHistory);
        AttendanceHistories histories = new AttendanceHistories(attendanceHistories);

        assertThat(histories.findByName(name).getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("이름을 기준으로 기존 출석 기록에 추가하기")
    public void addAttendanceHistoriesByName() {
        // given
        String name = "쿠키";
        LocalDateTime attendanceTime1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime attendanceTime2 = LocalDateTime.of(2024, 12, 18, 10, 1);
        LocalDateTime attendanceTime3 = LocalDateTime.of(2024, 12, 19, 10, 2);
        List<Attendance> attendances = new ArrayList<>();
        attendances.add(new Attendance(attendanceTime1));
        attendances.add(new Attendance(attendanceTime2));
        attendances.add(new Attendance(attendanceTime3));
        AttendanceHistory attendanceHistory = new AttendanceHistory(name, attendances);

        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        attendanceHistories.add(attendanceHistory);
        AttendanceHistories histories = new AttendanceHistories(attendanceHistories);
        histories.addAttendanceHistory(name, new Attendance(LocalDateTime.of(2024, 12, 20, 10, 3));

        assertThat(histories.findByName(name).getAttendances().size()).isEqualTo(4);


    }
}
