import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.AttendanceManager;
import domain.AttendanceStatus;
import domain.Records;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-14 13:03", "2024-12-25 13:03"})
    @DisplayName("수정하려는 날짜가 등교일이 아닌 경우 예외를 발생한다.")
    void holidayTest(String dateAndTime) {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-13 13:00", formatter);
        LocalDateTime editedDateAndTime = LocalDateTime.parse(dateAndTime, formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attendanceManager.editCrew(name, editedDateAndTime);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("등교일이 아닙니다.");
    }

    private TimeAndStatus findTimeAndStatus(String name, LocalDate localDate) {
        Records records = attendanceManager.findByName(name);
        return records.findByDate(localDate);
    }
}
