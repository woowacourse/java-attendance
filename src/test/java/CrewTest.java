import static domain.AttendTime.ABSENT;
import static domain.AttendTime.ATTENDED;
import static domain.AttendTime.LATE;
import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendTime;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("출석 데이터를 파싱한다.")
    @Test
    void test2() {
        String[] crew1 = {"쿠키", "2024-12-13 10:08"};
        Crew parsedCrew1 = new Crew(crew1[0], crew1[1]);

        assertThat(parsedCrew1.getName()).isEqualTo("쿠키");
        assertThat(parsedCrew1.getAttendTimes().get(0).getAttendTime().toString()).isEqualTo("2024-12-13T10:08");
    }

    @DisplayName("출석 데이터를 추가한다.")
    @Test
    void test3() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.addAttendTime("2024-12-14 10:09");

        assertThat(crew.getAttendTimes().size()).isEqualTo(2);
    }

    @DisplayName("지각인지 확인한다.")
    @Test
    void test4() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        var i = crew.attend("2024-12-13 10:06");

        assertThat(i).isEqualTo(LATE);
    }

    @DisplayName("결석인지 확인한다.")
    @Test
    void test5() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        var i = crew.attend("2024-12-13 10:31");

        assertThat(i).isEqualTo(ABSENT);
    }

    @DisplayName("월요일 출석인지 확인한다.")
    @Test
    void test6() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        var i = crew.attend("2024-12-13 09:59");

        assertThat(i).isEqualTo(ATTENDED);
    }

    @DisplayName("화~금요일 출석 데이터를 찾는다.")
    @Test
    void test7() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        var i = crew.attend("2024-12-16 12:59");

        assertThat(i).isEqualTo(ATTENDED);
    }

    @DisplayName("해당 날짜의 출석 데이터를 찾는다.")
    @Test
    void test8() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        AttendTime attendTime = crew.findAttendTimeByDate(13);

        assertThat(attendTime.getAttendTime().getDayOfMonth()).isEqualTo(13);
    }

}
