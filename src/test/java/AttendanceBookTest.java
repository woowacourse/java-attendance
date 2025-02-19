import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceBookTest {

    @Test
    @DisplayName("출석부에_기존이름이_존재여부_확인")
    void 출석부에_기존이름이_존재여부_확인() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance("2024-12-01", "10:06");
        attendanceBook.addNewCrew(crew1);

        assertThat(attendanceBook.checkAlreadyExists("쿠키")).isEqualTo(true);
        assertThat(attendanceBook.checkAlreadyExists("없음")).isEqualTo(false);
    }

    @Test
    @DisplayName("이름이 존재하는 경우, 해당 이름의 일별 출석 기록에 해당 날짜와 시간 기록")
    void 이름이_존재하는_경우_해당_이름의_일별_출석_기록에_해당_날짜와_시간_기록() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance("2024-12-01", "10:06");
        attendanceBook.addNewCrew(crew1);

        attendanceBook.initialize("쿠키", "2024-12-02", "12:00"); // 같은 이름, 다른 날짜와 시간

        assertThat(attendanceBook.getCrewByName("쿠키").getDailyAttendance().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("이름이 존재하지 않는 경우, 해당 이름의 일별 출석 기록에 해당 날짜와 시간 기록")
    void 이름이_존재하지_않는_경우_해당_이름의_일별_출석_기록에_해당_날짜와_시간_기록() {
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew1 = Crew.createByName("쿠키");
        crew1.addDailyAttendance("2024-12-01", "10:06");
        attendanceBook.addNewCrew(crew1);

        attendanceBook.initialize("우유", "2024-12-01", "12:00"); // 다른 이름

        Crew crew = attendanceBook.getCrewByName("우유");

        assertThat(attendanceBook.getCrewByName("우유").getDailyAttendance().size()).isEqualTo(1);
        assertThat(attendanceBook.getCrewByName("쿠키").getDailyAttendance().size()).isEqualTo(1);
    }
}