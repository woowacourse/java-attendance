package attendance.loader;

import attendance.domain.AttendanceHistory;
import attendance.domain.EducationDayPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceAssemblerTest {

    @DisplayName("실제 csv 파일의 데이터가 올바르게 변환된다.")
    @Test
    void test() {
        // given
        EducationDayPolicy policy = new EducationDayPolicy(Set.of(LocalDate.of(2024, 12, 25)));
        AttendanceAssembler assembler = new AttendanceAssembler(new AttendancesLoader(), policy);

        // when
        Map<String, AttendanceHistory> assembleDatas = assembler.assembleDatas();

        // then
        AttendanceHistory history = assembleDatas.get("빙티");
        Assertions.assertThat(history.getRecords()).hasSize(7);
    }

    @DisplayName("여러 크루의 데이터가 올바르게 변환된다.")
    @Test
    void testMultipleCrewDataConversion() {
        // given
        Map<String, List<LocalDateTime>> rawDatas = new HashMap<>();
        rawDatas.put("쿠키", Arrays.asList(
                LocalDateTime.of(2024, 12, 13, 10, 8),
                LocalDateTime.of(2024, 12, 17, 10, 10)
        ));
        rawDatas.put("빙봉", Arrays.asList(
                LocalDateTime.of(2024, 12, 13, 10, 7),
                LocalDateTime.of(2024, 12, 17, 10, 5),
                LocalDateTime.of(2024, 12, 18, 10, 3)
        ));
        rawDatas.put("빙티", List.of(
                LocalDateTime.of(2024, 12, 13, 10, 7)
        ));

        AttendancesLoader fakeLoader = new AttendancesLoader() {
            @Override
            public void load() {
                // do nothing
            }

            @Override
            public Map<String, List<LocalDateTime>> getRawDatas() {
                return rawDatas;
            }
        };

        EducationDayPolicy policy = new EducationDayPolicy(Set.of(LocalDate.of(2024, 12, 25)));
        AttendanceAssembler assembler = new AttendanceAssembler(fakeLoader, policy);

        // when
        Map<String, AttendanceHistory> assembleDatas = assembler.assembleDatas();

        // then
        Assertions.assertThat(assembleDatas.get("쿠키").getRecords()).hasSize(2);
        Assertions.assertThat(assembleDatas.get("빙봉").getRecords()).hasSize(3);
        Assertions.assertThat(assembleDatas.get("빙티").getRecords()).hasSize(1);
    }

}