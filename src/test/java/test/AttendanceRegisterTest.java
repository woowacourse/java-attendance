package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.DuplicatedAttendanceRegistrationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import model.Attendance;
import model.AttendanceStatus;
import model.AttendanceBook;
import model.Crew;
import model.AttendanceInitializer;
import model.Crews;
import model.December;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRegisterTest {

//    @DisplayName("파일에서 크루 이름과 출석 데이터를 읽어온다.")
//    @Test
//    void test0() {
//        //given
//        String crewInput = """
//                쿠키,2024-12-13 10:08
//                빙봉,2024-12-13 10:07
//                이든,2024-12-13 10:07
//                이든,2024-12-12 10:06
//                """;
//
//        //when
//        List<String> crewNames = AttendanceInitializer.readCrewAndAttendanceData(crewInput);
//
//        //then
//        assertThat(crewNames).containsAll(Arrays.asList(
//                "쿠키,2024-12-13 10:08",
//                "빙봉,2024-12-13 10:07",
//                "이든,2024-12-13 10:07",
//                "이든,2024-12-12 10:06"
//        ));
//    }

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

    /**
     * test5_1과 통합된 기능
     */
//    @DisplayName("크루 이름과 날짜 객체를 입력하면 날짜와 시간을 읽어서 LocalTime 객체를 반환한다.")
//    @Test
//    void test5() {
//        String combinedData = "쿠키,2024-12-13 10:08";
//
//        LocalDateTime attendanceTime = AttendanceInitializer.parseAttendanceFrom(combinedData);
//
//        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
//    }
    @DisplayName("크루 객체 별로 출석 객체를 빈 객체로 초기화한다.")
    @Test
    void test5_0() {
        //given
        Crew crew = new Crew("쿠키");
        List<Crew> crewsInput = List.of(crew);
        Crews crews = new Crews(crewsInput);

        //when
        Map<Crew, AttendanceBook> attendancesPerCrew = AttendanceInitializer.initializeAttendanceOf(crews);

        //then
        assertThat(attendancesPerCrew.get(crew)).isEqualTo(new AttendanceBook(
                IntStream.range(1, 32)
                        .mapToObj(date -> new Attendance(LocalDate.of(2024, 12, date), LocalTime.of(0, 0)))
                        .toList()
        ));
    }

    @DisplayName("크루 이름과 날짜 객체를 입력하면 날짜와 시간을 읽어서 Attendance 객체를 반환한다.")
    @Test
    void test5_1() {
        String combinedData = "쿠키,2024-12-13 10:08";

        LocalDateTime dateTime = AttendanceInitializer.parseAttendanceFrom(combinedData);

        assertThat(dateTime).isEqualTo(
                LocalDateTime.of(2024, 12, 13, 10, 8)
        );
    }

    @DisplayName("새로운 출석 객체를 입력하면 크루에 맞는 출석 객체를 갱신한다.")
    @Test
    void test5_2() {
        Crew crew = new Crew("빙티");
        Crews crews = new Crews(List.of(crew));
        Map<Crew, AttendanceBook> initializedAttendances = AttendanceInitializer.initializeAttendanceOf(crews);

        //when
        AttendanceBook attendanceBookOfCrew = initializedAttendances.get(crew);
        Attendance attendance = attendanceBookOfCrew.register(LocalDate.of(2024, 12, 14),
                LocalTime.of(10, 10));

        assertThat(attendance).isEqualTo(new Attendance(LocalDate.of(2024, 12, 14),
                LocalTime.of(10, 10)));
        //findByDate도 하면 좋을듯
    }

    @DisplayName("출석 기록을 읽어서 Attendances 객체의 필드를 갱신한다.")
    @Test
    void test5_3() {
        //given
        Crew crew1 = new Crew("쿠키");
        Crew crew2 = new Crew("이든");

        List<String> combinedData = List.of(
                "쿠키,2024-12-13 10:08",
                "이든,2024-12-12 11:11"
        );
        Crews crews = new Crews(List.of(crew1, crew2));
        Map<Crew, AttendanceBook> defaultAttendances = AttendanceInitializer.initializeAttendanceOf(crews);

        //when
        AttendanceInitializer.updateAttendances(crews, combinedData, defaultAttendances);

        //then
        assertThat(defaultAttendances.get(crew1)
                .findByDate(LocalDate.of(2024, 12, 13)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 8)));
        assertThat(defaultAttendances.get(crew2)
                .findByDate(LocalDate.of(2024, 12, 12)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(11, 11)));
    }

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

    @DisplayName("날짜와 시간을 입력하면 이에 맞는 출석 상태를 반환한다.")
    @Test
    void test6_0() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime time = LocalTime.of(10, 5);

        //then, when
        assertThat(AttendanceStatus.findByAttendanceTime(date, time)).isEqualTo(AttendanceStatus.NORMAL);
    }

    @DisplayName("출석 객체가 시작시간 5분 이내 출석이면 출석 상태를 반환한다.")
    @Test
    void test6() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 5));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.NORMAL);
    }

    @DisplayName("출석 객체가 시작시간 30분 이내 출석이면 지각 상태를 반환한다.")
    @Test
    void test6_1() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 6));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("출석 객체가 시작시간 5분 이내 출석이면 출석 상태를 반환한다.")
    @Test
    void test6_2() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 31));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @DisplayName("출결 상태 반환시, 월요일은 13시 시작으로 처리한다.")
    @Test
    void test6_3() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 5));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.NORMAL);
    }

    @DisplayName("등교일이 아닌 경우에 예외를 반환한다. - 공휴일")
    @Test
    void test7() {
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("등교일이 아닌 경우에 예외를 반환한다. - 주말")
    @Test
    void test7_1() {
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 14)))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> December.validateHoliday(LocalDate.of(2024, 12, 15)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("입력한 날짜에 출석객체가 있는 경우, 예외를 반환한다.")
    @Test
    void test8() {
        Crew crew = new Crew("빙티");
        Crews crews = new Crews(List.of(crew));
        Map<Crew, AttendanceBook> initializedAttendances = AttendanceInitializer.initializeAttendanceOf(crews);
        AttendanceBook attendanceBook = initializedAttendances.get(crew);

        attendanceBook.register(LocalDate.of(2024, 12, 13), LocalTime.of(10, 5));

        assertThatThrownBy(() -> attendanceBook.register(LocalDate.of(2024, 12, 13), LocalTime.of(11, 11)))
                .isInstanceOf(DuplicatedAttendanceRegistrationException.class);
    }
}