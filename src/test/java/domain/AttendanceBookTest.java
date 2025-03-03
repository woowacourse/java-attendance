package domain;

import static domain.attendance.constant.AttendanceRiskLevel.COUNSELING;
import static org.assertj.core.api.Assertions.assertThat;

import domain.attendance.AttendanceBook;
import domain.attendance.AttendanceInfo;
import domain.attendance.AttendanceInfos;
import domain.datetime.CampusDate;
import domain.datetime.CampusTime;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import util.FileReader;

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
        List<AttendanceInfo> attendanceInfos = attendanceBook.findInfoByCrew(crew).getAttendanceInfos();
        assertThat(attendanceInfos).hasSize(1);
        assertThat(attendanceInfos.getFirst().getHour()).isEqualTo(10);
        assertThat(attendanceInfos.getFirst().getMinute()).isEqualTo(6);
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
        AttendanceInfos addedInfos = attendanceBook.addInfoWithDateAndTime(crew, campusDate, campusTime);

        // then
        assertThat(addedInfos.getAttendanceInfos()).hasSize(1);
    }

    @Test
    void 시간과_날짜_정보로_크루의_출석정보를_수정한다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initBook();
        Crew crew = Crew.fromName("제프리");
        attendanceBook.addCrew(crew);
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));
        CampusTime campusTime = CampusTime.from("10:06");
        attendanceBook.addInfoWithDateAndTime(crew, campusDate, campusTime);

        // when
        CampusDate modifyDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));
        CampusTime modifyTime = CampusTime.from("10:04");
        AttendanceInfos modifiedInfos = attendanceBook.modifyInfoWithDateAndTime(crew, modifyDate, modifyTime);

        // then
        assertThat(modifiedInfos.getAttendanceInfos()).hasSize(1);
        assertThat(modifiedInfos.getAttendanceInfos().getFirst().getHour()).isEqualTo(10);
        assertThat(modifiedInfos.getAttendanceInfos().getFirst().getMinute()).isEqualTo(4);
    }

    @Test
    void 크루_이름으로_크루의_출석정보를_가져온다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.initBook();
        Crew crew = Crew.fromName("제프리");
        attendanceBook.addCrew(crew);
        CampusDate campusDate = CampusDate.fromDate(LocalDate.of(2025, 2, 27));
        CampusTime campusTime = CampusTime.from("10:06");
        attendanceBook.addInfoWithDateAndTime(crew, campusDate, campusTime);

        // when
        AttendanceInfos infoByCrew = attendanceBook.findInfoByCrew(crew);

        // then
        assertThat(infoByCrew.getAttendanceInfos()).hasSize(1);
        assertThat(infoByCrew.getAttendanceInfos().getFirst().getHour()).isEqualTo(10);
        assertThat(infoByCrew.getAttendanceInfos().getFirst().getMinute()).isEqualTo(6);
    }

    @Test
    void 위험관리대상_크루들의_정보를_출력한다() {
        // given
        AttendanceBook attendanceBook = AttendanceBook.createBookByAttendances(
                FileReader.fileReadLine("attendances.csv"));
        LocalDate date = LocalDate.of(2025, 2, 17);
        Crew crew = Crew.fromName("빙티");

        // when
        AttendanceBook riskCrewBook = attendanceBook.findRiskCrewBook(date);

        // then
        assertThat(riskCrewBook.getBook()).hasSize(5);
        assertThat(riskCrewBook.findInfoByCrew(crew).countsByDate(date).calculateAttendanceRiskLevel()).isEqualTo(
                COUNSELING);
    }

}
