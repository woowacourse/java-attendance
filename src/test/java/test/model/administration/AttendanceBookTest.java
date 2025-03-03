package test.model.administration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.admininstration.AttendanceBook;
import model.admininstration.Crews;
import model.attendance.Attendance;
import model.attendance.AttendanceHistory;
import model.attendance.Crew;
import model.exception.DuplicatedAttendanceRegistrationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @DisplayName("크루 객체 별로 출석 객체를 빈 객체로 초기화한다.")
    @Test
    void success_initializeAttendancesInAttendanceBook() {
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

    @DisplayName("새로운 출석 객체를 입력하면 크루에 맞는 출석 객체를 갱신한다.")
    @Test
    void success_updateOneAttendance() {
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

        Crews crews = new Crews(List.of(crew1, crew2));
        AttendanceBook attendanceBook = AttendanceBook.from(crews);

        Map<Crew, List<LocalDateTime>> updatingData = new HashMap<>(Map.of(
                crew1, List.of(LocalDateTime.of(2024, 12, 13, 10, 8)),
                crew2, List.of(LocalDateTime.of(2024, 12, 12, 11, 11))
        ));

        //when
        attendanceBook.update(updatingData);
        AttendanceHistory attendanceHistory1 = attendanceBook.findByCrew(crew1);
        AttendanceHistory attendanceHistory2 = attendanceBook.findByCrew(crew2);

        //then
        assertThat(attendanceHistory1.findByDate(LocalDate.of(2024, 12, 13)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 8)));

        assertThat(attendanceHistory2.findByDate(LocalDate.of(2024, 12, 12)))
                .isEqualTo(new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(11, 11)));
    }

    @DisplayName("입력한 날짜에 출석객체가 있는 경우, 예외를 반환한다.")
    @Test
    void fail_ifRegisterAtExistingAttendanceDate() {
        //given
        Crew crew = new Crew("빙티");
        Crews crews = new Crews(List.of(crew));
        AttendanceBook attendanceBook = AttendanceBook.from(crews);

        AttendanceHistory attendanceHistory = attendanceBook.findByCrew(crew);
        attendanceHistory.register(LocalDate.of(2024, 12, 13), LocalTime.of(10, 5));

        //when, then
        assertThatThrownBy(() -> attendanceHistory.register(LocalDate.of(2024, 12, 13), LocalTime.of(11, 11)))
                .isInstanceOf(DuplicatedAttendanceRegistrationException.class);
    }


}
