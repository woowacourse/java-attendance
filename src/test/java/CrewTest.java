import domain.AttendTime;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

public class CrewTest {

    @DisplayName("크루의 출석 시간 기록을 확인할 수 있다")
    @Test
    void test2() {
        String[] crew1 = {"쿠키", "2024-12-13 10:08"};
        Crew parsedCrew1 = new Crew(crew1[0], crew1[1]);

        assertThat(parsedCrew1.getName()).isEqualTo("쿠키");
        assertThat(parsedCrew1.getAttendTimes().get(0).getAttendTime().toString()).isEqualTo("2024-12-13T10:08");
    }

    @DisplayName("크루의 출석기록을 추가할 수 있다")
    @Test
    void test3() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.addAttendTime("2024-12-13 10:09");

        assertThat(crew.getAttendTimes().size()).isEqualTo(2);
    }

    @DisplayName("크루의 출석이 지각인지를 판별 할 수 있다")
    @Test
    void test4() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 10:06");
        assertThat(i).isEqualTo(LATE.getType());
    }

    @DisplayName("크루의 출석이 결석인지를 판별 할 수 있다")
    @Test
    void test5() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 10:31");
        assertThat(i).isEqualTo(ABSENT.getType());
    }

    @DisplayName("크루의 출석이 잘 되었는지를 판별 할 수 있다")
    @Test
    void test6() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 09:59");
        assertThat(i).isEqualTo(ATTENDED.getType());
    }

    @DisplayName("크루의 출석이 잘 기록되었는지 확인 할 수 있다")
    @Test
    void test8() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        AttendTime attendTime = crew.findAttendanceByDate(13).orElseThrow(()->new IllegalArgumentException("[Error] 이 날은 출석 기록이 없습니다."));

        assertThat(attendTime.getAttendTime().getDayOfMonth()).isEqualTo(13);
    }

    @DisplayName("크루의 출석 기록을 지울 수 있다")
    @Test
    void test9() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.deleteAttendance(13);

        assertThat(crew.getAttendTimes().size()).isEqualTo(0);
    }

}
