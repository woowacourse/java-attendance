package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {

    private static Attendances attendances;

    @BeforeEach
    public void setAttendances() {
        attendances = new Attendances(Map.of(
                "짱수", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 5)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 10)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 4)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 0))),
                "이든", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 11, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 40))),
                "빙티", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 30)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 12, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 13, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 15, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 14, 0))),
                "빙봉", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 15)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 12, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 9, 56)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 45))),
                "쿠키", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 11, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 6)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 10)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 29))
                )
        ));
    }

    @Test
    void 크루의_출석_상태별_횟수를_조회한다() {
        String nickname = "짱수";

        assertThat(attendances.calculateAttendanceCount(nickname)).isEqualTo(3);
        assertThat(attendances.calculateLateCount(nickname)).isEqualTo(1);
        assertThat(attendances.calculateAbsentCount(nickname)).isEqualTo(1);
    }

    @Test
    void 결석_5회_이상일경우_제적_대상자이다() {
        String nickname = "이든";

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.EXPULSION);
    }

    @Test
    void 결석_3회_초과일경우_면담_대상자이다() {
        String nickname = "빙티";

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.INTERVIEW);
    }

    @Test
    void 결석_3회_초과일경우_경고_대상자이다() {
        String nickname = "빙봉";

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.CAUTION);
    }

    @Test
    void 지각_3회는_결석_1회로_간주한다() {
        String nickname = "쿠키";

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.CAUTION);
    }

    @Test
    void 결석_2회_이하일경우_제적_대상자가_아니다() {
        String nickname = "짱수";

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.NONE);
    }

    @Test
    void 닉네임과_일자를_통해_출석기록을_가져온다() {
        String nickname = "빙티";
        LocalDate attendanceDate = LocalDate.of(2025, 2, 5);

        Attendance log = attendances.findLogWithNameAndDate(nickname, attendanceDate);

        assertThat(log.getLocalDateTime()).isEqualTo(LocalDateTime.of(2025, 2, 5, 13, 0));
    }

    @Test
    void 닉네임과_일자를_통해_출석을_추가한다() {
        String nickname = "짱수";
        LocalDateTime attendanceDateTime = LocalDateTime.of(2025, 2, 10, 13, 0);

        Attendances testAttendances = new Attendances(new HashMap<>());
        testAttendances.addAttendanceLog(nickname, attendanceDateTime);

        Assertions.assertDoesNotThrow(() -> testAttendances.findLogWithNameAndDate(nickname, attendanceDateTime.toLocalDate()));
    }

    @Test
    void 닉네임과_수정일자를_통해_출석기록을_수정한다() {
        String nickname = "빙봉";
        LocalDateTime updateDateTime = LocalDateTime.of(2025, 2, 7, 10, 0);

        attendances.updateAttendance(nickname, updateDateTime);

        Attendance log = attendances.findLogWithNameAndDate(nickname, updateDateTime.toLocalDate());

        assertThat(log.getLocalDateTime().toLocalTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 공휴일에_출석을_시도하면_예외를_발생시킨다() {
        String nickname = "짱수";

        LocalDateTime attendanceDateTime = LocalDateTime.of(2025, 2, 11, 10, 0);
        int month = attendanceDateTime.getMonthValue();
        int dayOfMonth = attendanceDateTime.getDayOfMonth();

        assertThatThrownBy(() -> attendances.addAttendanceLog(nickname, attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] " + month + "월 " + dayOfMonth + "일은 공휴일입니다.");
    }
}
