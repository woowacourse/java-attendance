package domain;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewGroupTest {
    private final CrewGroup crewGroup = new CrewGroup();
    private final Attendances specificAttendances = new Attendances(
            List.of(new Attendance(LocalDateTime.of(2024, 12, 2, 10, 3))));

    @BeforeEach
    void makeTestCrewGroup() {
        crewGroup.add("민지", new Attendances(List.of(new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)))));
        crewGroup.add("아마", specificAttendances);
        crewGroup.add("가콩", new Attendances(List.of(new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)))));
        crewGroup.add("김수한무", new Attendances(List.of(new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)))));
        crewGroup.add("거북이", new Attendances(List.of(new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)))));
    }

    @ParameterizedTest
    @DisplayName("크루 이름이 없으면 예외가 발생합니다.")
    @ValueSource(strings = {"하하", "호호", "안녕", "제임스 하든"})
    void invalidCrewNameTest(String value) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> crewGroup.validateCrewName(value));
    }

    @Test
    @DisplayName("특정 크루의 출석을 불러오는지 확인합니다.")
    void getSpecificAttendancesTest() {
        String name = "아마";
        Assertions.assertEquals(specificAttendances, crewGroup.getSpecificAttendances(name));
    }
}