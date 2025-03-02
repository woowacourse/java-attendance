package domain;

import static domain.AttendanceStatus.ABSENT;
import static domain.AttendanceStatus.LATE;
import static domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static util.parser.DateTimeParser.parseStringToDate;
import static util.parser.DateTimeParser.parseStringToDateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@Nested
public class AttendanceStatusTest {

    @Nested
    @DisplayName("시간별 상태 생성 테스트")
    class CreateStatusTest {

        @ParameterizedTest
        @MethodSource("providePresentTime")
        @DisplayName("출석 상태를 반환한다.")
        void pickPresent(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(PRESENT);
        }

        static Stream<LocalDateTime> providePresentTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 0),
                LocalDateTime.of(2025, 12, 3, 10, 5),
                LocalDateTime.of(2025, 12, 4, 9, 30)
            );
        }

        @ParameterizedTest
        @MethodSource("provideLateTime")
        @DisplayName("지각 상태를 반환한다.")
        void pickLate(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(LATE);
        }

        static Stream<LocalDateTime> provideLateTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 6),
                LocalDateTime.of(2025, 12, 3, 10, 30)
            );
        }

        @ParameterizedTest
        @MethodSource("provideAbsentTime")
        @DisplayName("결석 상태를 반환한다.")
        void pickAbsent(LocalDateTime dateTime) {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();

            AttendanceStatus status = AttendanceStatus.of(date.getDayOfWeek(), time);
            assertThat(status).isEqualTo(ABSENT);
        }

        static Stream<LocalDateTime> provideAbsentTime() {
            return Stream.of(
                LocalDateTime.of(2024, 12, 2, 13, 31),
                LocalDateTime.of(2025, 12, 3, 10, 59),
                LocalDateTime.of(2025, 12, 4, 11, 59)
            );
        }
    }

    @Nested
    @DisplayName("출석 기록 통계 테스트")
    class RecordStatisticsTest {

        Crew crew = new Crew();

        @BeforeEach
        void initializeRecord() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                parseStringToDateTime("2024-12-02 10:08"), // 지각
                parseStringToDateTime("2024-12-03 10:02"), // 출석
                parseStringToDateTime("2024-12-04 10:32"), // 결석
                parseStringToDateTime("2024-12-05 09:30"), // 출석
                parseStringToDateTime("2024-12-06 11:02"), // 결석
                // 9일 - 결석
                parseStringToDateTime("2024-12-10 10:02") // 출석
            );
            crew.initializeDailyRecords(crewRecords);
        }

        @Test
        @DisplayName("출석 횟수를 계산할 수 있다.")
        void calculatePresent() {
            LocalDate startDate = parseStringToDate("2024-12-01");
            LocalDate endDate = parseStringToDate("2024-12-11");
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfYearAndMonth(startDate, endDate);
            Map<AttendanceStatus, Integer> statisticsResult = AttendanceStatus.countStatus(records);

            assertThat(statisticsResult.get(PRESENT)).isEqualTo(3);
        }

        @Test
        @DisplayName("지각 횟수를 계산할 수 있다.")
        void calculateLate() {
            LocalDate startDate = parseStringToDate("2024-12-01");
            LocalDate endDate = parseStringToDate("2024-12-11");
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfYearAndMonth(startDate, endDate);
            Map<AttendanceStatus, Integer> statisticsResult = AttendanceStatus.countStatus(records);

            assertThat(statisticsResult.get(LATE)).isEqualTo(1);
        }

        @Test
        @DisplayName("결석 횟수를 계산할 수 있다.")
        void calculateAbsent() {
            LocalDate startDate = parseStringToDate("2024-12-01");
            LocalDate endDate = parseStringToDate("2024-12-11");
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfYearAndMonth(startDate, endDate);
            Map<AttendanceStatus, Integer> statisticsResult = AttendanceStatus.countStatus(records);

            assertThat(statisticsResult.get(ABSENT)).isEqualTo(3);
        }
    }
}