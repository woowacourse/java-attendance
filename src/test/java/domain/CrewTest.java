package domain;

import static domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static util.parser.DateTimeParser.parseStringToDate;
import static util.parser.DateTimeParser.parseStringToDateTime;
import static util.parser.DateTimeParser.parseStringToTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class CrewTest {

    @Nested
    @DisplayName("출석 기록 검색 테스트")
    class FindRecordTest {

        @Test
        @DisplayName("2024년 12월의 출석 기록을 검색할 수 있다.")
        void searchDecemberRecords() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:08"),
                parseStringToDateTime("2024-12-05 10:02")
            );
            crew.initializeDailyRecords(crewRecords);

            LocalDate startDate = parseStringToDate("2024-12-01");
            LocalDate endDate = parseStringToDate("2024-12-06"); // 2일, 3일을 결석으로 추가
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, endDate);

            assertThat(records.size()).isEqualTo(4);
        }

        @Test
        @DisplayName("2025년 3월의 출석 기록을 검색할 수 없다.")
        void searchMarchRecords() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:08"),
                parseStringToDateTime("2024-12-05 10:02")
            );
            crew.initializeDailyRecords(crewRecords);

            LocalDate startDate = parseStringToDate("2025-03-01");
            LocalDate endDate = parseStringToDate("2025-03-02");
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, endDate);

            assertThat(records.size()).isEqualTo(0);
        }

        @Test
        @DisplayName("2025년 2월에 출석을 하고 기록을 검색할 수 없다.")
        void searchRecordAfterAttendance() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:08"),
                parseStringToDateTime("2024-12-05 10:02")
            );
            crew.initializeDailyRecords(crewRecords);

            crew.addDailyRecord(parseStringToDateTime("2025-02-04 10:08"));
            LocalDate startDate = parseStringToDate("2025-02-01");
            LocalDate endDate = parseStringToDate("2025-02-05");// 3일을 결석으로 추가
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, endDate);

            assertThat(records.size()).isEqualTo(2);
        }
    }

    @Nested
    @DisplayName("크루 출석 기록 생성 테스트")
    class CreateCrewRecordTest {

        @Test
        @DisplayName("크루별 데이터를 날짜와 시간으로 구분할 수 있다.")
        void separateCrew() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:08"),
                parseStringToDateTime("2024-12-05 10:02")
            );
            crew.initializeDailyRecords(crewRecords);

            LocalDate date1 = parseStringToDate("2024-12-04");
            LocalTime time1 = parseStringToTime("10:08");
            LocalDate date2 = parseStringToDate("2024-12-05");
            LocalTime time2 = parseStringToTime("10:02");

            assertAll(
                () -> assertThat(crew.findRecordByDate(date1)).isEqualTo(
                    new DailyRecord(date1.getDayOfWeek(), time1)),
                () -> assertThat(crew.findRecordByDate(date2)).isEqualTo(
                    new DailyRecord(date2.getDayOfWeek(), time2))
            );
        }

        @Test
        @DisplayName("출석 날짜와 시간에 대한 정보를 저장할 수 있다.")
        void addRecord() {
            Crew crew = new Crew();
            LocalDateTime dateTime = parseStringToDateTime("2024-12-09 10:02");

            DailyRecord record = crew.addDailyRecord(dateTime);

            assertAll(
                () -> assertThat(record.getAttendedTime()).isEqualTo(dateTime.toLocalTime()),
                () -> assertThat(record.getStatus()).isEqualTo(PRESENT)
            );
        }
    }

    @Nested
    @DisplayName("크루 출석 기록 수정 테스트")
    class UpdateCrewRecordTest {

        @Test
        @DisplayName("지각을 출석으로 수정할 수 있다.")
        void editLateRecord() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:08")
            );
            crew.initializeDailyRecords(crewRecords);
            LocalDateTime editedDateTime = parseStringToDateTime("2024-12-04 10:02");

            DailyRecord editedRecord = crew.updateDailyRecord(editedDateTime);

            assertAll(
                () -> assertThat(editedRecord.getAttendedTime()).isEqualTo(editedDateTime.toLocalTime()),
                () -> assertThat(editedRecord.getStatus()).isEqualTo(PRESENT)
            );
        }

        @Test
        @DisplayName("결석을 출석으로 수정할 수 있다.")
        void editAbsentRecord() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-04 10:40")
            );
            crew.initializeDailyRecords(crewRecords);
            LocalDateTime editedDateTime = parseStringToDateTime("2024-12-04 10:02");

            DailyRecord editedRecord = crew.updateDailyRecord(editedDateTime);

            assertAll(
                () -> assertThat(editedRecord.getAttendedTime()).isEqualTo(editedDateTime.toLocalTime()),
                () -> assertThat(editedRecord.getStatus()).isEqualTo(PRESENT)
            );
        }
    }
}