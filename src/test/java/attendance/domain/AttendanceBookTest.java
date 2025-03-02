package attendance.domain;

import static attendance.domain.AttendancesTest.generateAttendances;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 출석_시_닉네임이_존재하지_않으면_예외를_던진다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);

        assertThatThrownBy(
                () -> attendanceBook.attend("모루", Attendance.from(LocalDateTime.of(2024, 12, 16, 12, 59))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임과_시간을_입력하면_출석한다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);
        Attendance attendance = Attendance.from(LocalDateTime.of(2024, 12, 16, 12, 59));

        assertThatCode(() -> attendanceBook.attend("훌라", attendance))
                .doesNotThrowAnyException();
    }

    @Test
    void 이미_출석했다면_예외를_던진다() {
        List<LocalDateTime> dateTimes = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", dateTimes);

        assertThatThrownBy(
                () -> attendanceBook.attend("훌라", Attendance.from(LocalDateTime.of(2024, 12, 13, 9, 59))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 닉네임과_시간을_이용해_출석하면_수정된_기록을_반환한다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        final var result = attendanceBook.updateAttendance("훌라", LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThat(result.getDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 6));
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

    @Test
    void 닉네임과_날짜를_이용해_출석기록을_가져온다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 13, 9, 59));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        final var result = attendanceBook.findByNicknameAndDate("훌라", LocalDateTime.of(2024, 12, 13, 10, 6));

        assertThat(result.getDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 9, 59));
    }

    @Test
    void 닉네임과_날짜를_이용해_출석기록을_가져온다2() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 12, 9, 31));
        AttendanceBook attendanceBook = generateAttendanceBook("훌라", attendances);

        final var result = attendanceBook.findByNicknameAndDate("훌라", LocalDateTime.of(2024, 12, 12, 10, 4));

        assertThat(result.getDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 12, 9, 31));
    }

    @Test
    void 위험자들을_반환한다() {
        List<LocalDateTime> attendances = List.of(
                LocalDateTime.of(2024, 12, 10, 10, 31),
                LocalDateTime.of(2024, 12, 11, 10, 31),
                LocalDateTime.of(2024, 12, 12, 10, 31),
                LocalDateTime.of(2024, 12, 13, 10, 31)
        );
        AttendanceBook attendanceBook = new AttendanceBook(
                Map.of(
                        "훌라", generateAttendances(attendances),
                        "모루", generateAttendances(attendances)
                )
        );

        final var result = attendanceBook.findPenaltyCrews();

        assertThat(result.keySet()).hasSize(2);
    }

    private static AttendanceBook generateAttendanceBook(String nickname, List<LocalDateTime> dateTimes) {
        Map<String, Attendances> crewAttendances = Map.of(nickname, generateAttendances(dateTimes));
        return new AttendanceBook(new HashMap<>(crewAttendances));
    }
}
