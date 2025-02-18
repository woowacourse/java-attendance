import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceStoreTest {
    AttendanceStoreService attendanceStoreService = new AttendanceStoreService();

    @Test
    void test2() {
        List<Object> transferedInformation = attendanceStoreService.parse("쿠키,2024-12-13 10:08");
        assertThat(transferedInformation.get(0)).isInstanceOf(Crew.class);
        assertThat(transferedInformation.get(1)).isInstanceOf(LocalDateTime.class);

        LocalDateTime time = (LocalDateTime) transferedInformation.get(1);
        assertThat(time.getYear()).isEqualTo(2024);
        assertThat(time.getMonthValue()).isEqualTo(12);
        assertThat(time.getDayOfMonth()).isEqualTo(13);
        assertThat(time.getHour()).isEqualTo(10);
        assertThat(time.getMinute()).isEqualTo(8);
    }

    @DisplayName("파일 내용을 기반으로 객체를 생성할 수 있다.")
    @Test
    void test3() {
        // given

        // when
        List<String> lines = attendanceStoreService.load("src/main/resources/attendances.csv");

        // then
        assertThat(lines.size()).isEqualTo(41);
    }
}
