import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CrewsTest {
    @DisplayName("크루들의 출석 데이터를 받아와 크루를 생성할 수 있다")
    @Test
    void test1() {
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        assertThat(crews.getCrews().size()).isGreaterThan(0);
    }

    @DisplayName("크루들의 출석 데이터를 받아올때 한 크루가 여러번 들어가지 않는다")
    @Test
    void test2() {
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        assertThat(crews.getCrews().stream().filter(crew -> crew.getNickname().equals("쿠키")).toList().size()).isEqualTo(1);
    }

    @DisplayName("크루의 이름을 통해서 크루를 찾을 수 있다")
    @Test
    void test3(){
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        Crew crew = crews.findCrewByNickname("쿠키").orElseThrow(()->new IllegalArgumentException("없는 닉네임입니다"));
        assertThat(crew.getNickname()).isEqualTo("쿠키");
    }
    @DisplayName("크루의 이름을 통해서 크루를 찾을 수 있다")
    @Test
    void test4(){
        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
        Crews crews = new Crews(crewAttendanceResource);
        Crew crew = crews.findCrewByNickname("이든").orElseThrow(()->new IllegalArgumentException("없는 닉네임입니다"));
        assertThat(crew.getNickname()).isEqualTo("이든");
    }
//
//    @DisplayName("크루의 이름과 시간이 주어지면 오늘 출석할 수 있다")
//    @Test
//    void test5() {
//        List<String> crewAttendanceResource = CrewAttendanceFileReader.readFile("src/main/resources/attendances.csv");
//        Crews crews = new Crews(crewAttendanceResource);
//        String nickname = "쿠키";
//        String time = "2024-12-16 09:58";
//        crews.addCrewAttendance(nickname, time);
//        assertThat(crews.findCrewByNickname("쿠키")
//                .orElseThrow(() -> new IllegalArgumentException("없는 닉네임입니다."))
//                .getAttendTimes()
//                .stream()
//                .filter(attendTime -> attendTime
//                        .equals(LocalDateTime.of(2024, 12, 16, 9, 58))).count())
//                .isEqualTo(1);
//    }

    @Test
    void test6() {
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate.toString());
    }
}
