package attendance.loader;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceHistory;
import attendance.domain.Crew;
import attendance.domain.Crews;
import attendance.domain.EducationDayPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        AttendanceBook attendanceBook = assembler.getAttendanceBook();
        Crews crews = assembler.getCrews();

        // then
        AttendanceHistory history = attendanceBook.getHistoryByCrew(new Crew("빙티"));
        assertThat(history.getRecords()).hasSize(7);
        assertThat(crews.getAllCrews()).hasSize(5);
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

        AttendanceBook attendanceBook = getCrewAttendanceHistoryMap(
                rawDatas);

        // then
        assertThat(attendanceBook.getHistoryByCrew(new Crew("쿠키")).getRecords()).hasSize(2);
        assertThat(attendanceBook.getHistoryByCrew(new Crew("빙봉")).getRecords()).hasSize(3);
        assertThat(attendanceBook.getHistoryByCrew(new Crew("빙티")).getRecords()).hasSize(1);
    }

    private AttendanceBook getCrewAttendanceHistoryMap(Map<String, List<LocalDateTime>> rawDatas) {
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

        return assembler.getAttendanceBook();
    }

}