import static domain.DangerousTarget.DISMISSAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendTime;
import domain.AttendanceStatus;
import domain.Crew;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("크루는 출석을 할 수 있다")
    @Test
    void 크루는_출석을_할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);
        crew.attend(localDateTime);
        assertThat(crew.getAttendTimes().getAttendTimes().size()).isEqualTo(1);
    }

    @DisplayName("크루는 주말이나 휴일에 출석을 할 수 없다")
    @Test
    void 크루는_주말이나_휴일에_출석을_할_수_없다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 15, 10, 8);
        assertThatThrownBy(() -> crew.attend(localDateTime)).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말은 출석 할 수 없습니다.");
    }

    @DisplayName("크루는 날짜를 통해 자신의 출석 정보를 찾을 수 있다")
    @Test
    void 크루는_날짜를_통해_자신의_출석_정보를_찾을_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 10, 8);
        crew.attend(localDateTime);
        assertThat(crew.findAttendanceByDate(localDateTime.getDayOfMonth())).isInstanceOf(AttendTime.class);
    }

    @DisplayName("크루는 자신의 출석 기록을 변경 할 수 있다")
    @Test
    void 크루는_자신의_출석_기록을_변경_할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 8);
        crew.attend(localDateTime);
        crew.changeAttendanceTime(16, LocalTime.of(13, 6));
        assertThat(crew.findAttendanceByDate(16).checkAttendanceStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("크루는 자신의 출석 기록을 수정할 수 있다")
    @Test
    void 크루는_자신의_출석_기록을_수정할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 8);
        crew.attend(localDateTime);
        crew.changeAttendanceTime(16, LocalTime.of(14, 6));
        assertThat(crew.findAttendanceByDate(16).getLocalTime()).isEqualTo(LocalTime.of(14, 6));
    }

    @DisplayName("크루는 자신이 제적 대상자인지 확인 할 수 있다")
    @Test
    void 크루는_자신이_제적_대상자인지_확인할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 8);
        crew.attend(localDateTime);
        assertThat(crew.isDismissalCrew()).isTrue();
    }

    @DisplayName("크루는 자신의 출석 횟수를 구할 수 있다")
    @Test
    void 크루는_자신의_출석_횟수를_구할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 3, 8);
        crew.attend(localDateTime);
        assertThat(crew.getCrewAttendedCount()).isEqualTo(1);
    }

    @DisplayName("크루는 자신의 지각 횟수를 구할 수 있다")
    @Test
    void 크루는_자신의_지각_횟수를_구할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 8);
        crew.attend(localDateTime);
        assertThat(crew.getCrewLateCount()).isEqualTo(1);
    }

    @DisplayName("크루는 자신의 결석 횟수를 구할 수 있다")
    @Test
    void 크루는_자신의_결석_횟수를_구할_수_있다() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 16, 13, 38);
        crew.attend(localDateTime);
        assertThat(crew.getCrewAbsentCount()).isEqualTo(11);
    }

    @DisplayName("크루는 자신의 제적 대상자 상태를 확인 할 수 있다")
    @Test
    void canCheckCrewDismissalStatus() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 13, 38);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 3, 13, 38);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 4, 13, 38);
        crew.attend(localDateTime1);
        crew.attend(localDateTime2);
        crew.attend(localDateTime3);
        assertThat(crew.getDismissalStatus()).isEqualTo(DISMISSAL);
    }

    @DisplayName("크루는 자신의 제적 대상자인지를 체크할 수 있다")
    @Test
    void canCheckCrewIsDismissal() {
        Crew crew = new Crew("슬링키");
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 13, 38);
        LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 3, 13, 38);
        LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 4, 13, 38);
        crew.attend(localDateTime1);
        crew.attend(localDateTime2);
        crew.attend(localDateTime3);
        assertThat(crew.isDismissalCrew()).isEqualTo(true);
    }
}
