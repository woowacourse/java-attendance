import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceManager;
import domain.Records;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceCheckTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void should_SaveCrewInfo_When_GivenNameAndDateTime() {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-13 13:00", formatter);
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        attendanceManager.attendCrew(name, attendDateAndTime);

        assertThat(attendanceManager.findByName(name).getAttendanceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("크루의 출석 시간과 상태를 저장한다.")
    void should_SaveAttendanceTimeAndStatus_When_GivenNameAndDateTime() {
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDate localDate = dateAndTime.toLocalDate();
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of());
        TimeAndStatus timeStatus = attendanceManager.attendCrew(name, dateAndTime);

        Records records = attendanceManager.findByName(name);
        TimeAndStatus expectedTimeStatus = records.findByDate(localDate);

        assertThat(expectedTimeStatus).isEqualTo(timeStatus);
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void should_ThrowException_When_AttendanceAlreadyExists() {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-16 13:03", formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-14 13:03", "2024-12-25 13:03"})
    @DisplayName("등교일이 아닌 날 출석하려는 경우 예외를 발생한다.")
    void should_ThrowException_When_AttendanceIsOnHoliday(String dateAndTime) {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDateTime attendDateAndTime = LocalDateTime.parse(dateAndTime, formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
