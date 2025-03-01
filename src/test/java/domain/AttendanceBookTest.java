package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    void 초기_빈값의_출석부를_생성한다() {
        // when
        AttendanceBook attendanceBook = AttendanceBook.initBook();

        // then
        assertThat(attendanceBook).isNotNull();
        assertThat(attendanceBook.getBook()).hasSize(0);
    }

    @Test
    void 출석부에_크루를_추가한다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initBook();
        Crew crew = Crew.fromName("제프리");

        // when
        attendanceBook.addCrew(crew);

        // then
        assertThat(attendanceBook.getBook()).hasSize(1);
    }

    @Test
    void 출석부에_크루의_출석정보를_추가한다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initBook();
        Crew crew = Crew.fromName("제프리");
        AttendanceInfo attendanceInfo = AttendanceInfo.fromDateAndTime(CampusDate.fromDate(LocalDate.of(2025, 2, 27)),
                CampusTime.from("10:06"));
        attendanceBook.addCrew(crew);

        // when
        attendanceBook.addInfo(crew, attendanceInfo);

        // then
        assertThat(attendanceBook.getBook().get(crew).getAttendanceInfos()).hasSize(1);
        assertThat(attendanceBook.getBook().get(crew).getAttendanceInfos().getFirst().getHour()).isEqualTo(10);
        assertThat(attendanceBook.getBook().get(crew).getAttendanceInfos().getFirst().getMinute()).isEqualTo(6);
    }

    @Test
    void 시간과_날짜_정보로_크루의_출석정보를_추가한다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initBook();
        Crew crew = Crew.fromName("제프리");
        attendanceBook.addCrew(crew);

        // when
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));
        CampusTime campusTime = CampusTime.from("10:06");
        attendanceBook.addInfoWithDate(crew, campusDate, campusTime);

        // then
        assertThat(attendanceBook.getBook().get(crew).getAttendanceInfos()).hasSize(1);
    }
}
