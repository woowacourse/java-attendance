import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import domain.AttendanceManager;
import domain.Records;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceCheckTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @BeforeEach
    void setUp(){
        String name = "빙봉";
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-13 13:00", formatter);
        attendanceManager.createCrew(name, List.of(initialDateAndTime));
    }

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void should_SaveCrewInfo_When_GivenNameAndDateTime() {
        String name = "빙봉";
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        attendanceManager.attendCrew(name, attendDateAndTime);

        assertThat(attendanceManager.findByName(name).getAttendanceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("크루의 출석 시간과 상태를 저장한다.")
    void should_SaveAttendanceTimeAndStatus_When_GivenNameAndDateTime() {
        String name = "빙봉";
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDate localDate = dateAndTime.toLocalDate();

        TimeAndStatus attendTimeStatus = attendanceManager.attendCrew(name, dateAndTime);
        Records records = attendanceManager.findByName(name);
        TimeAndStatus expectedTimeStatus = records.findByDate(localDate);

        assertThat(expectedTimeStatus).isEqualTo(attendTimeStatus);
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void should_ThrowException_When_AttendanceAlreadyExists() {
        String name = "빙봉";
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-13 13:03", formatter);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("이미 출석한 경우 수정 기능을 사용하세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-14 13:03", "2024-12-25 13:03"})
    @DisplayName("등교일이 아닌 날 출석하려는 경우 예외를 발생한다.")
    void should_ThrowException_When_AttendanceIsOnHoliday(String dateAndTime) {
        String name = "빙봉";
        LocalDateTime attendDateAndTime = LocalDateTime.parse(dateAndTime, formatter);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("등교일이 아닙니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"2024-12-16 23:01", "2024-12-16 07:59"})
    @DisplayName("캠퍼스 운영시간이 아닌 경우 예외를 발생한다.")
    void should_ThrowException_When_OutsideOperatingHours(String attendDateTime) {
        String name = "빙봉";
        LocalDateTime attendDateAndTime = LocalDateTime.parse(attendDateTime, formatter);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        });
        Assertions.assertThat(exception.getMessage()).isEqualTo("캠퍼스 운영시간이 아닙니다.");
    }
}
