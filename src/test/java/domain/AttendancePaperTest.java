package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import common.SystemDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendancePaperTest {

    private AttendancePaper attendancePaper;

    @BeforeEach
    void setUp() {
        attendancePaper = new AttendancePaper(1L, "쿠키", AttendanceRecordGenerator.generate());
    }

    @Test
    @DisplayName("출석 페이터 생성 테스트")
    void test1() {
        //given
        final Long id = 1L;
        final String name = "윌슨";
        final Map<LocalDate, AttendanceRecord> attendanceMap = new LinkedHashMap<>();

        //should
        assertThatCode(() -> new AttendancePaper(id, name, attendanceMap)).doesNotThrowAnyException();

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 추가 테스트")
    void test2(final LocalDateTime localDateTime) {
        //given
        //when
        final AttendanceRecord attendanceRecord = attendancePaper.addAttendance(localDateTime);

        //then
        assertThat(attendanceRecord.attendanceDate().getLocalDate()).isEqualTo(localDateTime.toLocalDate());
    }

    private static Stream<LocalDateTime> test2() {
        return Stream.of(
        LocalDateTime.of(2024, 12, 13, 10, 6),
        LocalDateTime.of(2024, 12, 20, 10, 5),
        LocalDateTime.of(2024, 12, 20, 10, 31),
        LocalDateTime.of(2024, 12, 9, 13, 6),
        LocalDateTime.of(2024, 12, 16, 13, 5),
        LocalDateTime.of(2024, 12, 16, 13, 31)
        );
    }



    @Test
    @DisplayName("출석 확인 테스트")
    void test3() {
        //given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        final AttendanceRecord attendanceRecord = attendancePaper.addAttendance(localDateTime);
        //then
        assertAll(
                () -> assertThat(attendanceRecord.attendanceDate().getLocalDate()).isEqualTo(
                        localDateTime.toLocalDate()),
                () -> assertThat(attendanceRecord.attendanceTime().getLocalTime()).isEqualTo(
                        localDateTime.toLocalTime())
        );
    }

    @Test
    @DisplayName("출석 기록이 이미 존재하여 예외가 발생한다.")
    void test4() {
        //given
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        //then
        assertThatIllegalArgumentException().isThrownBy(
                () -> attendancePaper.validateExistAttendanceDate(localDateTime.toLocalDate()));

    }

    @Test
    @DisplayName("출석 수정 테스트")
    void test5() {
        //given
        final LocalDateTime beforeDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);
        final LocalDateTime afterDateTime = LocalDateTime.of(2024, 12, 13, 10, 31);
        final AttendanceTime attendanceTime = AttendanceTime.of(afterDateTime.getDayOfWeek(),
                afterDateTime.toLocalTime());

        //when
        attendancePaper.addAttendance(beforeDateTime);
        final AttendanceModification attendanceModification = attendancePaper.modifyAttendance(
                afterDateTime.toLocalDate(), attendanceTime);
        final AttendanceRecord beforeAttendanceRecord = attendanceModification.beforeAttendanceRecord();
        final AttendanceRecord afterAttendanceRecord = attendanceModification.afterAttendanceRecord();
        //then
        assertThat(beforeAttendanceRecord.attendanceTime().getLocalTime()).isEqualTo(LocalTime.of(10, 8));
        assertThat(afterAttendanceRecord.attendanceTime().getLocalTime()).isEqualTo(LocalTime.of(10, 31));

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 기록이 존재하지 않아 예외가 발생한다.")
    void test6(final LocalDate localDate) {
        //should
        assertThatIllegalArgumentException().isThrownBy(
                () -> attendancePaper.validateExistAttendanceDate(localDate));

    }

    private static Stream<LocalDate> test6() {
        return Stream.of(
                LocalDate.of(2024, 12, 7),
                LocalDate.of(2024, 12, 14),
                LocalDate.of(2024, 12, 25)
        );
    }

    @Test
    @DisplayName("크루별 출석 기록을 확인한다.")
    void test7() {
        //given
        final LocalDate localDate = SystemDate.NOW.getDate();

        //when
        attendancePaper.addAttendance(LocalDateTime.of(localDate, LocalTime.of(10, 8)));
        final List<AttendanceRecord> attendanceRecords = attendancePaper.lookUpAttendanceHistory();

        //then
        assertThat(attendanceRecords).contains(AttendanceRecord.of(localDate));
    }

    @Test
    @DisplayName("크루별 출석 상태를 카운팅 한다.")
    void test8() {
        //given
        final LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 10, 0);
        final LocalDateTime localDateTime2 = LocalDateTime.of(2024, 12, 3, 10, 0);
        final LocalDateTime localDateTime3 = LocalDateTime.of(2024, 12, 4, 10, 6);
        final LocalDateTime localDateTime4 = LocalDateTime.of(2024, 12, 5, 10, 31);
        final LocalDateTime localDateTime5 = LocalDateTime.of(2024, 12, 6, 10, 35);
        final LocalDateTime localDateTime6 = LocalDateTime.of(2024, 12, 9, 13, 6);
        //when
        attendancePaper.addAttendance(localDateTime1);
        attendancePaper.addAttendance(localDateTime2);
        attendancePaper.addAttendance(localDateTime3);
        attendancePaper.addAttendance(localDateTime4);
        attendancePaper.addAttendance(localDateTime5);
        attendancePaper.addAttendance(localDateTime6);
        final Map<AttendanceStatus, Integer> countAttendanceStatus = attendancePaper.countAttendanceStatus();

        //then
        assertAll(
                () -> assertThat(countAttendanceStatus).containsKey(AttendanceStatus.ATTENDANCE)
                        .containsKey(AttendanceStatus.LATE)
                        .containsKey(AttendanceStatus.ABSENCE),
                () -> assertThat(countAttendanceStatus.get(AttendanceStatus.ATTENDANCE)).isGreaterThan(1),
                () -> assertThat(countAttendanceStatus.get(AttendanceStatus.LATE)).isGreaterThan(1),
                () -> assertThat(countAttendanceStatus.get(AttendanceStatus.ABSENCE)).isGreaterThan(1)
        );
    }

    @Test
    @DisplayName("크루별 패널티를 계산한다.")
    void test9() {
        //given
        //when
        final Penalty penalty = attendancePaper.calculatePenalty();

        //then
        assertThat(penalty).isEqualTo(Penalty.EXPLUSION);
    }
}
