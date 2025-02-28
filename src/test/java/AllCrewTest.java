import static org.assertj.core.api.Assertions.assertThat;

import domain.AllCrew;
import domain.Attendance;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AllCrewTest {
    @DisplayName("크루 출석 정보 등록 테스트 ")
    @Test
    void test1() {
        AllCrew allCrew = new AllCrew();
        String name = "띠용";
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0));
        allCrew.addCrewInfoWithNameAndAttendance(name, attendance);

        Assertions.assertAll(
                () -> assertThat(allCrew.containsCrewName(name)).isTrue(),
                () -> assertThat(allCrew.containsCrewName("없는이름")).isFalse()
        );
    }

}
