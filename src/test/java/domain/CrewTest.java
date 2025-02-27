package domain;

import static org.assertj.core.api.Assertions.*;
import static util.parser.DateTimeParser.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class CrewTest {

    @Nested
    @DisplayName("크루 출석 기록 생성 테스트")
    class createCrewRecordTest {

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

            assertThat(crew.findRecordByDate(date1)).isEqualTo(
                new DailyRecord(date1.getDayOfWeek(), time1));
            assertThat(crew.findRecordByDate(date2)).isEqualTo(
                new DailyRecord(date2.getDayOfWeek(), time2));
        }
    }
}