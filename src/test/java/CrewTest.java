import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendTime;
import domain.Crew;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void test2() {
        String[] crew1 = {"쿠키", "2024-12-13 10:08"};
        Crew parsedCrew1 = new Crew(crew1[0], crew1[1]);

        assertThat(parsedCrew1.getName()).isEqualTo("쿠키");
        assertThat(parsedCrew1.getAttendTimes().get(0).getAttendTime().toString()).isEqualTo("2024-12-13T10:08");
    }

    @Test
    void test3() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.addAttendTime("2024-12-13 10:09");

        assertThat(crew.getAttendTimes().size()).isEqualTo(2);
    }

    @Test
    void test4() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 10:06");
        assertThat(i).isEqualTo("지각");
    }

    @Test
    void test5() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 10:31");
        assertThat(i).isEqualTo("결석");
    }

    @Test
    void test6() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-13 09:59");
        assertThat(i).isEqualTo("출석");
    }

    @Test
    void test7() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");
        var i = crew.attend("2024-12-16 12:59");
        assertThat(i).isEqualTo("출석");
    }

    @Test
    void test8() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        AttendTime attendTime = crew.findAttendanceByDate(13);

        assertThat(attendTime.getAttendTime().getDayOfMonth()).isEqualTo(13);
    }

    @Test
    void test9() {
        Crew crew = new Crew("폰트", "2024-12-13 10:08");

        crew.deleteAttendance(13);

        assertThat(crew.getAttendTimes().size()).isEqualTo(0);
    }

}
