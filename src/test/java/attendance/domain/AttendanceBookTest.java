package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("출석부 테스트")
public class AttendanceBookTest {

    @Test
    void 출석부에_크루별_출석을_추가할수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        assertThat(attendanceBook.size()).isEqualTo(1);
    }

    @Test
    void 출석부의_크루별_출석리스트중_원하는날짜의_출석을_가져올수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        LocalDate inputDate = LocalDate.of(2024, 12, 12);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, inputDate);

        assertThat(oldAttendance).isEqualTo(attendance);
    }

    @Test
    void 출석부에서_크루별_출석을_수정할수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        LocalDate inputDate = LocalDate.of(2024, 12, 12);
        Attendance oldAttendance = attendanceBook.findAttendanceByCrew(crew, inputDate);
        Attendance newAttendance = new Attendance(inputDate, LocalTime.of(10, 30));

        assertThatCode(() -> attendanceBook.update(crew, oldAttendance, newAttendance))
                .doesNotThrowAnyException();
    }
}
