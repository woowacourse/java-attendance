package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import model.Crew;
import model.AttendanceInitializer;
import model.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("파일에서 크루 이름과 출석 데이터를 읽어온다.")
    @Test
    void test0() {
        //given
        String crewInput = """
                쿠키,2024-12-13 10:08
                빙봉,2024-12-13 10:07
                이든,2024-12-13 10:07
                이든,2024-12-12 10:06
                """;

        //when
        List<String> crewNames = AttendanceInitializer.readCrewAndAttendanceData(crewInput);

        //then
        assertThat(crewNames).containsAll(Arrays.asList(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "이든,2024-12-13 10:07",
                "이든,2024-12-12 10:06"
        ));
    }

    @DisplayName("중복 없이 크루 이름을 읽어온다.")
    @Test
    void test1() {
        //given
        List<String> combinedData = List.of(
                "쿠키,2024-12-13 10:08",
                "빙봉,2024-12-13 10:07",
                "이든,2024-12-13 10:07",
                "빙봉,2024-12-12 11:11",
                "빙티,2024-12-12 10:07",
                "이든,2024-12-12 10:06",
                "이든,2024-12-11 10:10"
        );

        //when
        List<String> crewNames = AttendanceInitializer.extractUniqueCrewData(combinedData);

        //then
        assertThat(crewNames).containsExactly("쿠키", "빙봉", "이든", "빙티");
    }

    /**
     * test3과 통합된 기능
     */
//    @DisplayName("닉네임을 바탕으로 크루 객체를 생성한다.")
//    @Test
//    void test2() {
//        //given
//        List<String> crewNames = List.of("쿠키", "빙봉", "빙티", "이든");
//
//        //when
//        List<Crew> crews = CrewGenerator.registerCrew(crewNames);
//
//        //then
//        assertThat(crews).containsAll(Arrays.asList(
//                new Crew("쿠키"),
//                new Crew("빙봉"),
//                new Crew("빙티"),
//                new Crew("이든")
//        ));
//    }
    @DisplayName("크루 객체들을 포장한 객체를 생성한다.")
    @Test
    void test3() {
        //given
        List<String> crewNames = List.of("쿠키", "빙봉", "빙티", "이든");

        //when
        Crews crews = Crews.of(crewNames);

        //then
        assertThat(crews).isEqualTo(new Crews(List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        ))); //TODO : isSameAs로 하면 안됨
    }

    @DisplayName("입력한 닉네임에 맞는 크루 정보를 가져온다.")
    @Test
    void test4() {
        //given
        String name = "빙티";
        Crews crews = new Crews(List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        ));

        //when
        Optional<Crew> crew = crews.findCrewByName(name);

        //then
        assertThat(crew.get()).isEqualTo(new Crew(name));
    }

    @DisplayName("크루 이름과 날짜 객체를 입력하면 날짜와 시간을 읽어서 LocalTime 객체를 반환한다.")
    @Test
    void test5() {
        String combinedData = "쿠키,2024-12-13 10:08";

        LocalDateTime attendanceTime = AttendanceInitializer.parseLocalDateTimeFrom(combinedData);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
    }

//    @DisplayName("출석 기록을 읽어서 LocalDateTime 객체로 변환한다.")
//    @Test
//    void test5() {
//        //given
//        String crewInput = """
//                쿠키,2024-12-13 10:08
//                빙봉,2024-12-13 10:07
//                이든,2024-12-13 10:07
//                빙티,2024-12-12 10:07
//                """;
//        Map<String, LocalDateTime> times = AttendanceAdministrator.findAttendanceInfo(crewInput);
//
//        assertThat(times.get("쿠키")).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
//        assertThat(times.get("빙봉")).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 7));
//        assertThat(times.get("이든")).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 7));
//        assertThat(times.get("빙티")).isEqualTo(LocalDateTime.of(2024, 12, 12, 10, 7));
//    }

//    @DisplayName("입력한 날짜에 해당하는 출석 기록이 있는지 확인한다.")
//    @Test
//    void test5() {
//        //given
//        Crew crew = new Crew("빙티");
//        LocalDate date = LocalDate.of(2024, 12, 13);
//        LocalTime time = LocalTime.of(10, 0);
//        AttendanceAdministrator.registerAttendance(crew, date, time); //관심사가 2개임
//
//        //when
//        Optional<Attendance> attendance = AttendanceAdministrator.findAttendanceByCrewAndDate(crew, date);
//
//        //then
//        assertThat()
//
//    }

//    @DisplayName("오늘 날짜와 입력한 시간에 맞는 출석 객체를 등록한다.")
//    @Test
//    void test5() {
//        //given
//        Crew crew = new Crew("빙티");
//
//        Attendance attendance = new Attendance(); //이미 출석했는지 확인해야 함
//
//    }
}
