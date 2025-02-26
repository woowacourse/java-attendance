package attendance.domain;

import static attendance.domain.AttendancesTest.generateAttendances;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 출석_시_닉네임이_존재하지_않으면_예외를_던진다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);

        assertThatThrownBy(() -> attendanceBook.attend("모루", LocalDateTime.of(2024, 12, 16, 12, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임과_시간을_입력하면_출석한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);

        final var result = attendanceBook.attend("훌라", LocalDateTime.of(2024, 12, 16, 12, 59));

        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 16, 12, 59);
        Attendance comparison = Attendance.from(dateTime);

        assertThat(result).isEqualTo(comparison);
    }

    @Test
    void 이미_출석했다면_예외를_던진다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);

        assertThatThrownBy(() -> attendanceBook.attend("훌라", LocalDateTime.of(2024, 12, 13, 9, 59)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임과_시간을_이용해_출석하면_수정된_기록을_반환한다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        final var result = attendanceBook.updateAttendance("훌라", LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThat(result).isEqualTo(Attendance.from(LocalDateTime.of(2024, 12, 13, 10, 6)));
    }

    @Test
    void 출석_기록을_가져올_때_닉네임이_없다면_예외를_던진다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        assertThatThrownBy(() -> attendanceBook.findByNickname("모루"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임을_통해_출석_기록을_가져온다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        assertThatCode(() -> attendanceBook.findByNickname("훌라"))
                .doesNotThrowAnyException();
    }

    public static AttendanceBook generateAttendanceBook(String nickname, List<LocalDateTime> dateTimes) {
        Map<String, Attendances> crewAttendances = Map.of(nickname, generateAttendances(dateTimes));
        return new AttendanceBook(new HashMap<>(crewAttendances));
    }
}
