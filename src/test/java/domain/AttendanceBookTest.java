package domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.parser.DateTimeParser;

@Nested
public class AttendanceBookTest {

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
        @DisplayName("파일(csv)에서 크루별 데이터를 구분할 수 있다.")
        void separateCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            assertThat(attendanceBook.countCrew()).isEqualTo(5);
        }
    }

    @Nested
    @DisplayName("크루 출석 테스트")
    class SaveCrewTest {

        @Test
        @DisplayName("크루가 출석을 안한 날이라면 기록을 추가할 수 있다.")
        void saveCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 10:02");

            Crew crew = attendanceBook.findCrewByName(name);
            attendanceBook.saveAttendanceRecord(name, dateTime);

            assertThat(crew.countRecord()).isEqualTo(6);
        }

        @Test
        @DisplayName("크루가 출석을 한 날이라면 수정 기능을 안내한다.")
        void guideEditFeature() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-06 10:02");

            assertThatThrownBy(() -> attendanceBook.saveAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석 기록이 있으므로 수정만 가능합니다.");
        }

        @Test
        @DisplayName("등록된 크루만 출석 가능하다.")
        void notContainCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "사나";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 10:02");

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
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "빙봉";
            LocalDateTime editedDateTime = DateTimeParser.parseStringToDateTime("2024-12-06 10:01");
            DailyRecord editedRecord = attendanceBook.editAttendanceRecord(name, editedDateTime);

            assertAll(
                () -> assertThat(editedRecord.getAttendedTime()).isEqualTo(
                    editedDateTime.toLocalTime()),
                () -> assertThat(editedRecord.getStatus()).isEqualTo(AttendanceStatus.PRESENT)
            );
        }

        @Test
        @DisplayName("크루가 출석을 안했다면 출석 확인 기능을 안내한다.")
        void guideAttendFeature() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "빙봉";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-09 10:02");

            assertThatThrownBy(() -> attendanceBook.editAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("수정전 먼저 출석을 확인을 해야합니다.");
        }

        @Test
        @DisplayName("등록된 크루만 수정 가능하다.")
        void notContainCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);

            String name = "사나";
            LocalDateTime dateTime = DateTimeParser.parseStringToDateTime("2024-12-06 10:02");

            assertThatThrownBy(() -> attendanceBook.editAttendanceRecord(name, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 크루입니다.");
        }
    }

    @Nested
    @DisplayName("기록 확인 테스트")
    class RecordCheckTest {

        @Test
        @DisplayName("등록된 크루는 기록을 확인할 수 있다.")
        void recordCheckCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);
            String name = "빙봉";

            Crew crew = attendanceBook.findCrewByName(name);
            assertThat(crew.countRecord()).isEqualTo(5);
        }

        @Test
        @DisplayName("등록되지 않은 크루는 기록을 확인할 수 없다.")
        void recordNotCheckCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);
            String name = "사나";

            assertThatThrownBy(() -> attendanceBook.findCrewByName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("등록되지 않은 크루입니다.");
        }
    }

    @Nested
    @DisplayName("제적 위험 대상자 테스트")
    class WarningCrewTest {

        @Test
        @DisplayName("제적 위험 대상자를 확인할 수 있다.")
        void findWarningCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);
            /*
            짱수: 지각 0 결석 0
            빙티: 지각 2 결석 0
            쿠키: 지각 2 결석 2 (경고 대상자)
            이든: 지각 3 결석 1 (경고 대상자)
            빙봉: 지각 4 결석 0
             */

            LocalDate startDate = DateTimeParser.parseStringToDate("2024-12-01");
            LocalDate endDate = DateTimeParser.parseStringToDate("2024-12-07");

            Map<String, Crew> warningCrew = attendanceBook.findWarningCrew(startDate, endDate);
            assertThat(warningCrew.size()).isEqualTo(2);
        }

        @Test
        @DisplayName("제적 위험 대상자를 정렬할 수 있다.")
        void sortWarningCrew() {
            AttendanceBook attendanceBook = new AttendanceBook(attendanceData);
            /*
            짱수: 지각 0 결석 0
            빙티: 지각 2 결석 0
            쿠키: 지각 2 결석 2 (경고 대상자)
            이든: 지각 3 결석 1 (경고 대상자)
            빙봉: 지각 4 결석 0
             */

            LocalDate startDate = DateTimeParser.parseStringToDate("2024-12-01");
            LocalDate endDate = DateTimeParser.parseStringToDate("2024-12-07");
            Crew crew1 = attendanceBook.findCrewByName("쿠키");
            Crew crew2 = attendanceBook.findCrewByName("이든");

            Map<String, Crew> warningCrew = attendanceBook.findWarningCrew(startDate, endDate);
            assertAll(
                () -> assertThat(warningCrew.get("이든")).isEqualTo(crew2),
                () -> assertThat(warningCrew.get("쿠키")).isEqualTo(crew1)
            );
        }
    }
}