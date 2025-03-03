package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertAll;

import common.SystemDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = AttendanceBook.create();
    }

    @Test
    @DisplayName("출석 확인 테스트")
    void test1() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        final AttendanceRecord attendanceRecord = attendanceBook.addAttendance(name, localDateTime);
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
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 24, 10, 0);
        //when
        attendanceBook.addAttendance(name, localDateTime);
        //then
        assertThatIllegalArgumentException().isThrownBy(
                () -> attendanceBook.validateExistAttendance(localDateTime.toLocalDate(), name));

    }

    @Test
    @DisplayName("출석 수정 테스트")
    void test2() {
        //given
        final String name = "쿠키";
        final LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 13, 10, 31);
        final AttendanceTime attendanceTime = AttendanceTime.of(localDateTime.getDayOfWeek(),
                localDateTime.toLocalTime());

        //when
        final AttendanceModification attendanceModification = attendanceBook.modifyAttendance(name,
                localDateTime.toLocalDate(), attendanceTime);
        final AttendanceRecord beforeAttendanceRecord = attendanceModification.beforeAttendanceRecord();
        final AttendanceRecord afterAttendanceRecord = attendanceModification.afterAttendanceRecord();
        //then
        assertThat(beforeAttendanceRecord.attendanceTime().getLocalTime()).isEqualTo(LocalTime.of(10, 8));
        assertThat(afterAttendanceRecord.attendanceTime().getLocalTime()).isEqualTo(LocalTime.of(10, 31));

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("출석 기록이 존재하지 않아 예외가 발생한다.")
    void test3(final LocalDate localDate) {
        //should
        assertThatIllegalArgumentException().isThrownBy(
                () -> attendanceBook.validateModificationAttendanceDate("쿠키", localDate));

    }

    private static Stream<LocalDate> test3() {
        return Stream.of(
                LocalDate.of(2024, 12, 7),
                LocalDate.of(2024, 12, 14),
                LocalDate.of(2024, 12, 25)
        );
    }

    @Test
    @DisplayName("크루별 출석 기록을 확인한다.")
    void test5() {
        //given
        final String name = "쿠키";
        final LocalDate localDate = SystemDate.NOW.getDate();

        //when
        attendanceBook.addAttendance(name, LocalDateTime.of(localDate, LocalTime.of(10, 8)));
        final List<AttendanceRecord> attendanceRecords = attendanceBook.lookUpAttendanceHistory(name);

        //then
        assertThat(attendanceRecords).contains(AttendanceRecord.of(localDate));
    }

    @Test
    @DisplayName("크루별 출석 상태를 카운팅 한다.")
    void test6() {
        //given
        final String name = "쿠키";

        //when
        final Map<AttendanceStatus, Integer> countAttendanceStatus = attendanceBook.countAttendanceStatus(name);

        //then
        assertAll(
                () -> assertThat(countAttendanceStatus).containsKey(AttendanceStatus.ATTENDANCE)
                        .containsKey(AttendanceStatus.LATE)
                        .containsKey(AttendanceStatus.ABSENCE),
                () -> assertThat(countAttendanceStatus.get(AttendanceStatus.ATTENDANCE)).isGreaterThanOrEqualTo(2),
                () -> assertThat(countAttendanceStatus.get(AttendanceStatus.ABSENCE)).isGreaterThanOrEqualTo(2)
        );
    }

    @Test
    @DisplayName("크루별 패널티를 계산한다.")
    void test7() {
        //given
        final String name = "쿠키";

        //when
        final Penalty penalty = attendanceBook.calculatePenalty(name);

        //then
        assertThat(penalty).isEqualTo(Penalty.INTERVIEW);
    }
    
    @Test
    @DisplayName("제적 위험자를 확인 한다.")
    void test8() {
        //given
        //when
        final List<AttendancePaper> sortedPenaltyAttendancePapers = attendanceBook.getSortedPenaltyAttendancePapers();
        //then
        assertThat(sortedPenaltyAttendancePapers).isNotNull();
        
    }
}
