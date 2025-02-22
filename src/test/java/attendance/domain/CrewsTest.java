package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CrewsTest {
    @DisplayName("기능: 크루 목록 초기화 및 이름으로 조회")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void initAndFindCrews(List<List<String>> attendanceRecords) {
        Crews crews = new Crews();
        crews.initCrews(attendanceRecords);

        assertThat(crews.getCrews().size()).isEqualTo(4);
        assertThat(crews.findCrew("빙티")).isEqualTo(new Crew("빙티"));
    }

    @DisplayName("예외: 존재하지 않는 크루 이름 조회에 대한 처리")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void findCrewsByName(List<List<String>> attendanceRecords) {
        Crews crews = new Crews();
        crews.initCrews(attendanceRecords);

        assertThatThrownBy(() -> crews.findCrew("초코"))
                .isInstanceOf(IllegalStateException.class);
    }

    private static Stream<Arguments> provideAttendanceInstances() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                List.of("쿠키", "2025-02-19 10:34"),
                                List.of("쿠키", "2025-02-18 10:00"),
                                List.of("빙봉", "2025-02-18 10:01"),
                                List.of("쿠키", "2025-02-17 13:03"),
                                List.of("빙티", "2025-02-17 13:04"),
                                List.of("쿠키", "2025-02-14 10:02"),
                                List.of("쿠키", "2025-02-13 10:07"),
                                List.of("짱수", "2025-02-13 10:08")
                        )
                )
        );
    }
}
