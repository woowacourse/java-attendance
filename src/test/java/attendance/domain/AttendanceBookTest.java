package attendance.domain;

import attendance.util.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;

@DisplayName("출석부 테스트")
public class AttendanceBookTest {

    @Test
    void 출석부에_크루별_출석을_추가할수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));

        assertThatCode(() -> attendanceBook.add(crew, attendance))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석부의_크루별_출석리스트중_원하는날짜의_출석을_가져올수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        LocalDate inputDate = LocalDate.of(2024, 12, 12);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, inputDate);

        assertThat(oldAttendance).isEqualTo(attendance);
    }

    @Test
    void 출석부에서_크루별_출석을_수정할수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        LocalDate inputDate = LocalDate.of(2024, 12, 12);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, inputDate);
        Attendance newAttendance = Attendance.of(inputDate, LocalTime.of(10, 30));

        assertThatCode(() -> attendanceBook.update(crew, oldAttendance, newAttendance))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석부에_존재하는_크루가_아니라면_예외를_반환한다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        Crew anotherCrew = new Crew(new Nickname("브라운"));

        assertThatThrownBy(() -> attendanceBook.validateContainsCrew(anotherCrew))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CREW_NICKNAME_NOT_EXIST_ERROR.getMessage());
    }

    @Test
    void 출석부에서_해당날짜_이전까지_크루의_출석기록을_가져올수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance1 = Attendance.of(LocalDate.of(2024, 12, 11), LocalTime.of(10, 0));
        Attendance attendance2 = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance1);
        attendanceBook.add(crew, attendance2);

        LocalDate today = LocalDate.of(2024, 12, 13);
        Attendances attendances = attendanceBook.getRecordOfCrew(today, crew);
        assertThat(attendances.getAttendancesUntilYesterday(today)).hasSize(2);
    }

    @Test
    void 출석부에서_크루와_출석통계를_반환한다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew duei = new Crew(new Nickname("듀이"));
        Crew brown = new Crew(new Nickname("브라운"));
        Attendance attendanceOfDuei = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        Attendance attendanceOfBrown = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(duei, attendanceOfDuei);
        attendanceBook.add(brown, attendanceOfBrown);

        LocalDate today = LocalDate.of(2024, 12, 13);
        assertThat(attendanceBook.getSortedCrewsAndStatistics(today)).hasSize(2);
    }
}
