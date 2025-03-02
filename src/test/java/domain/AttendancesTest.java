package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
                new Nickname("짱수"), List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 5)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 10)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 4)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 0))),
                new Nickname("이든"), List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 11, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 40)),
                        new Attendance(LocalDateTime.of(2025, 2, 10, 13, 40))),
                new Nickname("빙티"), List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 30)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 12, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 13, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 15, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 14, 0))),
                new Nickname("빙봉"), List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 15)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 12, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 9, 56)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 20))),
                new Nickname("쿠키"), List.of(
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
        Nickname nickname = new Nickname("짱수");

        assertThat(attendances.calculateAttendanceCount(nickname)).isEqualTo(3);
        assertThat(attendances.calculateLateCount(nickname)).isEqualTo(1);
        assertThat(attendances.calculateAbsentCount(nickname)).isEqualTo(1);
    }

    @Test
    void 결석_5회_초과일경우_제적_대상자이다() {
        Nickname nickname = new Nickname("이든");

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.EXPULSION);
    }

    @Test
    void 결석_3회_이상일경우_면담_대상자이다() {
        Nickname nickname = new Nickname("빙티");

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.INTERVIEW);
    }

    @Test
    void 결석_2회_이상일경우_경고_대상자이다() {
        Nickname nickname = new Nickname("빙봉");

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.CAUTION);
    }

    @Test
    void 지각_3회는_결석_1회로_간주한다() {
        Nickname nickname = new Nickname("쿠키");

        int totalAbsentCount = PenaltyStatus.getTotalAbsentCount(nickname, attendances);

        assertThat(totalAbsentCount).isEqualTo(3);
    }

    @Test
    void 결석_2회_이하일경우_제적_대상자가_아니다() {
        Nickname nickname = new Nickname("짱수");

        PenaltyStatus statusByNickname = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(statusByNickname).isEqualTo(PenaltyStatus.NONE);
    }

    @Test
    void 닉네임과_일자를_통해_출석기록을_가져온다() {
        Nickname nickname = new Nickname("빙티");
        LocalDate attendanceDate = LocalDate.of(2025, 2, 5);

        Attendance log = attendances.findLogWithNameAndDate(nickname, attendanceDate);

        assertThat(log.getLocalDateTime()).isEqualTo(LocalDateTime.of(2025, 2, 5, 13, 0));
    }

    @Test
    void 닉네임과_일자를_통해_출석을_추가한다() {
        Nickname nickname = new Nickname("짱수");
        LocalDateTime attendanceDateTime = LocalDateTime.of(2025, 2, 10, 13, 0);

        Attendances testAttendances = new Attendances(new HashMap<>());
        testAttendances.addAttendanceLog(nickname, attendanceDateTime);

        Assertions.assertDoesNotThrow(() -> testAttendances.findLogWithNameAndDate(nickname, attendanceDateTime.toLocalDate()));
    }

    @Test
    void 닉네임과_수정일자를_통해_출석기록을_수정한다() {
        Nickname nickname = new Nickname("빙봉");
        LocalDateTime updateDateTime = LocalDateTime.of(2025, 2, 7, 10, 0);

        attendances.updateAttendance(nickname, updateDateTime);

        Attendance log = attendances.findLogWithNameAndDate(nickname, updateDateTime.toLocalDate());

        assertThat(log.getLocalDateTime().toLocalTime()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 공휴일에_출석을_시도하면_예외를_발생시킨다() {
        Nickname nickname = new Nickname("짱수");

        LocalDateTime attendanceDateTime = LocalDateTime.of(2025, 2, 11, 10, 0);
        int month = attendanceDateTime.getMonthValue();
        int dayOfMonth = attendanceDateTime.getDayOfMonth();

        assertThatThrownBy(() -> attendances.addAttendanceLog(nickname, attendanceDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] " + month + "월 " + dayOfMonth + "일은 공휴일입니다.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"제프", "에드", "이프"})
    void 등록되지_않은_닉네임_예외를_발생시킨다(String nickname) {
        assertThatThrownBy(() -> attendances.checkCrewName(new Nickname(nickname)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 10, 12, 13, 14, 17, 18, 19, 20, 21, 24, 25, 26, 27})
    void 출석_기록에_비어있는_부분을_결석_처리한다(int dayOfMonth) {
        Attendances attendanceLogs = new Attendances(new HashMap<>());
        attendanceLogs.addAttendanceLog(new Nickname("짱수"), LocalDateTime.of(2025, 2, 28, 10, 0));

        attendanceLogs.recordAllAbsences();
        Attendance attendance = attendanceLogs.findLogWithNameAndDate(new Nickname("짱수"), LocalDate.of(2025, 2, dayOfMonth));
        LocalTime localTime = attendance.getLocalDateTime().toLocalTime();

        assertThat(localTime).isEqualTo(LocalTime.MAX);
    }

}
