package domain;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import util.parser.DateTimeParser;

@Nested
public class CrewTest {

    @Nested
    @DisplayName("크루 생성 테스트")
    class createCrewTest {

        @Test
        @DisplayName("크루별 데이터를 날짜와 시간으로 구분할 수 있다.")
        void separateCrew() {
            Crew crew = new Crew();
            List<LocalDateTime> crewRecords = List.of(
                DateTimeParser.parseStringToDateTime("2024-12-04 10:08"),
                DateTimeParser.parseStringToDateTime("2024-12-05 10:02")
            );
            crew.initializeDailyRecords(crewRecords);

            LocalDate date1 = DateTimeParser.parseStringToDate("2024-12-04");
            LocalDate date2 = DateTimeParser.parseStringToDate("2024-12-05");

            assertThat(crew.findRecordByDate(date1)).isEqualTo(DailyRecord.class);
            assertThat(crew.findRecordByDate(date2)).isEqualTo(DailyRecord.class);
        }
    }
}
