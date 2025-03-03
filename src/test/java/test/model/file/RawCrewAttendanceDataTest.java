package test.model.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import model.file.RawCrewAttendanceData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RawCrewAttendanceDataTest {

    @DisplayName("출석 데이터를 문자열로 받아서 키값이 크루 이름인 맵으로 저장한다.")
    @Test
    void success_createUniqueCrewNames() {
        List<String> rawData = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07",
                "빙봉,2024-12-12 11:11",
                "이든,2024-12-12 10:06",
                "짱수,2024-12-12 10:00"
        );

        RawCrewAttendanceData data = RawCrewAttendanceData.from(rawData);

        assertThat(data.findAllCrewNames()).containsAll(List.of(
                "쿠키", "빙봉", "빙티", "이든", "짱수"
        ));
    }

    @DisplayName("출석 데이터를 문자열로 받아서 밸류값이 크루의 출석 정보 리스트인 맵으로 저장한다.")
    @Test
    void success_saveDateTimeToCrewName() {
        List<String> rawData = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "빙티,2024-12-13 10:07",
                "이든,2024-12-13 10:07",
                "빙봉,2024-12-12 11:11",
                "이든,2024-12-12 10:06",
                "짱수,2024-12-12 10:00"
        );

        RawCrewAttendanceData data = RawCrewAttendanceData.from(rawData);
        Map<String, List<LocalDateTime>> mapData = data.getData();

        //then
        assertThat(mapData.get("쿠키")).containsAll(List.of(
                LocalDateTime.of(2024, 12, 13, 10, 8)
        ));
        assertThat(mapData.get("빙봉")).containsAll(List.of(
                LocalDateTime.of(2024, 12, 13, 10, 7),
                LocalDateTime.of(2024, 12, 12, 11, 11)
        ));
        assertThat(mapData.get("빙티")).containsAll(List.of(
                LocalDateTime.of(2024, 12, 13, 10, 7)
        ));
        assertThat(mapData.get("이든")).containsAll(List.of(
                LocalDateTime.of(2024, 12, 13, 10, 7),
                LocalDateTime.of(2024, 12, 12, 10, 6)
        ));
        assertThat(mapData.get("짱수")).containsAll(List.of(
                LocalDateTime.of(2024, 12, 12, 10, 0)
        ));

    }
}
