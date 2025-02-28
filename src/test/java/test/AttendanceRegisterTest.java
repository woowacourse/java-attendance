package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import model.exception.DuplicatedAttendanceRegistrationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.attendance.Attendance;
import model.admininstration.AttendanceBook;
import model.attendance.AttendanceStatus;
import model.attendance.AttendanceHistory;
import model.attendance.Crew;
import model.admininstration.Crews;
import model.date.December;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRegisterTest {

    @DisplayName("크루 객체들을 포장한 객체를 생성한다.")
    @Test
    void test3() {
        //given
        List<String> combinedData = List.of(
                "쿠키", "빙봉", "이든", "빙봉", "빙티", "이든", "이든"
        );

        //when
        Crews crews = Crews.from(combinedData);

        //then
        assertThat(crews).isEqualTo(new Crews(List.of(
                new Crew("쿠키"),
                new Crew("빙봉"),
                new Crew("빙티"),
                new Crew("이든")
        )));
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

    @DisplayName("크루 객체 별로 출석 객체를 빈 객체로 초기화한다.")
    @Test
    void test5_0() {
        //given
        Crew crew = new Crew("쿠키");
        Crews crews = new Crews(List.of(crew));

        //when
        AttendanceBook attendanceBook = AttendanceBook.from(crews);

        //then
        assertThat(attendanceBook).isEqualTo(new AttendanceBook(new HashMap<>(Map.of(
                crew, new AttendanceHistory()
        ))));
    }

    /**
     * Disabled
     * private으로 전환
     */
//    @DisplayName("크루 이름과 날짜 객체를 입력하면 날짜와 시간을 읽어서 Attendance 객체를 반환한다.")
//    @Test
//    void test5_1() {
//        String combinedData = "쿠키,2024-12-13 10:08";
//
//        LocalDateTime dateTime = ExistingAttendances.parseAttendanceData(combinedData);
//
//        assertThat(dateTime).isEqualTo(
//                LocalDateTime.of(2024, 12, 13, 10, 8)
//        );
//    }

    @DisplayName("새로운 출석 객체를 입력하면 크루에 맞는 출석 객체를 갱신한다.")
    @Test
    void test5_2() {
        //when
        AttendanceHistory attendanceHistoryOfCrew = new AttendanceHistory();
        Attendance attendance = attendanceHistoryOfCrew.register(LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 10));

        assertThat(attendance).isEqualTo(new Attendance(LocalDate.of(2024, 12, 13),
                LocalTime.of(10, 10)));
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
        AttendanceBook attendanceBook = AttendanceBook.from(crews);

        Map<Crew, List<LocalDateTime>> updatingData = new HashMap<>(Map.of(
                crew1, List.of(LocalDateTime.of(2024, 12, 13, 10, 8)),
                crew2, List.of(LocalDateTime.of(2024, 12, 12, 11, 11))
        ));

        //when
        attendanceBook.update(updatingData);

        //then
        AttendanceHistory attendanceHistory1 = attendanceBook.findByCrew(crew1);
        assertThat(attendanceHistory1.findByDate(LocalDate.of(2024, 12, 13)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 8)));

        AttendanceHistory attendanceHistory2 = attendanceBook.findByCrew(crew2);
        assertThat(attendanceHistory2.findByDate(LocalDate.of(2024, 12, 12)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(11, 11)));
    }

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
        AttendanceBook attendanceBook = AttendanceBook.from(crews);
        AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);

        attendanceHistory.register(LocalDate.of(2024, 12, 13), LocalTime.of(10, 5));

        assertThatThrownBy(() -> attendanceHistory.register(LocalDate.of(2024, 12, 13), LocalTime.of(11, 11)))
                .isInstanceOf(DuplicatedAttendanceRegistrationException.class);
    }
}