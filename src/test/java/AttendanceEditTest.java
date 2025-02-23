import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceManager;
import domain.AttendanceStatus;
import domain.Records;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceEditTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @DisplayName("출석 상태를 출석에서 지각으로 변경한다.")
    void 출석_상태_지각으로_변경() {
        String name = "빙봉";
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDateTime editedDateAndTime = LocalDateTime.parse("2024-12-16 13:06", formatter);
        LocalDate localDate = editedDateAndTime.toLocalDate();

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        attendanceManager.editCrew(name, editedDateAndTime);

        TimeAndStatus timeAndStatus = findTimeAndStatus(name, localDate);

        assertThat(timeAndStatus.getStatus()).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    @DisplayName("출석 상태를 지각에서 출석으로 변경한다.")
    void 출석_상태_출석으로_변경() {
        String name = "빙봉";
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-16 13:07", formatter);
        LocalDateTime editedDateAndTime = LocalDateTime.parse("2024-12-16 12:59", formatter);
        LocalDate localDate = editedDateAndTime.toLocalDate();

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        attendanceManager.editCrew(name, editedDateAndTime);

        TimeAndStatus timeAndStatus = findTimeAndStatus(name, localDate);

        assertThat(timeAndStatus.getStatus()).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    @DisplayName("출석하지 않고 수정하는 경우 예외메시지를 출력한다.")
    void 출석하지_않고_수정하는_경우() {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-13 13:00", formatter);
        LocalDateTime editedDateAndTime = LocalDateTime.parse("2024-12-16 13:03", formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            attendanceManager.editCrew(name, editedDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private TimeAndStatus findTimeAndStatus(String name, LocalDate localDate) {
        Records records = attendanceManager.findByName(name);
        return records.findByDate(localDate);
    }
}
