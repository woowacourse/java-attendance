import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendTime;
import domain.Crew;
import domain.Crews;
import domain.December;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Test
    void test1() {

        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        Crew crew = crews.findCrew("폰트");

        assertThat(crew.getName()).isEqualTo("폰트");
    }

    @Test
    void test2() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));
        Crew crew = crews.findCrew("슬링키");

        assertThat(crew.getName()).isEqualTo("슬링키");
    }

    @Test
    void test3() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        crews.initializeAttendTime("슬링키", "2024-12-09 13:03");

        assertThat(crews.findCrew("슬링키").getAttendTimes().size())
                .isEqualTo(2);
    }

    @Test
    void test4() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        crews.initializeAttendTime("포비", "2024-12-09 13:03");

        assertThat(crews.getCrews().size())
                .isEqualTo(3);
    }

    @Test
    void test5() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));
        AttendTime attendTime = crews.deleteAttendance("슬링키", 9);

        assertThat(attendTime.getAttendTime().getHour()).isEqualTo(13);
        assertThat(attendTime.getAttendTime().getMinute()).isEqualTo(3);
    }

    @Test
    void test6() {
        System.out.println(December.getWeekDays());
    }

    @Test
    void test7() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03"));

        List<Crew> dismissalCrews = crews.getDangerousCrews("제적");

        assertThat(dismissalCrews.size()).isEqualTo(2);
    }

    @Test
    void test8() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03", "포비,2024-12-09 13:03"));

        List<Crew> dismissalCrews = crews.getDangerousCrews("제적");

        assertThat(dismissalCrews.size()).isEqualTo(3);
    }

    @Test
    void test9() {
        Crews crews = new Crews(List.of("폰트,2024-12-13 10:08", "슬링키,2024-12-09 13:03", "포비,2024-12-09 13:03"));

        assertThatThrownBy(() -> crews.ifFindNameAddTime("벨로"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
