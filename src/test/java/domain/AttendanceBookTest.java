package domain;

import static domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static util.loader.FileLoader.loadCSV;
import static util.parser.DateTimeParser.parseStringToDateTime;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class AttendanceBookTest {

    @Nested
    @DisplayName("크루 생성 테스트")
    class CreateCrewTest {

        @Test
        @DisplayName("파일(csv)에서 크루별 데이터를 구분할 수 있다.")
        void separateCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            assertThat(attendanceBook.countCrew()).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("크루 출석 테스트")
    class SaveCrewTest {

        @Test
        @DisplayName("크루가 출석을 안한 날이라면 기록을 추가할 수 있다.")
        void saveCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "빙봉";
            LocalDateTime dateTime = parseStringToDateTime("2024-12-09 10:02");

            Crew crew = attendanceBook.findCrewByName(name);
            attendanceBook.saveAttendanceRecord(name, dateTime);

            assertThat(crew.countRecord()).isEqualTo(6);
        }

        @Test
        @DisplayName("크루가 출석을 한 날이라면 수정 기능을 안내한다.")
        void guideEditFeature() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "빙봉";
            LocalDateTime dateTime = parseStringToDateTime("2024-12-06 10:02");

            assertThatThrownBy(() -> attendanceBook.saveAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 출석 기록이 있으므로 수정만 가능합니다.");
        }

        @Test
        @DisplayName("등록된 크루만 출석 가능하다.")
        void notContainCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "사나";
            LocalDateTime dateTime = parseStringToDateTime("2024-12-09 10:02");

            assertThatThrownBy(() -> attendanceBook.saveAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 크루입니다.");
        }
    }

    @Nested
    @DisplayName("크루 수정 테스트")
    class EditCrewTest {

        @Test
        @DisplayName("크루가 출석을 한 날에만 기록을 수정할 수 있다.")
        void editCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "빙봉";
            LocalDateTime editedDateTime = parseStringToDateTime("2024-12-06 10:01");
            DailyRecord editedRecord = attendanceBook.editAttendanceRecord(name, editedDateTime);

            Assertions.assertThat(editedRecord.getAttendedTime()).isEqualTo(editedDateTime.toLocalTime());
            Assertions.assertThat(editedRecord.getStatus()).isEqualTo(PRESENT);
        }

        @Test
        @DisplayName("크루가 출석을 안했다면 출석 확인 기능을 안내한다.")
        void guideAttendFeature() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "빙봉";
            LocalDateTime dateTime = parseStringToDateTime("2024-12-09 10:02");

            assertThatThrownBy(() -> attendanceBook.editAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정전 먼저 출석을 확인을 해야합니다.");
        }

        @Test
        @DisplayName("등록된 크루만 수정 가능하다.")
        void notContainCrew() {
            AttendanceBook attendanceBook = new AttendanceBook();
            attendanceBook.initializeCrewRecords(loadCSV("src/test/resources/attendances.csv"));

            String name = "사나";
            LocalDateTime dateTime = parseStringToDateTime("2024-12-06 10:02");

            assertThatThrownBy(() -> attendanceBook.editAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 크루입니다.");
        }
    }
}