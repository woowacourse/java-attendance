package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("크루 그룹 테스트")
class CrewGroupTest {

    @DisplayName("닉네임을 이용해 크루를 찾을 수 있다.")
    @Test
    void findCrewByNickname() {
        String nickname = "포비";
        Crew crew = new Crew(nickname);
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        assertThat(crewGroup.findCrewByNickname(nickname))
                .isEqualTo(new Crew("포비"));
    }

    @DisplayName("찾으려 하는 닉네임이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNotExistNickname() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        String notExistNickname = "네오";
        Assertions.assertThatThrownBy(() -> crewGroup.findCrewByNickname(notExistNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("네오은(는) 등록되지 않은 닉네임입니다.");
    }

    @DisplayName("특정 크루가 속해있는지 알 수 있다.")
    @Test
    void contains() {
        //given
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        //when
        boolean result = crewGroup.contains(crew);

        //then
        assertThat(result).isTrue();
    }

    @DisplayName("모든 크루의 출석 결과를 생성한다.")
    @Test
    void createAttendanceResultOfAllCrewUntilDate() {
        //given
        Crew pobi = new Crew("포비");
        Crew neo = new Crew("네오");
        CrewGroup crewGroup = new CrewGroup(Set.of(pobi, neo));
        AttendanceBook attendanceBook = new AttendanceBook(crewGroup, List.of(
                new Attendance(pobi, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(neo, LocalDateTime.of(2024, 12, 2, 10, 1))
        ));

        //when
        List<AttendanceResult> attendanceResults = crewGroup.createAttendanceResultOfAllCrewUntilDate(
                attendanceBook, LocalDate.of(2024, 12, 3));

        //then
        assertThat(attendanceResults).containsAll(List.of(
                new AttendanceResult(pobi,
                        Map.of(
                                AttendanceType.OK, 1,
                                AttendanceType.ABSENCE, 1
                        ),
                        List.of(
                                new Attendance(pobi, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                Attendance.absent(pobi, LocalDate.of(2024, 12, 3))
                        )
                ),
                new AttendanceResult(neo,
                        Map.of(
                                AttendanceType.OK, 1,
                                AttendanceType.ABSENCE, 1
                        ),
                        List.of(
                                new Attendance(neo, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                Attendance.absent(neo, LocalDate.of(2024, 12, 3))
                        )
                )
        ));
    }
}
