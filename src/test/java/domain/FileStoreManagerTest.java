package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class FileStoreManagerTest {
    Map<String, Attendance> expected = new HashMap<>();

    LocalDate date = LocalDate.of(2024, 12, 13);
    Attendance 쿠키_출석 = new ExistAttendance(date, LocalTime.of(10, 8));
    Attendance 빙봉_출석 = new ExistAttendance(date, LocalTime.of(10, 7));
    Attendance 빙티_출석 = new ExistAttendance(date, LocalTime.of(10, 7));
    Attendance 이든_출석 = new ExistAttendance(date, LocalTime.of(10, 7));

    @BeforeEach
    void setup() {
        expected.putAll(Map.of(
                "쿠키", 쿠키_출석,
                "빙봉", 빙봉_출석,
                "빙티", 빙티_출석,
                "이든", 이든_출석
        ));
    }

    @DisplayName("주어진 파일의 내용에 알맞게 크루 정보를 저장할 수 있다.")
    @Test
    void test1() {
        // given
        CrewAttendanceStorage storage = CrewAttendanceStorage.init();
        FileStoreManager fileStoreManager = new FileStoreManager(storage);

        // when
        fileStoreManager.save("src/main/resources/test_attendances.csv");

        // then
        for (String crew : expected.keySet()) {
            Attendance attendance = storage.findAttendance(crew, date);
            Attendance expected = this.expected.get(crew);
            assertThat(attendance.getTime()).isEqualTo(expected.getTime());
        }
    }
}
