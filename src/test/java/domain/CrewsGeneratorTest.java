package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsGeneratorTest {

    //쿠키,2024-12-13 10:08
    @Test
    @DisplayName("csv파일을 읽어 크루 출석 기록 초기화 한다")
    void test1() {
        //given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);
        final AttendanceRecord attendanceRecord = AttendanceRecord.of(localDateTime);
        //when
        final Map<String, Crew> crews = CrewsGenerator.generate();
        final Set<String> crewsName = crews.keySet();

        //then
        assertAll(
                () -> assertThat(crewsName).contains("쿠키")
                        .contains("빙티")
                        .contains("빙봉")
                        .contains("이든")
                        .contains("짱수"),
                () -> assertThat(crews.get("쿠키").getAttendanceMap()).containsEntry(localDateTime.toLocalDate(),
                        attendanceRecord),
                () -> assertThat(crews.get("빙봉").getAttendanceMap()).containsEntry(localDateTime.toLocalDate(),
                        attendanceRecord),
                () -> assertThat(crews.get("빙티").getAttendanceMap()).containsEntry(localDateTime.toLocalDate(),
                        attendanceRecord),
                () -> assertThat(crews.get("이든").getAttendanceMap()).containsEntry(localDateTime.toLocalDate(),
                        attendanceRecord)
        );
    }
}
