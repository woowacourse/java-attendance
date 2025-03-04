package domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.loader.FileLoader;
import util.parser.DateTimeParser;
import util.parser.FileParser;

@Nested
public class AttendanceManagerTest {

    Map<String, List<LocalDateTime>> attendanceData = new HashMap<>();

    @BeforeEach
    void initializeAttendanceData() {
        attendanceData.put("빙봉", List.of(
            LocalDateTime.of(2024, 12, 6, 10, 8),
            LocalDateTime.of(2024, 12, 5, 10, 6),
            LocalDateTime.of(2024, 12, 4, 10, 7),
            LocalDateTime.of(2024, 12, 3, 10, 3),
            LocalDateTime.of(2024, 12, 2, 13, 6)
        ));

        attendanceData.put("이든", List.of(
            LocalDateTime.of(2024, 12, 6, 10, 7),
            LocalDateTime.of(2024, 12, 4, 10, 8),
            LocalDateTime.of(2024, 12, 3, 10, 6),
            LocalDateTime.of(2024, 12, 2, 13, 2)
        ));

        attendanceData.put("빙티", List.of(
            LocalDateTime.of(2024, 12, 6, 10, 1),
            LocalDateTime.of(2024, 12, 5, 10, 6),
            LocalDateTime.of(2024, 12, 4, 10, 2),
            LocalDateTime.of(2024, 12, 3, 10, 7),
            LocalDateTime.of(2024, 12, 2, 13, 0)
        ));

        attendanceData.put("짱수", List.of(
            LocalDateTime.of(2024, 12, 6, 10, 0),
            LocalDateTime.of(2024, 12, 5, 10, 0),
            LocalDateTime.of(2024, 12, 4, 10, 0),
            LocalDateTime.of(2024, 12, 3, 10, 0),
            LocalDateTime.of(2024, 12, 2, 13, 0)
        ));

        attendanceData.put("쿠키", List.of(
            LocalDateTime.of(2024, 12, 5, 10, 7),
            LocalDateTime.of(2024, 12, 4, 10, 2),
            LocalDateTime.of(2024, 12, 3, 10, 6)
        ));
    }

    @Nested
    @DisplayName("크루 생성 테스트")
    class CreateCrewTest {

        @Test
        @DisplayName("코치는 평일에 크루를 출석시킬 수 있다.")
        void attendCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 10:02");

            assertThatNoException().isThrownBy(() -> attendanceManager.attendCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 휴일에 크루를 출석시킬 수 없다.")
        void notAttendCrewInHoliday() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-25 10:02");

            assertThatThrownBy(() -> attendanceManager.attendCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 운영시간에 크루를 출석시킬 수 있다.")
        void notAttendCrewInOperatingTime() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 23:13");

            assertThatThrownBy(() -> attendanceManager.attendCrew(name, dateTime));
        }
    }

    @Nested
    @DisplayName("크루 수정 테스트")
    class EditCrewTest {

        @Test
        @DisplayName("코치는 평일의 기록을 수정시킬 수 있다.")
        void editCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime editedDateTime = DateTimeParser.parseStringToDateTime("2024-12-06 10:02");

            assertThatNoException().isThrownBy(() -> attendanceManager.editCrew(name, editedDateTime));
        }

        @Test
        @DisplayName("코치는 휴일의 기록을 수정시킬 수 없다.")
        void notEditCrewInHoliday() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-25 10:02");

            assertThatThrownBy(() -> attendanceManager.editCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 운영시간 외의 시간으로 기록을 수정시킬 수 없다.")
        void notEditCrewInOperatingTime() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            AttendanceManager attendanceManager = new AttendanceManager(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-06 23:13");

            assertThatThrownBy(() -> attendanceManager.editCrew(name, dateTime));
        }
    }
}