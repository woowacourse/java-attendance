import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Crew;
import domain.Day;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceUpdateTest {
    private static Stream<Arguments> provideModificationInformation() {
        return Stream.of(
                Arguments.of("에드", LocalTime.of(10, 0), LocalTime.of(10, 6), true, false),
                Arguments.of("제프", LocalTime.of(10, 6), LocalTime.of(10, 4), false, false),
                Arguments.of("율무", LocalTime.of(10, 0), LocalTime.of(10, 31), false, true),
                Arguments.of("링크", LocalTime.of(10, 31), LocalTime.of(10, 30), true, false));
    }

    @Test
    void 닉네임_수정날짜_등교시간을_입력하여_기록을_수정한다() {
        final var crew = new Crew("에드");
        final var day = new Day(LocalDate.of(2025, 2, 27));
        final var originTime = LocalTime.of(10, 0);
        final var attendance = new Attendance(day, originTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.recordAttendance(crew, attendance);

        final var modifiedTime = LocalTime.of(10, 5);
        attendance.modifyTimeTo(modifiedTime);

        assertEquals(modifiedTime, attendance.getTime());
    }

    @ParameterizedTest
    @MethodSource("provideModificationInformation")
    void 수정된_시간에_따라_출석_상태도_함께_변경된다(String nickname, LocalTime originTime, LocalTime modifiedTime,
                                   Boolean isLateExpected,
                                   Boolean isAbsentExpected) {
        final var day = new Day(LocalDate.of(2025, 2, 27));
        final var attendance = new Attendance(day, originTime);
        Crew crew = new Crew(nickname);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.recordAttendance(crew, attendance);

        attendance.modifyTimeTo(modifiedTime);

        Boolean isLate = attendance.isLate();
        Boolean isAbsent = attendance.isAbsent();

        assertEquals(isLateExpected, isLate);
        assertEquals(isAbsentExpected, isAbsent);
    }
}