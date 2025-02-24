import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.Crews;
import domain.AttendanceStatus;
import domain.Crew;
import domain.StatisticsResult;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    Crews crews = new Crews();

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void attendCrewTest_1() {
        LocalDateTime initialDateAndTime = LocalDateTime.of(2024, 12, 13, 13, 0);
        LocalDateTime attendDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        String name = "빙봉";

        crews.createCrew(name, List.of(initialDateAndTime));
        crews.attendCrew(name, attendDateAndTime);

        assertThat(crews.findByName(name).getAttendanceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("크루 정보를 출석 시간을 저장한다.")
    void attendCrewTest_2() {
        LocalDateTime dateAndTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        LocalDate localDate = dateAndTime.toLocalDate();
        String name = "빙봉";

        crews.createCrew(name, List.of());
        TimeAndStatus timeStatus = crews.attendCrew(name, dateAndTime);

        Crew crew = crews.findByName(name);
        TimeAndStatus expectedTimeStatus = crew.findByDate(localDate);

        assertThat(expectedTimeStatus).isEqualTo(timeStatus);
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void attendCrewTest_3() {
        LocalDateTime initialDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        LocalDateTime attendDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 3);
        String name = "빙봉";

        crews.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            crews.attendCrew(name, attendDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석 상태를 출석에서 지각으로 변경한다.")
    void editCrew_1() {
        String name = "빙봉";
        LocalDateTime initialDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 0);
        LocalDateTime editedDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 6);
        LocalDate localDate = editedDateAndTime.toLocalDate();

        crews.createCrew(name, List.of(initialDateAndTime));
        crews.editCrew(name, editedDateAndTime);

        TimeAndStatus timeAndStatus = findTimeAndStatus(name, localDate);

        assertThat(timeAndStatus.getStatus()).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    @DisplayName("출석하지 않고 수정하는 경우 예외메시지를 출력한다.")
    void editCrew_2() {
        LocalDateTime initialDateAndTime = LocalDateTime.of(2024, 12, 13, 13, 0);
        LocalDateTime editedDateAndTime = LocalDateTime.of(2024, 12, 16, 13, 3);
        String name = "빙봉";

        crews.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            crews.editCrew(name, editedDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private TimeAndStatus findTimeAndStatus(String name, LocalDate localDate) {
        Crew crew = crews.findByName(name);
        return crew.findByDate(localDate);
    }

    @DisplayName("제적 위험자를 기준에 맞게 정렬한다.")
    @Test
    void findWarningCrewsTest_1() {
        List<LocalDateTime> testRecords1 = List.of(LocalDateTime.of(2024, 12, 2, 13, 6), // 지각
            LocalDateTime.of(2024, 12, 3, 9, 7), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 8), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 9),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 10),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 40),// 결석
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        List<LocalDateTime> testRecords2 = List.of(LocalDateTime.of(2024, 12, 2, 13, 8), // 지각
            LocalDateTime.of(2024, 12, 3, 10, 5), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 7),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 6),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 6),// 지각
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        crews.createCrew("이든", testRecords1); // 면담 대상자
        crews.createCrew("빙봉", testRecords2); // 면담 대상자
        LocalDate nowDate = LocalDate.of(2024, 12, 11);
        Map<String, StatisticsResult> sortedResult = crews.findWarningCrews(nowDate);
        List<String> names = new ArrayList<>(sortedResult.keySet());

        assertThat(names.get(0)).isEqualTo("이든");
        assertThat(names.get(1)).isEqualTo("빙봉");
    }
}
