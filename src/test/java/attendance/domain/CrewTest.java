package attendance.domain;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.LATENESS;
import static attendance.domain.AttendanceStatus.PRESENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("크루원")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CrewTest {


    @Test
    void Crew_객체를_생성한다() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        Crew crew = new Crew("빙봉");

        assertThatCode(() -> crew.attend(localDateTime)).doesNotThrowAnyException();
    }

    @Test
    void 출석_정보를_저장한다() {
        String crewName = "빙봉";
        Crew crew = new Crew(crewName);

        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> crew.attend(localDateTime)).doesNotThrowAnyException();
    }

    @Test
    void 출석_저장_시_출석_기록이_존재하면_예외가_발생한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        String crewName = "빙봉";
        Crew crew = new Crew(crewName);
        crew.attend(attendanceTime);
        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 3);

        assertThatThrownBy(() -> crew.attend(newAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @Test
    void 출석_정보를_수정하면_이전_출석_시간을_반환한다() {
        String crewName = "빙봉";
        Crew crew = new Crew(crewName);

        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 15, 35);
        crew.attend(prevAttendanceTime);

        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        Attendance prevAttendance = crew.modify(newAttendanceTime);

        assertThat(prevAttendance.getHour()).isEqualTo(15);
        assertThat(prevAttendance.getMinute()).isEqualTo(35);
    }

    @Test
    void 수정_시_해당_날짜에_출석_기록이_없으면_예외가_발생한다() {
        String crewName = "빙봉";
        Crew crew = new Crew(crewName);

        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> crew.modify(newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @Test
    void 출석_지각_결석_횟수를_반환한다() {
        LocalDateTime present1 = LocalDateTime.of(2024, 12, 3, 9, 58);
        LocalDateTime present2 = LocalDateTime.of(2024, 12, 4, 10, 2);
        LocalDateTime present3 = LocalDateTime.of(2024, 12, 6, 10, 1);
        LocalDateTime lateness1 = LocalDateTime.of(2024, 12, 5, 10, 6);

        String crewName = "빙봉";
        Crew crew = new Crew(crewName);
        crew.attend(present1);
        crew.attend(present2);
        crew.attend(present3);
        crew.attend(lateness1);

        Map<AttendanceStatus, Integer> attendanceStatuses = crew.countAttendanceStatus(6);

        assertThat(attendanceStatuses.get(PRESENT)).isEqualTo(2);
        assertThat(attendanceStatuses.get(LATENESS)).isEqualTo(1);
        assertThat(attendanceStatuses.get(ABSENCE)).isEqualTo(1);
    }
}
