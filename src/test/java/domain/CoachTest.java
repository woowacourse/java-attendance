package domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.loader.FileLoader;
import util.parser.DateTimeParser;

@Nested
public class CoachTest {

    @Nested
    @DisplayName("크루 생성 테스트")
    class CreateCrewTest {

        @Test
        @DisplayName("코치는 평일에 크루를 출석시킬 수 있다.")
        void attendCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 10:02");

            assertThatNoException().isThrownBy(() -> coach.attendCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 휴일에 크루를 출석시킬 수 없다.")
        void notAttendCrewInHoliday() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-25 10:02");

            assertThatThrownBy(() -> coach.attendCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 운영시간에 크루를 출석시킬 수 있다.")
        void notAttendCrewInOperatingTime() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 23:13");

            assertThatThrownBy(() -> coach.attendCrew(name, dateTime));
        }
    }

    @Nested
    @DisplayName("크루 수정 테스트")
    class EditCrewTest {

        @Test
        @DisplayName("코치는 평일의 기록을 수정시킬 수 있다.")
        void editCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime editedDateTime = DateTimeParser.parseStringToDateTime("2024-12-06 10:02");

            assertThatNoException().isThrownBy(() -> coach.editCrew(name, editedDateTime));
        }

        @Test
        @DisplayName("코치는 휴일의 기록을 수정시킬 수 없다.")
        void notEditCrewInHoliday() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-25 10:02");

            assertThatThrownBy(() -> coach.editCrew(name, dateTime));
        }

        @Test
        @DisplayName("코치는 운영시간 외의 시간으로 기록을 수정시킬 수 없다.")
        void notEditCrewInOperatingTime() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(FileLoader.loadCSV("src/test/resources/attendances.csv"));

            Coach coach = new Coach(attendanceBook);
            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-06 23:13");

            assertThatThrownBy(() -> coach.editCrew(name, dateTime));
        }
    }
}